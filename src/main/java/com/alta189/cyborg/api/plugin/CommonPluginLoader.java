/*
 * This file is part of SpoutAPI (http://www.spout.org/).
 *
 * SpoutAPI is licensed under the SpoutDev License Version 1.
 *
 * SpoutAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * SpoutAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package com.alta189.cyborg.api.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.regex.Pattern;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.CyborgLogger;
import com.alta189.cyborg.api.exception.InvalidDescriptionFileException;
import com.alta189.cyborg.api.exception.InvalidPluginException;
import com.alta189.cyborg.api.exception.UnknownDependencyException;
import com.alta189.cyborg.api.exception.UnknownSoftDependencyException;

public class CommonPluginLoader implements PluginLoader {
	private final Cyborg cyborg;
	private final Pattern[] patterns;
	private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
	private final Map<String, CommonClassLoader> loaders = new HashMap<String, CommonClassLoader>();

	public CommonPluginLoader(final Cyborg game) {
		this.cyborg = game;
		patterns = new Pattern[] {Pattern.compile("\\.jar$")};
	}

	public Pattern[] getPatterns() {
		return patterns;
	}

	public void enablePlugin(Plugin paramPlugin) {
		if (!CommonPlugin.class.isAssignableFrom(paramPlugin.getClass())) {
			throw new IllegalArgumentException("Cannot enable plugin with this PluginLoader as it is of the wrong type!");
		}
		if (!paramPlugin.isEnabled()) {
			CommonPlugin cp = (CommonPlugin) paramPlugin;
			String name = cp.getDescription().getName();

			if (!loaders.containsKey(name)) {
				loaders.put(name, (CommonClassLoader) cp.getClassLoader());
			}

			try {
				cp.setEnabled(true);
				cp.onEnable();
			} catch (Exception e) {
				CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("An error occured when enabling '").append(paramPlugin.getDescription().getFullName()).append("': ").append(e.getMessage()).toString(), e);
			}

			// TODO call PluginEnableEvent
		}
	}

	public void disablePlugin(Plugin paramPlugin) {
		if (!CommonPlugin.class.isAssignableFrom(paramPlugin.getClass())) {
			throw new IllegalArgumentException("Cannot disable plugin with this PluginLoader as it is of the wrong type!");
		}
		if (paramPlugin.isEnabled()) {
			CommonPlugin cp = (CommonPlugin) paramPlugin;
			String name = cp.getDescription().getName();

			if (!loaders.containsKey(name)) {
				loaders.put(name, (CommonClassLoader) cp.getClassLoader());
			}

			try {
				cp.setEnabled(false);
				cp.onDisable();
			} catch (Exception e) {
				CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("An error occurred when disabling plugin '").append(paramPlugin.getDescription().getFullName()).append("' : ").append(e.getMessage()).toString(), e);
			}

			// TODO call PluginDisableEvent
		}

	}

	public Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidPluginException, UnknownDependencyException, InvalidDescriptionFileException {
		return loadPlugin(paramFile, false);
	}

	public Plugin loadPlugin(File paramFile, boolean ignoresoftdepends) throws InvalidPluginException, InvalidPluginException, UnknownDependencyException, InvalidDescriptionFileException {
		CommonPlugin result = null;
		PluginDescriptionFile desc = null;

		if (!paramFile.exists()) {
			throw new InvalidPluginException(new StringBuilder().append(paramFile.getName()).append(" does not exist!").toString());
		}

		JarFile jar = null;
		InputStream in = null;
		try {
			jar = new JarFile(paramFile);
			JarEntry entry = jar.getJarEntry("spoutplugin.yml");

			if (entry == null) {
				entry = jar.getJarEntry("plugin.yml");
			}

			if (entry == null) {
				throw new InvalidPluginException("Jar has no plugin.yml or spoutplugin.yml!");
			}

			in = jar.getInputStream(entry);
			desc = new PluginDescriptionFile(in);
		} catch (IOException e) {
			throw new InvalidPluginException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (jar != null) {
				try {
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		File dataFolder = new File(paramFile.getParentFile(), desc.getName());

		List<String> depends = desc.getDepends();
		if (depends == null) {
			depends = new ArrayList<String>();
		}

		for (String depend : depends) {
			if (loaders == null) {
				throw new UnknownDependencyException(depend);
			}
			if (!loaders.containsKey(depend)) {
				throw new UnknownDependencyException(depend);
			}
		}

		if (!ignoresoftdepends) {
			List<String> softdepend = desc.getSoftDepends();
			if (softdepend == null) {
				softdepend = new ArrayList<String>();
			}

			for (String depend : depends) {
				if (loaders == null) {
					throw new UnknownSoftDependencyException(depend);
				}
				if (!loaders.containsKey(depend)) {
					throw new UnknownSoftDependencyException(depend);
				}
			}
		}

		CommonClassLoader loader = null;
		try {
			URL[] urls = new URL[1];
			urls[0] = paramFile.toURI().toURL();

			loader =  new CommonClassLoader(this, urls, getClass().getClassLoader());
			Class<?> main = Class.forName(desc.getMain(), true, loader);
			Class<? extends CommonPlugin> plugin = main.asSubclass(CommonPlugin.class);

			Constructor<? extends CommonPlugin> constructor = plugin.getConstructor();

			result = constructor.newInstance();

			result.initialize(this, cyborg, desc, dataFolder, paramFile, loader);

		} catch (Exception e) {
			throw new InvalidPluginException(e);
		}

		loaders.put(desc.getName(), loader);

		return result;
	}

	public Class<?> getClassByName(final String name) {
		Class<?> cached = classes.get(name);

		if (cached != null) {
			return cached;
		} else {
			for (String current : loaders.keySet()) {
				CommonClassLoader loader = loaders.get(current);

				try {
					cached = loader.findClass(name, false);
				} catch (ClassNotFoundException cnfe) {
				}
				if (cached != null) {
					return cached;
				}
			}
		}
		return null;
	}

	public void setClass(final String name, final Class<?> clazz) {
		if (!classes.containsKey(name)) {
			classes.put(name, clazz);
		}
	}
}

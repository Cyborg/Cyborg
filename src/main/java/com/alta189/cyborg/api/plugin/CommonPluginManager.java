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
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.CyborgLogger;
import org.apache.commons.io.FileUtils;
import com.alta189.cyborg.api.exception.InvalidDescriptionFileException;
import com.alta189.cyborg.api.exception.InvalidPluginException;
import com.alta189.cyborg.api.exception.UnknownDependencyException;

public class CommonPluginManager implements PluginManager {
	private final Cyborg cyborg;
	private File updateDir;
	private final Map<Pattern, PluginLoader> loaders = new HashMap<Pattern, PluginLoader>();
	private final Map<String, Plugin> names = new HashMap<String, Plugin>();
	private final List<Plugin> plugins = new ArrayList<Plugin>();

	public CommonPluginManager(final Cyborg cyborg) {
		this.cyborg = cyborg;
	}

	public void registerPluginLoader(Class<? extends PluginLoader> loader) {
		PluginLoader instance = null;

		try {
			Constructor<? extends PluginLoader> constructor = loader.getConstructor(new Class[] {Cyborg.class});

			instance = constructor.newInstance(cyborg);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error registering plugin loader!", e);
		}

		synchronized (this) {
			for (Pattern pattern : instance.getPatterns()) {
				loaders.put(pattern, instance);
			}
		}
	}

	public Plugin getPlugin(String plugin) {
		return names.get(plugin);
	}

	public Plugin[] getPlugins() {
		return plugins.toArray(new Plugin[plugins.size()]);
	}

	public synchronized Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidDescriptionFileException, UnknownDependencyException {
		return loadPlugin(paramFile, false);
	}

	public synchronized Plugin loadPlugin(File paramFile, boolean ignoresoftdepends) throws InvalidPluginException, InvalidDescriptionFileException, UnknownDependencyException {
		File update = null;

		if (updateDir != null && updateDir.isDirectory()) {
			update = new File(updateDir, paramFile.getName());
			if (update.exists() && update.isFile()) {
				try {
					FileUtils.copyFile(update, paramFile);
				} catch (IOException e) {
					CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("Error copying file '").append(update.getPath()).append("' to its new destination at '").append(paramFile.getPath()).append("': ").append(e.getMessage()).toString(), e);
				}
				update.delete();
			}
		}

		Set<Pattern> patterns = loaders.keySet();
		Plugin result = null;

		for (Pattern pattern : patterns) {
			String name = paramFile.getName();
			Matcher m = pattern.matcher(name);

			if (m.find()) {
				PluginLoader loader = loaders.get(pattern);
				result = loader.loadPlugin(paramFile, ignoresoftdepends);

				if (result != null) {
					break;
				}
			}
		}

		if (result != null) {
			plugins.add(result);
			names.put(result.getDescription().getName(), result);
		}
		return result;
	}

	public synchronized Plugin[] loadPlugins(File paramFile) {

		if (!paramFile.isDirectory()) {
			throw new IllegalArgumentException("File parameter was not a Directory!");
		}

		if (cyborg.getUpdateFolder() != null) {
			updateDir = cyborg.getUpdateFolder();
		}

		List<Plugin> result = new ArrayList<Plugin>();
		LinkedList<File> files = new LinkedList<File>(Arrays.asList(paramFile.listFiles()));
		boolean failed = false;
		boolean lastPass = false;

		while (!failed || lastPass) {
			failed = true;
			Iterator<File> iterator = files.iterator();

			while (iterator.hasNext()) {
				File file = iterator.next();
				Plugin plugin = null;

				if (file.isDirectory()) {
					iterator.remove();
					continue;
				}

				try {
					plugin = loadPlugin(file, lastPass);
					iterator.remove();
				} catch (UnknownDependencyException e) {
					if (lastPass) {
						CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("Unable to load '").append(file.getName()).append("' in directory '").append(paramFile.getPath()).append("': ").append(e.getMessage()).toString(), e);
						iterator.remove();
					} else {
						plugin = null;
					}
				} catch (InvalidDescriptionFileException e) {
					CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("Unable to load '").append(file.getName()).append("' in directory '").append(paramFile.getPath()).append("': ").append(e.getMessage()).toString(), e);
					iterator.remove();
				} catch (InvalidPluginException e) {
					CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("Unable to load '").append(file.getName()).append("' in directory '").append(paramFile.getPath()).append("': ").append(e.getMessage()).toString(), e);
					iterator.remove();
				}

				if (plugin != null) {
					result.add(plugin);
					failed = false;
					lastPass = false;
				}
			}
			if (lastPass) {
				break;
			} else if (failed) {
				lastPass = true;
			}
		}

		return result.toArray(new Plugin[result.size()]);
	}

	public void disablePlugins() {
		for (Plugin plugin : plugins) {
			disablePlugin(plugin);
		}
	}

	public void clearPlugins() {
		synchronized (this) {
			disablePlugins();
			plugins.clear();
			names.clear();
			// HandlerList.unregisterAll();
		}
	}

	public void enablePlugin(Plugin plugin) {
		if (!plugin.isEnabled()) {

			try {
				plugin.getPluginLoader().enablePlugin(plugin);
			} catch (Exception e) {
				CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("An error ocurred in the Plugin Loader while enabling plugin '").append(plugin.getDescription().getFullName()).append("': ").append(e.getMessage()).toString(), e);
			}
		}
	}

	public void disablePlugin(Plugin plugin) {
		if (plugin.isEnabled()) {

			try {
				plugin.getPluginLoader().disablePlugin(plugin);
			} catch (Exception e) {
				CyborgLogger.getLogger().log(Level.SEVERE, new StringBuilder().append("An error ocurred in the Plugin Loader while disabling plugin '").append(plugin.getDescription().getFullName()).append("': ").append(e.getMessage()).toString(), e);
			}
		}
	}
}

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
import java.util.logging.Logger;

import com.alta189.cyborg.Cyborg;

public abstract class CommonPlugin implements Plugin {
	private Cyborg cyborg;
	private PluginDescriptionFile description;
	private CommonClassLoader classLoader;
	private CommonPluginLoader pluginLoader;
	private File dataFolder;
	private File file;
	private boolean enabled;
	private Logger logger;

	public abstract void onEnable();

	public abstract void onDisable();

	public void onReload() {
	}

	public void onLoad() {
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public final PluginLoader getPluginLoader() {
		return pluginLoader;
	}

	public final Logger getLogger() {
		return logger;
	}

	public final PluginDescriptionFile getDescription() {
		return description;
	}

	public final void initialize(CommonPluginLoader commonsPluginLoader, Cyborg cyborg, PluginDescriptionFile desc, File dataFolder, File paramFile, CommonClassLoader loader) {
		description = desc;
		classLoader = loader;
		pluginLoader = commonsPluginLoader;
		this.cyborg = cyborg;
		this.dataFolder = dataFolder;
		file = paramFile;
		logger = new PluginLogger(this);
	}

	public final Cyborg getCyborg() {
		return cyborg;
	}

	public final ClassLoader getClassLoader() {
		return classLoader;
	}

	public final File getDataFolder() {
		return dataFolder;
	}

	public final File getFile() {
		return file;
	}

	public String getName() {
		return getDescription().getName();
	}
}

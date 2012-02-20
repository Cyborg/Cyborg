/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of cyborg
 *
 * cyborg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * cyborg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.cyborg.api.plugin;

import java.io.File;

import com.alta189.cyborg.api.exception.InvalidDescriptionFileException;
import com.alta189.cyborg.api.exception.InvalidPluginException;
import com.alta189.cyborg.api.exception.UnknownDependencyException;

public interface PluginManager {
	/**
	 * Returns the the instance of a plugins when given its name
	 *
	 * @param plugin The name of the plugin
	 * @return instance of the plugin
	 */
	public abstract Plugin getPlugin(String plugin);

	/**
	 * Returns an array of plugins that have been loaded
	 *
	 * @return plugins
	 */
	public abstract Plugin[] getPlugins();

	/**
	 * Loads the file as a plugin
	 *
	 * @param paramFile
	 * @return instance of the plugin
	 * @throws InvalidPluginException
	 * @throws InvalidDescriptionFileException
	 * @throws UnknownDependencyException
	 */
	public abstract Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidDescriptionFileException, UnknownDependencyException;

	/**
	 * Loads all plugins in a directory
	 *
	 * @param paramFile
	 * @return array of plugins loaded
	 */
	public abstract Plugin[] loadPlugins(File paramFile);

	/**
	 * Disables all plugins
	 */
	public abstract void disablePlugins();

	/**
	 * Disables all plugins and clears the List of plugins
	 */
	public abstract void clearPlugins();

	/**
	 * Enables the plugin
	 *
	 * @param plugin
	 */
	public abstract void enablePlugin(Plugin plugin);

	/**
	 * Disables the plugin
	 *
	 * @param plugin
	 */
	public abstract void disablePlugin(Plugin plugin);
}

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
import java.util.regex.Pattern;

import com.alta189.cyborg.api.exception.InvalidDescriptionFileException;
import com.alta189.cyborg.api.exception.InvalidPluginException;
import com.alta189.cyborg.api.exception.UnknownDependencyException;

public interface PluginLoader {
	public abstract Pattern[] getPatterns();

	/**
	 * Enables the plugin
	 *
	 * @param paramPlugin
	 */
	public abstract void enablePlugin(Plugin paramPlugin);

	/**
	 * Disables the plugin
	 *
	 * @param paramPlugin
	 */
	public abstract void disablePlugin(Plugin paramPlugin);

	/**
	 * Loads the file as a plugin
	 *
	 * @param paramFile
	 * @return instance of the plugin
	 * @throws InvalidPluginException
	 * @throws InvalidPluginException
	 * @throws UnknownDependencyException
	 * @throws InvalidDescriptionFileException
	 */
	public abstract Plugin loadPlugin(File paramFile) throws InvalidPluginException, InvalidPluginException, UnknownDependencyException, InvalidDescriptionFileException;

	/**
	 * Loads the file as a plugin
	 *
	 * @param paramFile
	 * @param paramBoolean ignores soft dependencies when it attempts to load
	 *            the plugin
	 * @return instance of the plugin
	 * @throws InvalidPluginException
	 * @throws InvalidPluginException
	 * @throws UnknownDependencyException
	 * @throws InvalidDescriptionFileException
	 */
	public abstract Plugin loadPlugin(File paramFile, boolean paramBoolean) throws InvalidPluginException, InvalidPluginException, UnknownDependencyException, InvalidDescriptionFileException;
}

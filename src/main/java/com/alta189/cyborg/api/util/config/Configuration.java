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
package com.alta189.cyborg.api.util.config;

import java.util.regex.Pattern;

import com.alta189.cyborg.api.exception.ConfigurationException;

public interface Configuration extends ConfigurationNodeSource {
	/**
	 * Load the configuration's values
	 *
	 * @throws ConfigurationException if an error occurs while loading the configuration
	 */
	void load() throws ConfigurationException;

	/**
	 * Save the configuration's values
	 *
	 * @throws ConfigurationException when an error occurs
	 */
	void save() throws ConfigurationException;

	/**
	 * Adds the given node to the configuration structure
	 * This will attempt to use the node's existing parents in the configuration structure where possible
	 *
	 * @param node The node to add
	 */
	void setNode(ConfigurationNode node);

	/**
	 * The path separator to use with {@link #getNode(String)}
	 * The path separator splits paths as a literal string, not a regular expression.
	 *
	 * @return The configuration's path separator
	 */
	String getPathSeparator();

	/**
	 * Sets this configuration's path separator. More information on how the path separator
	 * functions in {@link #getPathSeparator()}
	 *
	 * @see #getPathSeparator()
	 * @param pathSeparator The path separator
	 */
	void setPathSeparator(String pathSeparator);

	Pattern getPathSeparatorPattern();

	/**
	 * Whether this configuration writes default values (from {@link ConfigurationNode#getValue(Object)}
	 * to the configuration structure
	 * @return Whether this configuration writes defaults
	 */
	boolean doesWriteDefaults();

	/**
	 * Sets whether this configuration writes defaults
	 *
	 * @see #doesWriteDefaults() for info on what this means
	 * @param writesDefaults Whether this configuration writes defaults
	 */
	void setWritesDefaults(boolean writesDefaults);

	/**
	 * Split the provided path into a string array suitable for accessing the correct configuration children.
	 * Normally this just splits the path with the {@link #getPathSeparator()}, but can limit
	 * how deep a child path can go or whether this configuration can even have children.
	 *
	 * @param path The path to split
	 * @return The connectly split path.
	 */
	String[] splitNodePath(String path);

	/**
	 * Make sure the provided path meets the requirements. A correct implementation of
	 * Configuration will impose the same restrictions on this and {@link #splitNodePath(String)},
	 * so invoking this method on an array from {@link #splitNodePath(String)} would return the original array.
	 *
	 * @param rawPath The raw path of the configuration
	 * @return The corrected input path
	 */
	String[] ensureCorrectPath(String[] rawPath);
}

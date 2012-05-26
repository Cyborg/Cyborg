/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of Cyborg
 *
 * Cyborg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cyborg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.cyborg.api.util.config.commented;

import com.alta189.cyborg.api.util.config.Configuration;

/**
 * A configuration that accepts comments. All ConfigurationNodes passed to this configuration
 * must be CommentedConfigurationNodes, and the node getters for this configuration all
 * return CommentedConfigurationNodes for convenience.
 * @author zml2008
 */
public interface CommentedConfiguration extends Configuration {
	public CommentedConfigurationNode createConfigurationNode(String[] path, Object value);

	public CommentedConfigurationNode getNode(String... node);

	public CommentedConfigurationNode getNode(String path);
}

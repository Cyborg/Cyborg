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
package com.alta189.cyborg.api.util.config.commented;

import com.alta189.cyborg.api.util.config.Configuration;
import com.alta189.cyborg.api.util.config.ConfigurationNode;

/**
 * A ConfigurationNode type that also stores comments. These normally exist in {@link CommentedConfiguration}s.
 * @author zml2008
 */
public class CommentedConfigurationNode extends ConfigurationNode {
	public static final String LINE_SEPARATOR;

	static {
		String sep = System.getProperty("line.separator");
		if (sep == null) {
			sep = "\n";
		}
		LINE_SEPARATOR = sep;
	}

	private String[] comment;

	public CommentedConfigurationNode(Configuration config, String[] path, Object value) {
		super(config, path, value);
	}

	/**
	 * Returns the comment lines attached to this configuration node
	 * Will return null if this node doesn't have a comment
	 * @return The comment for this node
	 */
	public String[] getComment() {
		return comment;
	}

	/**
	 * Sets the comment that is attached to this configuration node.
	 * In this method the comment is provided as one line, containing the line separator character
	 * @param comment The comment to set
	 */
	public void setComment(String comment) {
		checkAdded();
		this.comment = comment.split(LINE_SEPARATOR);
	}

	/**
	 * Sets the comment of the configuration, already split by line
	 * @param comment The comment lines
	 */
	public void setComment(String... comment) {
		checkAdded();
		this.comment = comment;
	}

	@Override
	public CommentedConfigurationNode createConfigurationNode(String[] path, Object value) {
		return new CommentedConfigurationNode(getConfiguration(), path, value);
	}
}

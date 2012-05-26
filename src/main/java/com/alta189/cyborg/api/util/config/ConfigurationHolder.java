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
package com.alta189.cyborg.api.util.config;

import com.alta189.cyborg.api.util.data.ValueHolderBase;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Map;
import java.util.Set;

/**
 * This object holds a reference to a ConfigurationNode and provides all the methods to
 * get its value, but using the default provided in the constructor
 */
public class ConfigurationHolder extends ValueHolderBase implements ConfigurationNodeSource {
	private Configuration configuration;
	private final String[] path;
	private Object def;

	public ConfigurationHolder(Configuration config, Object def, String... path) {
		this.path = path;
		this.configuration = config;
		this.def = def;
	}

	public ConfigurationHolder(Object value, String... path) {
		this(null, value, path);
	}

	private ConfigurationNode getNode() {
		if (getConfiguration() == null) {
			throw new IllegalStateException("The ConfigurationHolder at path " + ArrayUtils.toString(path) + " is not attached to a Configuration!");
		}
		return getConfiguration().getNode(getPathElements());
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration config) {
		this.configuration = config;
	}

	public String[] getPathElements() {
		return path;
	}

	@Override
	public Object getValue() {
		return getNode().getValue(this.def);
	}

	public Object getValue(Object def) {
		return getNode().getValue(this.def);
	}

	public Object setValue(Object value) {
		return getNode().setValue(value);
	}

	public ConfigurationNode getChild(String name) {
		return getNode().getChild(name);
	}

	public ConfigurationNode getChild(String name, boolean add) {
		return getNode().getChild(name, add);
	}

	public ConfigurationNode addChild(ConfigurationNode node) {
		return getNode().addChild(node);
	}

	public void addChildren(ConfigurationNode... nodes) {
		getNode().addChildren(nodes);
	}

	public ConfigurationNode removeChild(String key) {
		return getNode().removeChild(key);
	}

	public ConfigurationNode removeChild(ConfigurationNode node) {
		return getNode().removeChild(node);
	}

	public Map<String, ConfigurationNode> getChildren() {
		return getNode().getChildren();
	}

	public Map<String, Object> getValues() {
		return getNode().getValues();
	}

	public Set<String> getKeys(boolean deep) {
		return getNode().getKeys(deep);
	}

	public ConfigurationNode getNode(String path) {
		return getNode().getNode(path);
	}

	public ConfigurationNode getNode(String... path) {
		return getNode().getNode(path);
	}

	public boolean hasChildren() {
		return getNode().hasChildren();
	}
}

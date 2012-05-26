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

import com.alta189.cyborg.api.exception.ConfigurationException;

import java.util.Collections;
import java.util.Map;

/**
 * This represents a configuration that loads its values from an in-memory {@link Map}
 * @author zml2008
 */
public class MapConfiguration extends MapBasedConfiguration {
	private Map<?, ?> map;

	/**
	 * Create a new configuration backed by an empty map. Loading a configuration
	 * instantiated with this constructor will do nothing.
	 */
	public MapConfiguration() {
		this(Collections.emptyMap());
	}

	public MapConfiguration(Map<?, ?> map) {
		super();
		this.map = map == null ? Collections.emptyMap() : map;
	}

	@Override
	protected Map<?, ?> loadToMap() throws ConfigurationException {
		return map;
	}

	@Override
	protected void saveFromMap(Map<?, ?> map) throws ConfigurationException {
		this.map = map;
	}

	public Map<?, ?> getMap() {
		return map;
	}
}

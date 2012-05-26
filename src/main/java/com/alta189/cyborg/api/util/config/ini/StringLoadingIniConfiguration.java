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
package com.alta189.cyborg.api.util.config.ini;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * A subclass of IniConfiguration that loads from a String
 * @author zml2008
 */
public class StringLoadingIniConfiguration extends IniConfiguration {
	private String value;
	private StringWriter writer;

	public StringLoadingIniConfiguration(String value) {
		super(null);
		this.value = value;
	}

	@Override
	protected Reader getReader() {
		return new StringReader(value);
	}

	/**
	 * Set the value to load from.  {@link #load()} needs to be called separately for
	 * the value passed in this method to affect the actual configuration data.
	 * @param value The configuration value
	 */
	public void setValue(String value) {
		this.value = value == null ? "" : value;
	}

	public String getValue() {
		if (writer != null) {
			value = writer.toString();
			writer = null;
		}
		return value;
	}

	@Override
	protected Writer getWriter() {
		return writer = new StringWriter();
	}
}

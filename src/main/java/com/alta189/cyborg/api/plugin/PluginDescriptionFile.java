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

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.alta189.cyborg.api.exception.InvalidDescriptionFileException;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class PluginDescriptionFile {
	private static final Yaml yaml = new Yaml(new SafeConstructor());

	private String name;
	private String version;
	private String description;
	private String author;
	private List<String> authors;
	private String website;
	private boolean reload;
	private String main;
	private List<String> depends;
	private List<String> softdepends;
	private String fullname;
	private String protocol;

	public PluginDescriptionFile(String name, String version, String main) {
		this.name = name;
		this.version = version;
		this.main = main;;
		fullname = new StringBuilder().append(name).append(" v").append(version).toString();
	}

	@SuppressWarnings("unchecked")
	public PluginDescriptionFile(InputStream stream) throws InvalidDescriptionFileException {
		load((Map<String, Object>) yaml.load(stream));
	}

	@SuppressWarnings("unchecked")
	public PluginDescriptionFile(Reader reader) throws InvalidDescriptionFileException {
		load((Map<String, Object>) yaml.load(reader));
	}

	@SuppressWarnings("unchecked")
	public PluginDescriptionFile(String raw) throws InvalidDescriptionFileException {
		load((Map<String, Object>) yaml.load(raw));
	}

	@SuppressWarnings("unchecked")
	private void load(Map<String, Object> map) throws InvalidDescriptionFileException {
		try {
			name = (String) map.get("name");

			if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
				throw new InvalidDescriptionFileException("The field 'name' in plugin.yml contains invalid characters.");
			}
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'name' is not defined in the plugin.yml!");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'name' is of the wrong type in the plugin.yml!");
		}

		try {
			main = (String) map.get("main");
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'main' is not defined in the plugin.yml!");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'main' is of the wrong type in the plugin.yml!");
		}

		try {
			version = map.get("version").toString();
		} catch (NullPointerException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'version' is not defined in the plugin.yml!");
		} catch (ClassCastException ex) {
			throw new InvalidDescriptionFileException(ex, "The field 'version' is of the wrong type in the plugin.yml!");
		}

		fullname = new StringBuilder().append(name).append(" v").append(version).toString();

		if (map.containsKey("author")) {
			try {
				author = (String) map.get("author");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'author' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("authors")) {
			try {
				authors = (List<String>) map.get("authors");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'authors' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("depends")) {
			try {
				depends = (List<String>) map.get("depends");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'depends' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("softdepends")) {
			try {
				softdepends = (List<String>) map.get("softdepends");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'softdepends' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("description")) {
			try {
				description = (String) map.get("description");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'description' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("reload")) {
			try {
				reload = (Boolean) map.get("reload");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'reload' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("website")) {
			try {
				website = (String) map.get("website");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'website' is of the wrong type in the plugin.yml!");
			}
		}

		if (map.containsKey("protocol")) {
			try {
				protocol = (String) map.get("protocol");
			} catch (ClassCastException ex) {
				throw new InvalidDescriptionFileException(ex, "The field 'protocol' is of the wrong type in the plugin.yml!");
			}
		}
	}

	/**
	 * Returns the plugin's name
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the plugin's version
	 *
	 * @return version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Returns the plugin's description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the plugin's author
	 *
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Returns the plugin's authors
	 *
	 * @return authors
	 */
	public List<String> getAuthors() {
		return authors;
	}

	/**
	 * Returns the plugin's website
	 *
	 * @return website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * Returns false if the plugin wants to be exempt from a reload
	 *
	 * @return reload
	 */
	public boolean allowsReload() {
		return reload;
	}

	/**
	 * Returns the path the plugins main class
	 *
	 * @return main
	 */
	public String getMain() {
		return main;
	}

	/**
	 * Returns the plugin's dependencies
	 *
	 * @return depends
	 */
	public List<String> getDepends() {
		return depends;
	}

	/**
	 * Returns the plugin's soft dependencies
	 *
	 * @return softdepends
	 */
	public List<String> getSoftDepends() {
		return softdepends;
	}

	/**
	 * Returns the plugin's fullname The fullname is formatted as follows:
	 * [name] v[version]
	 *
	 * @return
	 */
	public String getFullName() {
		return fullname;
	}

	/**
	 * Returns the plugin's protocol.
	 *
	 * @return The protocol string contained in the plugin.yml
	 */
	public String getProtocol() {
		return protocol;
	}
}

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
package com.alta189.cyborg;

import com.alta189.cyborg.api.util.yaml.YAMLProcessor;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Setter;

public class Settings {
	@Setter(AccessLevel.PROTECTED)
	private static YAMLProcessor settings;

	public static String getNick() {
		return settings.getString("nick", "Cyborg");
	}

	public static void setNick(String nick) {
		settings.setProperty("nick", nick);
	}

	public static String getIdent() {
		return settings.getString("ident", "Cyborg");
	}

	public static void setIdent(String ident) {
		settings.setProperty("ident", ident);
	}

	public static List<String> getAlternateNicks() {
		return settings.getStringList("alt-nicks", new ArrayList<String>());
	}

	public static void setAlternativeNicks(List<String> nicks) {
		settings.setProperty("alt-nicks", nicks);
	}

	public static String getServerAddress() {
		return settings.getString("server.address", "irc.esper.net");
	}

	public static void setServerAddress(String address) {
		settings.setProperty("server.address", address);
	}

	public static int getServerPort() {
		return settings.getInt("server.port", 6667);
	}

	public static void setServerPort(int port) {
		settings.setProperty("server.port", port);
	}

	public static String getServerPass() {
		String pass = settings.getString("sever.password", "none");
		if (pass.equals("none")) {
			return null;
		}
		return pass;
	}

	public static void setServerPass(String pass) {
		if (pass == null) {
			settings.setProperty("sever.password", "none");
		} else {
			settings.setProperty("sever.password", pass);
		}
	}

	public static List<String> getChannels() {
		return settings.getStringList("server.channels", new ArrayList<String>());
	}

	public static void setChannels(List<String> nicks) {
		settings.setProperty("server.channels", nicks);
	}

	public static long getMessageDelay() {
		return Long.valueOf(settings.getString("message-delay", "1000"));
	}

	public static void setMessageDelay(long delay) {
		settings.setProperty("message-delay", delay);
	}
}

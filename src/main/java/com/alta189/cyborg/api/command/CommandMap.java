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
package com.alta189.cyborg.api.command;

import java.util.HashMap;
import java.util.Map;

public class CommandMap {
	private final Map<String, Command> commandMap = new HashMap<String, Command>();
	private final Map<String, Command> aliasMap = new HashMap<String, Command>();

	public void addCommand(Command command) {
		if (commandMap.get(command.getCommand()) == null) {
			commandMap.put(command.getCommand(), command);
			for (String alias : command.getAliases()) {
				registerAlias(alias, command);
			}
		}
	}

	private void registerAlias(String alias, Command command) {
		if (aliasMap.get(alias.toLowerCase()) == null) {
			aliasMap.put(alias.toLowerCase(), command);
		}
	}

	public Command getCommand(String cmd) {
		return getCommand(cmd, false);
	}

	public Command getCommand(String cmd, boolean ignoreAliases) {
		Command command = commandMap.get(cmd.toLowerCase());
		if (command == null && !ignoreAliases) {
			command = aliasMap.get(cmd.toLowerCase());
		}
		return command;
	}
}

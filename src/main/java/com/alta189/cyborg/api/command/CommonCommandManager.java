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

import com.alta189.cyborg.api.command.annotation.AnnotatedCommandFactory;
import com.alta189.cyborg.api.command.annotation.Injector;
import java.util.Arrays;
import java.util.List;

public class CommonCommandManager extends CommandManager {
	private final List<String> prefixes = Arrays.asList(",", ".", "!", "~", "?", ">", ",", ":", "@", "$", "%", "^", "&", "*", "-", "_", "=", "+", "`");

	@Override
	public String execute(CommandSource source, Command command, CommandContext context) throws CommandException {
		CommandExecutor executor = command.getExecutor();
		if (executor != null) {
			return executor.processCommand(source, command, context);
		}
		return null;
	}

	@Override
	public String execute(CommandSource source, String raw, CommandContext.LocationType locationType, String location) throws CommandException {
		String prefix = getPrefix(raw);
		if (prefix != null) {
			raw = raw.substring(1);
		}
		Command command = getCommandMap().getCommand(getCommand(raw));
		if (command != null) {
			String[] args = getArgs(raw);

			CommandContext context = new CommandContext(args, prefix, locationType);
			context.setLocation(location);
			return execute(source, command, context);
		}
		return null;
	}

	@Override
	public String execute(CommandSource source, String raw, CommandContext.LocationType locationType) throws CommandException {
		return execute(source, raw, locationType, null);
	}

	@Override
	public String execute(CommandSource source, String raw) throws CommandException {
		return execute(source, raw, null);
	}

	@Override
	public boolean isCommand(String command) {
		String prefix = getPrefix(command);
		if (prefix != null) {
			command = command.substring(1);
		}
		command = getCommand(command);
		Command cmd = getCommandMap().getCommand(command);
		return cmd != null;
	}

	private String getPrefix(String raw) {
		String p = raw.substring(0, 1);
		if (prefixes.contains(p)) {
			return p;
		}
		return null;
	}

	@Override
	public void registerCommand(Command command) {
		getCommandMap().addCommand(command);
	}

	@Override
	public void registerCommands(Named owner, Class<?> clazz, Injector injector) {
		AnnotatedCommandFactory factory = new AnnotatedCommandFactory(injector);
		List<Command> commands = factory.createCommands(owner, clazz);
		if (commands != null && commands.size() > 0) {
			for (Command command : commands) {
				registerCommand(command);
			}
		}
	}

	private String[] getArgs(String raw) {
		if (!raw.contains(" ") || raw.equals(" ")) {
			return null;
		}

		int firstSpace = raw.indexOf(" ");
		if (firstSpace + 1 >= raw.length()) {
			return null;
		}
		return raw.substring(firstSpace + 1).split(" ");
	}

	private String getCommand(String raw) {
		if (raw.contains(" ")) {
			return raw.substring(0, raw.indexOf(" "));
		}
		return raw;
	}
}

/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of TerminalCommand
 *
 * TerminalCommand is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TerminalCommand is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alta189.cyborg.api.command;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Command {

	@Getter
	private final Named owner;
	@Getter
	private final String command;
	@Getter
	@Setter
	private String prefix;
	@Getter
	private final List<String> aliases = new ArrayList<String>();
	@Getter
	@Setter
	private CommandExecutor executor;
	@Getter
	@Setter
	private String desc;
	
	public Command(Named owner, String command) {
		this.owner = owner;
		this.command = command.toLowerCase();
	}
	
}

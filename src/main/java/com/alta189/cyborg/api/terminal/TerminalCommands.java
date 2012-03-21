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

package com.alta189.cyborg.api.terminal;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.Main;
import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.annotation.Command;

public class TerminalCommands {
	
	@Command(name = "/stop", desc = "Terminal command to stop the bot")
	public void stop(CommandSource source, CommandContext contex) {
		if (source.getTerminalUser() != null) {
			source.getTerminalUser().sendMessage("Stopping");

			Cyborg.getInstance().getPluginManager().disablePlugins();
			Cyborg.getInstance().getPluginManager().clearPlugins();
			Cyborg.getInstance().quitServer();
			Cyborg.getInstance().disconnect();
			
			Main.getTerminalThread().interrupt();
			
			System.exit(1);
			
		}
	}
	
}

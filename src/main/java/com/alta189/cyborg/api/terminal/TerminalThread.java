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
import com.alta189.cyborg.api.command.CommandManager;
import com.alta189.cyborg.api.command.CommandSource;
import jline.console.ConsoleReader;

public class TerminalThread extends Thread {

	private ConsoleReader reader;
	private TerminalUser user;
	private CommandSource source;
	private CommandManager commandManager = null;
	@Override
	public void start() {
		try {
			reader = new ConsoleReader();
			user = new TerminalUser();
			source = new CommandSource(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.start();
	}

	@Override
	public void run() {
		try {
			String line;
			while (!isInterrupted() && ((line = reader.readLine()) != null)) {
				if (!line.isEmpty()) {
					if (commandManager == null)
						commandManager = Cyborg.getInstance().getCommandManager();
					try {
						String result = commandManager.execute(source, line);
						if (result != null)
							System.out.println(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

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

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.api.terminal.TerminalUser;
import lombok.Getter;
import org.pircbotx.User;

public class CommandSource {
	
	@Getter
	private final TerminalUser terminalUser;
	@Getter
	private final User user;
	@Getter
	private final Source source;
	
	public CommandSource(TerminalUser terminalUser) {
		this.terminalUser = terminalUser;
		this.user = null;
		this.source = Source.TERMINALUSER;
	}
	
	public CommandSource(User user) {
		this.user = user;
		this.terminalUser = null;
		this.source = Source.USER;
	}
	
	public void sendMessage(String message) {
		switch (getSource()) {
			case USER:
				Cyborg.getInstance().sendMessage(user, message);
				break;
			case TERMINALUSER:
				terminalUser.sendMessage(message);
		}
	}
	
	public enum Source {
		TERMINALUSER,
		USER;
	}

}

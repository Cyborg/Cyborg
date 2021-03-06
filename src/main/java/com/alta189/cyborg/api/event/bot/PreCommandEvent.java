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
package com.alta189.cyborg.api.event.bot;

import com.alta189.cyborg.api.command.Command;
import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;

public class PreCommandEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	private final Command command;
	private final CommandSource source;
	private final CommandContext context;

	public PreCommandEvent(Command command, CommandSource source, CommandContext context) {
		this.command = command;
		this.source = source;
		this.context = context;
	}

	public Command getCommand() {
		return command;
	}

	public CommandSource getSource() {
		return source;
	}

	public CommandContext getContext() {
		return context;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		super.setCancelled(cancelled);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

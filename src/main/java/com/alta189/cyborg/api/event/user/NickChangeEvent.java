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
package com.alta189.cyborg.api.event.user;

import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;
import lombok.Getter;
import org.pircbotx.User;

public class NickChangeEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	@Getter
	private final String oldNick;
	@Getter
	private final String newNick;
	@Getter
	private final User user;
	@Getter
	private final long timestamp;

	public NickChangeEvent(org.pircbotx.hooks.events.NickChangeEvent event) {
		this(event.getOldNick(), event.getNewNick(), event.getUser(), event.getTimestamp());
	}

	public NickChangeEvent(String oldNick, String newNick, User user) {
		this(oldNick, newNick, user, System.currentTimeMillis());
	}

	public NickChangeEvent(String oldNick, String newNick, User user, long timestamp) {
		this.oldNick = oldNick;
		this.newNick = newNick;
		this.user = user;
		this.timestamp = timestamp;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

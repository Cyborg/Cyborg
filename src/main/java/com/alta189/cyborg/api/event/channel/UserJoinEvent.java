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
package com.alta189.cyborg.api.event.channel;

import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;
import lombok.Getter;
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.JoinEvent;

public class UserJoinEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	@Getter
	private final User user;
	@Getter
	private final Channel channel;
	@Getter
	private final long timestamp;

	public UserJoinEvent(JoinEvent event) {
		this(event.getUser(), event.getChannel(), event.getTimestamp());
	}

	public UserJoinEvent(User user, Channel channel) {
		this.user = user;
		this.channel = channel;
		this.timestamp = System.currentTimeMillis();
	}

	public UserJoinEvent(User user, Channel channel, long timestamp) {
		this.user = user;
		this.channel = channel;
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

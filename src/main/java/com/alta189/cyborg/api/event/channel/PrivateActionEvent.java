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

package com.alta189.cyborg.api.event.channel;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;

import lombok.Getter;
import org.pircbotx.User;

public class PrivateActionEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	@Getter
	private final User user;
	@Getter
	private final String action;
	@Getter
	private final long timestamp;

	public PrivateActionEvent(org.pircbotx.hooks.events.ActionEvent event) {
		this(event.getUser(), event.getAction(), event.getTimestamp());
	}

	public PrivateActionEvent(User user, String action) {
		this.user = user;
		this.action = action;
		timestamp = System.currentTimeMillis();
	}

	public PrivateActionEvent(User user, String action, long timestamp) {
		this.user = user;
		this.action = action;
		this.timestamp = timestamp;
	}
	
	public void respond(String response) {
		Cyborg.getInstance().sendMessage(user, response);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

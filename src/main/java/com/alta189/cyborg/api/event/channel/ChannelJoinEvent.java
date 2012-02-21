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
import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.JoinEvent;

public class ChannelJoinEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	private final Channel channel;
	private final User user;
	private final long timestamp;
	
	public ChannelJoinEvent(JoinEvent event) {
		this(event.getChannel(), event.getUser(), event.getTimestamp());
	}
	
	public ChannelJoinEvent(Channel channel, User user) {
		this.channel = channel;
		this.user = user;
		timestamp = System.currentTimeMillis();
	}
	
	public ChannelJoinEvent(Channel channel, User user, long timestamp) {
		this.channel = channel;
		this.user = user;
		this.timestamp = timestamp;
	}
	
	public Channel getChannel() {
		return channel;
	}

	public User getUser() {
		return user;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void respond(String response) {
		Cyborg.getInstance().sendMessage(channel, user.getNick() + ": " + response);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}

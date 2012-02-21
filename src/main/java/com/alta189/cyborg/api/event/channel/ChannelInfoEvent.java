/*
 * Copyright (C) 2012 CyborgDev <cyborg@alta189.com>
 *
 * This file is part of Cyborg.
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

import java.util.Set;

import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;
import org.pircbotx.ChannelListEntry;

public class ChannelInfoEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	private Set<ChannelListEntry> list;
	
	public ChannelInfoEvent(org.pircbotx.hooks.events.ChannelInfoEvent event) {
		this(event.getList());
	}

	public ChannelInfoEvent(Set<ChannelListEntry> list) {
		this.list = list;
	}
	
	public Set<ChannelListEntry> getList() {
		return list;
	}

	//TODO Construct sendMessage

	/**
	 * Get the static handler list of this event subclass.
	 *
	 * @return HandlerList to call event with
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
}

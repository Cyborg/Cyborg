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

public class OpEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	@Getter
	private final Channel channel;
	@Getter
	private final User source;
	@Getter
	private final User recipient;
	@Getter
	private final boolean isOp;
	@Getter
	private final long timestamp;

	public OpEvent(org.pircbotx.hooks.events.OpEvent event) {
		this(event.getChannel(), event.getSource(), event.getRecipient(), event.isOp(), event.getTimestamp());
	}

	public OpEvent(Channel channel, User source, User recipient, boolean isOp) {
		this(channel, source, recipient, isOp, System.currentTimeMillis());
	}

	public OpEvent(Channel channel, User source, User recipient, boolean isOp, long timestamp) {
		this.channel = channel;
		this.source = source;
		this.recipient = recipient;
		this.isOp = isOp;
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

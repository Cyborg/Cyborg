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
import org.pircbotx.UserSnapshot;

public class QuitEvent extends Event {
	private static HandlerList handlers = new HandlerList();
	@Getter
	private final long timestamp = System.currentTimeMillis();
	@Getter
	private UserSnapshot user;
	@Getter
	private String reason;

	public QuitEvent(org.pircbotx.hooks.events.QuitEvent event) {
		this(event.getUser(), event.getReason());
	}

	public QuitEvent(UserSnapshot user, String reason) {
		this.user = user;
		this.reason = reason;
	}

	/**
	 * Get the static handler list of this event subclass.
	 * @return HandlerList to call event with
	 */
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

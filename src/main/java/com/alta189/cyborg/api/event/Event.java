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
package com.alta189.cyborg.api.event;

/**
 * Represents a callable event.
 */
public abstract class Event {
	/**
	 * Stores cancelled status. will be false unless a subclass publishes
	 * setCancelled.
	 */
	protected boolean cancelled = false;

	/**
	 * Get the static handler list of this event subclass.
	 *
	 * @return HandlerList to call event with
	 */
	public abstract HandlerList getHandlers();

	/**
	 * Get event type name.
	 *
	 * @return event name
	 */
	protected String getEventName() {
		return getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return getEventName() + " (" + this.getClass().getName() + ")";
	}

	/**
	 * Set cancelled status. Events which wish to be cancellable should
	 * implement Cancellable and implement setCancelled as:
	 *
	 * <pre>
	 * public void setCancelled(boolean cancelled) {
	 * 	super.setCancelled(cancelled);
	 * }
	 * </pre>
	 *
	 * @param cancelled True to cancel event
	 */
	protected void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/**
	 * Returning true will prevent calling any even Order slots.
	 *
	 * @see Order
	 * @return false if the event is propogating; events which do not implement
	 *         Cancellable should never return true here
	 */
	public boolean isCancelled() {
		return cancelled;
	}
}

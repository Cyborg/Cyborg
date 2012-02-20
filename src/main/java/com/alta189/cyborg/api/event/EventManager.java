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

public interface EventManager {
	/**
	 * Calls an event with the given details
	 *
	 * @param event Event details
	 * @return Called event
	 */
	public <T extends Event> T callEvent(T event);

	/**
	 * Registers all the events in the given listener class
	 *
	 * @param listener Listener to register
	 * @param owner Plugin to register
	 */
	public void registerEvents(Listener listener, Object owner);

	/**
	 * Registers the specified executor to the given event class
	 *
	 * @param event Event type to register
	 * @param priority Priority to register this event at
	 * @param executor EventExecutor to register
	 * @param owner Plugin to register
	 */
	public void registerEvent(Class<? extends Event> event, Order priority, EventExecutor executor, Object owner);
}

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
 * @author lahwran
 */
public class ListenerRegistration {
	private final EventExecutor executor;
	private final Order orderSlot;
	private final Object owner;

	/**
	 * @param executor  Listener this registration represents
	 * @param orderSlot Order position this registration is in
	 * @param owner	 object that created this registration
	 */
	public ListenerRegistration(final EventExecutor executor, final Order orderSlot, final Object owner) {
		this.executor = executor;
		this.orderSlot = orderSlot;
		this.owner = owner;
	}

	/**
	 * Gets the listener for this registration
	 * @return Registered Listener
	 */
	public EventExecutor getExecutor() {
		return executor;
	}

	/**
	 * Gets the {@link Object} for this registration
	 * @return Registered owner
	 */
	public Object getOwner() {
		return owner;
	}

	/**
	 * Gets the order slot for this registration
	 * @return Registered order
	 */
	public Order getOrder() {
		return orderSlot;
	}
}

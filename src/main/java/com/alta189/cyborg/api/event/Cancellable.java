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
package com.alta189.cyborg.api.event;

/**
 * Interface for events that can be cancelled.
 */
public interface Cancellable {
	/**
	 * If an event stops propogating (ie, is cancelled) partway through an even
	 * slot, that slot will not cease execution, but future even slots will not
	 * be called.
	 * @param cancelled True to set event canceled, False to uncancel event.
	 */
	public void setCancelled(boolean cancelled);

	/**
	 * Get event canceled state.
	 * @return whether event is cancelled
	 */
	public boolean isCancelled();
}

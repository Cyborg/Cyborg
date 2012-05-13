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

public enum Order {

	EARLIEST(0, false),
	EARLIEST_IGNORE_CANCEL(1, true),
	EARLY(2, false),
	DEFAULT_IGNORE_CANCEL(3, true),
	DEFAULT(4, false),
	LATE_IGNORE_CANCEL(5, true),
	LATE(6, false),
	LATEST_IGNORE_CANCEL(7, true),
	LATEST(8, false),
	MONITOR(9, true);
	
	private final int index;
	private final boolean ignoresCancelled;

	Order(int index, boolean ignoresCancelled) {
		this.index = index;
		this.ignoresCancelled = ignoresCancelled;
	}
	
	public int getIndex() {
		return index;
	}

	public boolean ignoresCancelled() {
		return ignoresCancelled;
	}
}

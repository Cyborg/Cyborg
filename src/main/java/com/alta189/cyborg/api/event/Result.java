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

public enum Result {
	/**
	 * Deny the event. Depending on the event, the action indicated by the event
	 * will either not take place or will be reverted. Some actions may not be
	 * denied.
	 */
	DENY(false),
	/**
	 * Neither deny nor allow the event. The server will proceed with its normal
	 * handling.
	 */
	DEFAULT(null),
	/**
	 * Allow / Force the event. The action indicated by the event will take
	 * place if possible, even if the server would not normally allow the
	 * action. Some actions may not be allowed.
	 */
	ALLOW(true);
	private Boolean result;

	private Result(Boolean result) {
		this.result = result;
	}

	public Boolean getResult() {
		return result;
	}
}

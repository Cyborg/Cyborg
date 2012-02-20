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
package com.alta189.cyborg.api.exception;

public class InvalidDescriptionFileException extends Exception {
	private static final long serialVersionUID = 1424408665150176335L;
	private final Throwable cause;
	private final String message;

	public InvalidDescriptionFileException(Throwable throwable) {
		this(throwable, "Invalid plugin.yml");
	}

	public InvalidDescriptionFileException(String message) {
		this(null, message);
	}

	public InvalidDescriptionFileException(Throwable throwable, String message) {
		cause = null;
		this.message = message;
	}

	public InvalidDescriptionFileException() {
		this(null, "Invalid plugin.yml");
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

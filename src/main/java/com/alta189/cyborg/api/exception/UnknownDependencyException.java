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
package com.alta189.cyborg.api.exception;

public class UnknownDependencyException extends Exception {
	private static final long serialVersionUID = -8878118844821666192L;
	private final Throwable cause;
	private final String message;

	public UnknownDependencyException(Throwable throwable) {
		this(throwable, "Unknown Dependency");
	}

	public UnknownDependencyException(String message) {
		this(null, message);
	}

	public UnknownDependencyException(Throwable throwable, String message) {
		cause = null;
		this.message = message;
	}

	public UnknownDependencyException() {
		this(null, "Unknown Dependency");
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

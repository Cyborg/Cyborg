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
package com.alta189.cyborg.api.command;

public class CommandResult {
	private ReturnType returnType = ReturnType.MESSAGE;
	private String target;
	private String body;
	private boolean forced = false;

	public ReturnType getReturnType() {
		return returnType;
	}

	public CommandResult setReturnType(ReturnType returnType) {
		this.returnType = returnType;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public CommandResult setTarget(String target) {
		this.target = target;
		return this;
	}

	public String getBody() {
		return body;
	}

	public CommandResult setBody(String body) {
		this.body = body;
		return this;
	}

	public boolean isForced() {
		return forced;
	}

	public CommandResult setForced(boolean forced) {
		this.forced = forced;
		return this;
	}
}

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

package com.alta189.cyborg.api.command.annotation;

import com.alta189.cyborg.api.command.CommandContext;
import com.alta189.cyborg.api.command.CommandException;
import com.alta189.cyborg.api.command.CommandExecutor;
import com.alta189.cyborg.api.command.CommandSource;
import com.alta189.cyborg.api.command.WrappedCommandException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AnnotatedCommandExecutor implements CommandExecutor {

	private final Object instance;
	private final Method method;

	public void processCommand(CommandSource source, com.alta189.cyborg.api.command.Command command, CommandContext args) throws CommandException {
		try {
			List<Object> commandArgs = new ArrayList<Object>(3);
			commandArgs.add(source);
			commandArgs.add(args);
			method.invoke(instance, commandArgs.toArray());
		} catch (IllegalAccessException e) {
			throw new WrappedCommandException(e);
		} catch (InvocationTargetException e) {
			if (e.getCause() == null) {
				throw new WrappedCommandException(e);
			} else {
				Throwable cause = e.getCause();
				if (cause instanceof CommandException) {
					throw (CommandException) cause;
				} else {
					throw new WrappedCommandException(cause);
				}
			}
		}
	}

}

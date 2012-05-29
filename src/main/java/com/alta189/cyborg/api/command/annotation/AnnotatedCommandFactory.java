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

import com.alta189.cyborg.api.command.CommandResult;
import com.alta189.cyborg.api.command.Named;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class AnnotatedCommandFactory {
	private final Injector injector;

	public List<com.alta189.cyborg.api.command.Command> createCommands(Named owner, Class<?> clazz) {
		List<com.alta189.cyborg.api.command.Command> result = new ArrayList<com.alta189.cyborg.api.command.Command>();
		Object instance = null;
		if (injector != null) {
			instance = injector.newInstance(clazz);
		}

		for (Method method : clazz.getMethods()) {
			if (!Modifier.isStatic(method.getModifiers()) && instance == null) {
				continue;
			}
			if (!method.isAnnotationPresent(Command.class)) {
				continue;
			}
			Command command = method.getAnnotation(Command.class);
			if (command.name() == null) {
				continue;
			}
			if (!method.getReturnType().equals(CommandResult.class)) {
				continue;
			}
			com.alta189.cyborg.api.command.Command cmd = new com.alta189.cyborg.api.command.Command(owner, command.name());
			cmd.getAliases().addAll(Arrays.asList(command.aliases()));
			cmd.setDesc(command.desc());
			cmd.setExecutor(new AnnotatedCommandExecutor(instance, method));

			if (method.isAnnotationPresent(Usage.class)) {
				Usage usage = method.getAnnotation(Usage.class);
				cmd.setUsage(usage.value());
			}

			if (method.isAnnotationPresent(Hidden.class)) {
				Hidden hidden = method.getAnnotation(Hidden.class);
				cmd.setHiddenFromList(hidden.value());
			}

			result.add(cmd);
		}
		return result;
	}
}

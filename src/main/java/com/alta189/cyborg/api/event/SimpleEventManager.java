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

import com.alta189.cyborg.CyborgLogger;
import com.alta189.cyborg.CyborgLogger.Level;
import com.alta189.cyborg.api.exception.EventException;
import com.alta189.cyborg.api.exception.IllegalPluginAccessException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleEventManager implements EventManager {
	public <T extends Event> T callEvent(T event) {
		HandlerList handlers = event.getHandlers();
		ListenerRegistration[] listeners = handlers.getRegisteredListeners();

		if (listeners != null) {
			for (ListenerRegistration listener : listeners) {
				try {
					if (!event.isCancelled() || listener.getOrder().ignoresCancelled()) {
						listener.getExecutor().execute(event);
					}
				} catch (Throwable ex) {
					CyborgLogger.log(CyborgLogger.Level.SEVERE, "Could not pass event " + event.getEventName() + " to " + listener.getOwner().getClass().getName(), ex);
				}
			}
		}
		return event;
	}

	public void registerEvents(Listener listener, Object owner) {
		for (Map.Entry<Class<? extends Event>, Set<ListenerRegistration>> entry : createRegisteredListeners(listener, owner).entrySet()) {
			Class<? extends Event> delegatedClass = getRegistrationClass(entry.getKey());
			if (!entry.getKey().equals(delegatedClass)) {
				CyborgLogger.log(CyborgLogger.Level.SEVERE, "Plugin attempted to register delegated event class " + entry.getKey() + ". It should be using " + delegatedClass + "!");
				continue;
			}
			getEventListeners(delegatedClass).registerAll(entry.getValue());
		}
	}

	public void registerEvent(Class<? extends Event> event, Order priority, EventExecutor executor, Object owner) {
		getEventListeners(event).register(new ListenerRegistration(executor, priority, owner));
	}

	/**
	 * Returns the specified event type's HandlerList
	 * @param type EventType to lookup
	 * @return HandlerList The list of registered handlers for the event.
	 */
	private HandlerList getEventListeners(Class<? extends Event> type) {
		try {
			Method method = getRegistrationClass(type).getDeclaredMethod("getHandlerList");
			method.setAccessible(true);
			return (HandlerList) method.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
		try {
			clazz.getDeclaredMethod("getHandlerList");
			return clazz;
		} catch (NoSuchMethodException e) {
			if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
				return getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
			} else {
				throw new IllegalPluginAccessException("Unable to find handler list for event " + clazz.getName());
			}
		}
	}

	public Map<Class<? extends Event>, Set<ListenerRegistration>> createRegisteredListeners(final Listener listener, Object plugin) {
		Map<Class<? extends Event>, Set<ListenerRegistration>> ret = new HashMap<Class<? extends Event>, Set<ListenerRegistration>>();
		Method[] methods;
		try {
			methods = listener.getClass().getDeclaredMethods();
		} catch (NoClassDefFoundError e) {
			CyborgLogger.log("Plugin " + plugin.getClass().getSimpleName() + " is attempting to register event " + e.getMessage() + ", which does not exist. Ignoring events registered in " + listener.getClass());
			return ret;
		}
		for (final Method method : methods) {
			final EventHandler eh = method.getAnnotation(EventHandler.class);
			if (eh == null) {
				continue;
			}
			final Class<?> checkClass = method.getParameterTypes()[0];
			Class<? extends Event> eventClass;
			if (!Event.class.isAssignableFrom(checkClass) || method.getParameterTypes().length != 1) {
				CyborgLogger.log(Level.SEVERE, "Wrong method arguments used for event type registered");
				continue;
			} else {
				eventClass = checkClass.asSubclass(Event.class);
			}
			method.setAccessible(true);
			Set<ListenerRegistration> eventSet = ret.get(eventClass);
			if (eventSet == null) {
				eventSet = new HashSet<ListenerRegistration>();
				ret.put(eventClass, eventSet);
			}
			eventSet.add(new ListenerRegistration(new EventExecutor() {
				public void execute(Event event) throws EventException {
					try {
						if (!checkClass.isAssignableFrom(event.getClass())) {
							throw new EventException("Wrong event type passed to registered method");
						}
						method.invoke(listener, event);
					} catch (Throwable t) {
						throw new EventException(t);
					}
				}
			}, eh.order(), plugin));
		}
		return ret;
	}
}

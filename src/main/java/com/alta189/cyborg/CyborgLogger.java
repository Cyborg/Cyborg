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

package com.alta189.cyborg;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class CyborgLogger {

	private static Logger logger = Logger.getLogger(Main.class.getName());

	protected static void init() {
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new CommonFormatter());
		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void log(Object obj) {

	}

	public static void log(Level lvl, Object obj) {
		System.out.println(new StringBuilder().append("[").append(lvl.name()).append("] ").append(obj).toString());
	}

	public static void log(Level lvl, Object obj, Throwable throwable) {

	}

	public enum Level {
		INFO,
		DEBUG,
		SEVERE,
		WARNING;
	}

}

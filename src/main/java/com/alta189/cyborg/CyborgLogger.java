package com.alta189.cyborg;

import java.util.logging.Logger;

public class CyborgLogger {
	
	private static Logger logger = Logger.getLogger(Main.class.getName());
	
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

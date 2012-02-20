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

import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.List;

public class StartupArguements {

	@Parameter
	private List<String> parameters = Lists.newArrayList();

	@Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
	private boolean verbose = false;
	
	@Parameter (names = { "-settings", "-config"}, description = "Sets the location of the settings.yml")
	private String settingsFile = "settings.yml";

	@Parameter (names = { "-exit", "-write-only"}, description = "Writes the settings file and exits")
	private boolean exit = false;

	@Parameter (names = { "-default", "-defaults", "-write-defaults"}, description = "Writes default settings file")
	private boolean defaults = false;
	
	public List<String> getParameters() {
		return parameters;
	}
	
	public boolean isVerbose() {
		return verbose;
	}
	
	public String getSettingsFile() {
		return settingsFile;
	}

	public boolean isExitAfterWrite() {
		return exit;
	}
	
	public boolean isWriteDefaults() {
		return defaults;
	}
}

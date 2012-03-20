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

import com.alta189.cyborg.api.terminal.TerminalThread;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.alta189.cyborg.api.util.yaml.YAMLFormat;
import com.alta189.cyborg.api.util.yaml.YAMLProcessor;
import com.beust.jcommander.JCommander;

public class Main {

	public static void main(String[] args) throws Exception {
		// Parse arguments \\
		StartupArguments params = new StartupArguments();
		new JCommander(params, args);
        StartupArguments.setInstance(params);
		
		new TerminalThread().start();
		
		CyborgLogger.init();
        CyborgLogger.log(CyborgLogger.Level.INFO, "Cyborg is starting up!");
		File settingsFile = new File("settings.yml");
		if (settingsFile.getParentFile() != null)
			settingsFile.getParentFile().mkdirs();
		if (params.isWriteDefaults())
			settingsFile.delete();
		YAMLProcessor settings = setupSettings(settingsFile);
		if (settings == null) {
			throw new NullPointerException("The YAMLProcessor object was null for settings.");
		}
		Settings.setSettings(settings);
		Cyborg cyborg = new Cyborg();
		cyborg.getPluginDirectory().mkdirs();

		if (params.isExitAfterWrite())
			System.exit(0);

		cyborg.loadPlugins();
		cyborg.enablePlugins();

		if (Settings.getServerPass() == null) {
			cyborg.connect(Settings.getServerAddress(), Settings.getServerPort());
		} else {
			cyborg.connect(Settings.getServerAddress(), Settings.getServerPort(), Settings.getServerPass());
		}
		
		if (Settings.getChannels() != null) {
			for (String channel : Settings.getChannels()) {
				cyborg.joinChannel(channel);
			}
		}
	}

	private static YAMLProcessor setupSettings(File file) {
		if (!file.exists()) {
			try {
				InputStream input = Main.class.getResource("settings.yml").openStream();
				if (input != null) {
					FileOutputStream output = null;
					try {
						if (file.getParentFile() != null)
							file.getParentFile().mkdirs();
						output = new FileOutputStream(file);
						byte[] buf = new byte[8192];
						int length;

						while ((length = input.read(buf)) > 0) {
							output.write(buf, 0, length);
						}

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							input.close();
						} catch (Exception ignored) {
						}
						try {
							if (output != null)
								output.close();
						} catch (Exception e) {
						}
					}
				}
			} catch (Exception e) {
			}
		}

		return new YAMLProcessor(file, false, YAMLFormat.EXTENDED);
	}

}

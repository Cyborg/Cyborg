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

import com.alta189.cyborg.api.command.CommandListener;
import com.alta189.cyborg.api.command.CommandManager;
import com.alta189.cyborg.api.command.CommonCommandManager;
import com.alta189.cyborg.api.command.Named;
import com.alta189.cyborg.api.command.annotation.EmptyConstructorInjector;
import com.alta189.cyborg.api.event.EventManager;
import com.alta189.cyborg.api.event.SimpleEventManager;
import com.alta189.cyborg.api.event.bot.JoinEvent;
import com.alta189.cyborg.api.event.bot.PartEvent;
import com.alta189.cyborg.api.event.bot.SendActionEvent;
import com.alta189.cyborg.api.event.bot.SendMessageEvent;
import com.alta189.cyborg.api.event.bot.SendNoticeEvent;
import com.alta189.cyborg.api.plugin.CommonPluginLoader;
import com.alta189.cyborg.api.plugin.CommonPluginManager;
import com.alta189.cyborg.api.plugin.Plugin;
import com.alta189.cyborg.api.plugin.PluginManager;
import com.alta189.cyborg.api.terminal.TerminalCommands;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.exception.IrcException;

public class Cyborg {
	private static Cyborg instance;
	private static final String newLine = System.getProperty("line.separator");
	@Getter(lazy = true)
	private final static String version = readVersion();
	private final File pluginDir = new File("plugins");
	private final CommonPluginManager pluginManager;
	private final SimpleEventManager eventManager;
	@Getter
	private final CommandManager commandManager;
	private final PircBotX bot = new PircBotX();

	public Cyborg() {
		if (instance != null) {
			throw new IllegalAccessError("There is already an instance of Cyborg!");
		}
		pluginManager = new CommonPluginManager(this);
		pluginManager.registerPluginLoader(CommonPluginLoader.class);
		eventManager = new SimpleEventManager();
		commandManager = new CommonCommandManager();

		// Register Internal Listeners
		bot.getListenerManager().addListener(new PircBotXListener());
		eventManager.registerEvents(new CommandListener(), this);
		eventManager.registerEvents(new InternalListener(), this);

		// Setup Bot \\
		bot.setVerbose(StartupArguments.getInstance().isVerbose());
		bot.setName(Settings.getNick());
		bot.setLogin(Settings.getIdent());
		setMessageDelay(Settings.getMessageDelay());

		// Register Default Commands \\
		commandManager.registerCommands(new Named() {
			@Override
			public String getName() {
				return Cyborg.class.getCanonicalName();
			}
		}, TerminalCommands.class, new EmptyConstructorInjector());

		instance = this;
	}

	private static String readVersion() {
		String version = "-1";
		try {
			version = IOUtils.toString(Main.class.getResource("version").openStream(), "UTF-8");
		} catch (Exception e) {
			// Ignored \\
		}
		if (version.equalsIgnoreCase("${build.number}")) {
			version = "custom_build";
		}
		return version;
	}

	public static Cyborg getInstance() {
		return instance;
	}

	protected final void loadPlugins() {
		if (!pluginDir.exists()) {
			pluginDir.mkdirs();
		}
		pluginManager.loadPlugins(pluginDir);
	}

	protected final void enablePlugins() {
		for (Plugin plugin : pluginManager.getPlugins()) {
			pluginManager.enablePlugin(plugin);
		}
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public PluginManager getPluginManager() {
		return pluginManager;
	}

	public File getUpdateFolder() {
		return new File(pluginDir, "updates");
	}

	public File getPluginDirectory() {
		return pluginDir;
	}

	public Set<Channel> getChannels() {
		return bot.getChannels();
	}

	public Set<Channel> getChannels(User user) {
		return bot.getChannels(user);
	}

	public Channel getChannel(String channel) {
		return bot.getChannel(channel);
	}

	public Set<String> getChannelNames() {
		return bot.getChannelsNames();
	}

	public User getUser(String user) {
		return bot.getUser(user);
	}

	public Set<User> getUsers(Channel channel) {
		return bot.getUsers(channel);
	}

	public void setMessageDelay(long delay) {
		bot.setMessageDelay(delay);
	}

	public long getMessageDelay() {
		return bot.getMessageDelay();
	}

	public void connect(String address) throws IOException, IrcException {
		bot.connect(address);
	}

	public void connect(String address, int port) throws IOException, IrcException {
		bot.connect(address, port);
	}

	public void connect(String address, int port, String pass) throws IOException, IrcException {
		bot.connect(address, port, pass);
	}

	public void quitServer() {
		bot.quitServer();
	}

	public void quitServer(String reason) {
		bot.quitServer(reason);
	}

	public void shutdown(String reason) {
		try {
			bot.quitServer(reason);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception ignored) {

		}
		bot.dispose();
		pluginManager.disablePlugins();
		Main.getTerminalThread().interrupt();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void shutdown() {
		try {
			bot.disconnect();
		} catch (Exception ignore) {

		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bot.dispose();
		pluginManager.disablePlugins();
		System.exit(0);
	}
	
	public void dispose() {
		bot.dispose();
	}

	public void joinChannel(String channel) {
		joinChannel(channel, null);
	}

	public void joinChannel(String channel, String key) {
		JoinEvent joinEvent = new JoinEvent(channel, key);
		joinEvent = eventManager.callEvent(joinEvent);
		if (!joinEvent.isCancelled()) {
			if (joinEvent.getKey() != null) {
				bot.joinChannel(joinEvent.getChannel(), joinEvent.getKey());
			} else {
				bot.joinChannel(joinEvent.getChannel());
			}
		}
	}

	public void partChannel(Channel channel) {
		partChannel(channel, null);
	}

	public void partChannel(Channel channel, String reason) {
		PartEvent partEvent = new PartEvent(channel, reason);
		partEvent = eventManager.callEvent(partEvent);
		if (!partEvent.isCancelled()) {
			if (partEvent.getReason() != null) {
				bot.partChannel(partEvent.getChannel(), partEvent.getReason());
			} else {
				bot.partChannel(channel);
			}
		}
	}

	public void sendMessage(User target, String message) {
		sendMessage(target.getNick(), message);
	}

	public void sendMessage(Channel target, String message) {
		sendMessage(target.getName(), message);
	}

	public void sendMessage(Channel target, User user, String message) {
		sendMessage(target.getName(), user.getNick() + ": " + message);
	}

	public void sendMessage(String target, String message) {
		SendMessageEvent event = new SendMessageEvent(target, message);
		event = eventManager.callEvent(event);
		if (!event.isCancelled()) {
			if (event.getMessage().contains(newLine)) {
				for (String line : event.getMessage().split(newLine)) {
					bot.sendMessage(event.getTarget(), line);
				}
			} else {
				bot.sendMessage(event.getTarget(), event.getMessage());
			}
		}
	}

	public void sendAction(User target, String action) {
		sendAction(target.getNick(), action);
	}

	public void sendAction(Channel target, String action) {
		sendAction(target.getName(), action);
	}

	public void sendAction(String target, String message) {
		SendActionEvent event = new SendActionEvent(target, message);
		event = eventManager.callEvent(event);
		if (!event.isCancelled()) {
			if (event.getAction().contains(newLine)) {
				for (String line : event.getAction().split(newLine)) {
					bot.sendAction(event.getTarget(), line);
				}
			} else {
				bot.sendAction(event.getTarget(), event.getAction());
			}
		}
	}

	public void sendNotice(User target, String action) {
		sendNotice(target.getNick(), action);
	}

	public void sendNotice(Channel target, String action) {
		sendNotice(target.getName(), action);
	}

	public void sendNotice(String target, String message) {
		SendNoticeEvent event = new SendNoticeEvent(target, message);
		event = eventManager.callEvent(event);
		if (!event.isCancelled()) {
			if (event.getNotice().contains(newLine)) {
				for (String line : event.getNotice().split(newLine)) {
					bot.sendNotice(event.getTarget(), line);
				}
			} else {
				bot.sendNotice(event.getTarget(), event.getNotice());
			}
		}
	}

	public String getNick() {
		return bot.getNick();
	}
}

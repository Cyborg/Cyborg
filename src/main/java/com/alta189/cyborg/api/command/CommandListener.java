package com.alta189.cyborg.api.command;

import com.alta189.cyborg.Cyborg;
import com.alta189.cyborg.api.event.EventHandler;
import com.alta189.cyborg.api.event.Listener;
import com.alta189.cyborg.api.event.Order;
import com.alta189.cyborg.api.event.bot.PrivateMessageEvent;
import com.alta189.cyborg.api.event.channel.MessageEvent;
import org.pircbotx.User;

public class CommandListener implements Listener {
	@EventHandler(order = Order.EARLIEST)
	public void onMessage(MessageEvent event) {
		try {
			String command = event.getMessage();

			if (!Cyborg.getInstance().getCommandManager().isCommand(command)) {
				System.out.println("Not a command");
				return; // Return if it is not a command
			}

			String data = null;
			Handle handle = null;

			int ping = command.lastIndexOf("|");
			int notice = command.lastIndexOf(">");

			if (!(ping <= 0)) {
				if (ping > notice) {
					data = command.substring(ping + 1);
					if (data != null && !data.isEmpty()) {
						data = data.replaceAll(" ", ""); // Delete spaces
						for (User user : event.getChannel().getUsers()) {
							if (user.getNick().equalsIgnoreCase(data)) { // Only if data is a user
								command = command.substring(0, ping);
								handle = Handle.PING;
								break;
							}
						}
					}
				}
			} else if (!(notice <= 0)) {
				data = command.substring(notice + 1);
				if (data != null && !data.isEmpty()) {
					data = data.replaceAll(" ", ""); // Delete spaces
					for (User user : event.getChannel().getUsers()) {
						if (user.getNick().equalsIgnoreCase(data)) { // Only if data is a user
							command = command.substring(0, notice);
							handle = Handle.NOTICE;
							break;
						}
					}
				}
			}

			String result = Cyborg.getInstance().getCommandManager().execute(new CommandSource(event.getUser()), command, CommandContext.LocationType.CHANNEL, event.getChannel().getName());
			if (result == null) {
				return;
			}

			if (handle == null) {
				Cyborg.getInstance().sendMessage(event.getChannel(), result);
			} else {
				switch (handle) {
					case PING:
						Cyborg.getInstance().sendMessage(event.getChannel(), data + ": " + result);
						break;
					case NOTICE:
						Cyborg.getInstance().sendNotice(data, result);
				}
			}
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(order = Order.EARLIEST)
	public void onPrivateMessage(PrivateMessageEvent event) {
		try {
			String result = Cyborg.getInstance().getCommandManager().execute(new CommandSource(event.getUser()), event.getMessage(), CommandContext.LocationType.PRIVATE_MESSAGE);
			if (result != null) {
				Cyborg.getInstance().sendMessage(event.getUser(), result);
			}
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}

	public enum Handle {
		PING,
		NOTICE
	}
}

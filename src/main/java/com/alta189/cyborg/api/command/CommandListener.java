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

			CommandResult result = Cyborg.getInstance().getCommandManager().execute(new CommandSource(event.getUser()), command, CommandContext.LocationType.CHANNEL, event.getChannel().getName());
			if (result == null) {
				return;
			}

			if (handle == null || result.getReturnType() != ReturnType.MESSAGE || result.isForced()) {
				switch (result.getReturnType()) {
					case ACTION:
						Cyborg.getInstance().sendAction(result.getTarget(), result.getBody());
						break;
					case MESSAGE:
						Cyborg.getInstance().sendMessage(result.getTarget(), result.getBody());
						break;
					case NOTICE:
						Cyborg.getInstance().sendNotice(result.getTarget(), result.getBody());
						break;
				}
			} else {
				switch (handle) {
					case PING:
						Cyborg.getInstance().sendMessage(event.getChannel(), data + ": " + result.getBody());
						break;
					case NOTICE:
						Cyborg.getInstance().sendNotice(data, result.getBody());
				}
			}
		} catch (CommandException e) {
			e.printStackTrace();
		}
	}

	@EventHandler(order = Order.EARLIEST)
	public void onPrivateMessage(PrivateMessageEvent event) {
		try {
			CommandResult result = Cyborg.getInstance().getCommandManager().execute(new CommandSource(event.getUser()), event.getMessage(), CommandContext.LocationType.PRIVATE_MESSAGE);
			if (result != null) {
				Cyborg.getInstance().sendMessage(result.getTarget(), result.getBody());
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

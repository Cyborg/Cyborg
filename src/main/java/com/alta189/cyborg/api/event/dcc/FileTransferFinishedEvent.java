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

package com.alta189.cyborg.api.event.dcc;

import com.alta189.cyborg.api.event.Event;
import com.alta189.cyborg.api.event.HandlerList;

import lombok.Getter;
import org.pircbotx.DccFileTransfer;

public class FileTransferFinishedEvent extends Event {

	private static HandlerList handlers = new HandlerList();
	@Getter
	private final DccFileTransfer transfer;
	@Getter
	private final Exception exception;
	@Getter
	private final long timestamp;
	
	public FileTransferFinishedEvent(org.pircbotx.hooks.events.FileTransferFinishedEvent event) {
		this(event.getTransfer(), event.getException(), event.getTimestamp());
	}
	
	public FileTransferFinishedEvent(DccFileTransfer transfer, Exception exception) {
		this(transfer, exception, System.currentTimeMillis());
	}

	public FileTransferFinishedEvent(DccFileTransfer transfer, Exception exception, long timestamp) {
		this.transfer = transfer;
		this.exception = exception;
		this.timestamp = timestamp;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}

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
package com.alta189.cyborg;

import com.alta189.cyborg.api.event.EventHandler;
import com.alta189.cyborg.api.event.Listener;
import com.alta189.cyborg.api.event.Order;
import com.alta189.cyborg.api.event.bot.ConnectEvent;

public class InternalListener implements Listener {
	@EventHandler(order = Order.EARLIEST)
	public void onConnect(ConnectEvent event) {
		System.out.println("Connect!@#$%^&*()(*&^%$#@#$%^&*()(*&^%$#@#$%^&*((*&^%$#@#$%^&*(*&^%$#@#$%^&*(*&^%$#$%^&*(*&^%$#$%^&*&^%$#$%^&*(*&^%$%^&*(*&^%$#%^&*(*&^%$%^&*(*&^%$%^&*(*&^%$#%^&*(*&^%$#%^&*(&^%$%^&*(&^%$#$%^&*(*&^%$#$%^&");
		System.out.println(Settings.getChannels());
		for (String channel : Settings.getChannels()) {
			Cyborg.getInstance().joinChannel(channel);
		}
	}
}

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

package com.alta189.cyborg.api.database;

import com.alta189.cyborg.Settings;
import com.mongodb.DB;
import com.mongodb.Mongo;
import lombok.Getter;

import java.net.UnknownHostException;

public class MongoDatabase {

	@Getter
	private Mongo mongo;
	@Getter
	private DB db;
	
	public MongoDatabase() {
		try {
			mongo = newMongo();
			db = mongo.getDB("cyborg");
		} catch (UnknownHostException e) {
			e.printStackTrace();
			mongo = null;
		}
	}
	
	private Mongo newMongo() throws UnknownHostException {
		if (Settings.getDatabaseHost() != null) {
			if (Settings.getDatabasePort() > 0) {
				return new Mongo(Settings.getDatabaseHost(), Settings.getDatabasePort());
			} else {
				return new Mongo(Settings.getDatabaseHost());
			}
		}
		return null;
	}
	
}

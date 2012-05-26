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
package com.alta189.cyborg.api.util;

import java.util.Collection;
import java.util.regex.Pattern;

public class CollectionUtil {
	public static boolean contains(Collection<String> collection, String regex) {
		Pattern pattern = Pattern.compile(regex);

		for (String s : collection) {
			if (pattern.matcher(s).matches()) {
				return true;
			}
		}

		return false;
	}

	public static boolean reverseContains(Collection<String> regexes, String data) {
		for (String regex : regexes) {
			if (Pattern.compile(regex).matcher(data).matches()) {
				return true;
			}
		}

		return false;
	}
}

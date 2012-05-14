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
package com.alta189.cyborg.api.util;

public class StringUtils {
	public static String toString(String[] array) {
		return toString(array, 0);
	}

	public static String toString(String[] array, String separator) {
		return toString(array, 0, separator);
	}

	public static String toString(String[] array, int offset) {
		return toString(array, offset, " ");
	}

	public static String toString(String[] array, int offset, String separator) {
		return toString(array, offset, array.length - 1, separator);
	}

	public static String toString(String[] array, int offset, int end, String separator) {
		StringBuilder builder = new StringBuilder();
		for (int i = offset; i <= end; i++) {
			builder.append(array[i]);
			if (i != end)
				builder.append(separator);
		}
		return builder.toString();
	}
}

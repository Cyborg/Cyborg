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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtils {
	/**
	 * Get all the public fields in a class, as well as those in its superclasses
	 * @param clazz		 The class to get all fields in
	 * @param includeObject Whether to include fields in {@link Object}
	 * @return The fields in the class
	 * @see Class#getFields()
	 */
	private static List<Field> getFieldsRecur(Class<?> clazz, boolean includeObject) {
		List<Field> fields = new ArrayList<Field>();
		while (clazz != null && (includeObject || !Object.class.equals(clazz))) {
			fields.addAll(Arrays.asList(clazz.getFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

	/**
	 * Get all the fields in a class, as well as those in its superclasses (excluding {@link Object})
	 * @param clazz The class to get all fields in
	 * @return The fields in the class
	 */
	public static List<Field> getDeclaredFieldsRecur(Class<?> clazz) {
		return getFieldsRecur(clazz, false);
	}

	/**
	 * Get all the fields in a class, as well as those in its superclasses
	 * @param clazz		 The class to get all fields in
	 * @param includeObject Whether to include fields in {@link Object}
	 * @return The fields in the class
	 * @see Class#getDeclaredFields()
	 */
	public static List<Field> getDeclaredFieldsRecur(Class<?> clazz, boolean includeObject) {
		List<Field> fields = new ArrayList<Field>();
		while (clazz != null && (includeObject || !Object.class.equals(clazz))) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}
}
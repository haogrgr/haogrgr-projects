package com.haogrgr.mybatis.generator.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Reflects {

	public static void setField(Object target, String fieldName, Object value) {
		try {
			Field field = field(target, fieldName);
			field.set(target, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getField(Object target, String fieldName, Class<T> clazz) {
		try {
			Field field = field(target, fieldName);
			Object value = field.get(target);
			return (T) value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Field field(Object target, String fieldName) {
		try {
			Field field = target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static List<Field> fields(Object target, String... fieldNames) {
		try {
			List<Field> fields = new ArrayList<>();
			for (String fieldName : fieldNames) {
				fields.add(field(target, fieldName));
			}
			return fields;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}

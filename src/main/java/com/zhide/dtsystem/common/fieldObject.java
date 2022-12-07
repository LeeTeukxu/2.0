package com.zhide.dtsystem.common;

import com.google.common.base.Strings;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class fieldObject {
    public static void setValue(Object instance, Field field, Object value) throws Exception {
        if (instance == null || field == null || value == null) return;
        String valueText = value.toString();
        if (Strings.isNullOrEmpty(valueText)) return;
        Class<?> fieldType = field.getType();
        if (fieldType == int.class || fieldType == Integer.class) {
            value = Integer.parseInt(valueText);
        } else if (fieldType == boolean.class || fieldType == Boolean.class) {
            value = Boolean.parseBoolean(valueText);
        } else if (fieldType == float.class || value == Float.class) {
            value = Float.parseFloat(valueText);
        } else if (fieldType == Long.class || fieldType == long.class) {
            value = Long.parseLong(valueText);
        } else if (fieldType == Double.class || fieldType == double.class) {
            value = Double.parseDouble(valueText);
        } else if (fieldType == Date.class) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            valueText = valueText.replace("T", " ");
            value = f.parse(valueText);
        } else if (fieldType == String.class) {
            value = valueText;
        }
        field.setAccessible(true);
        field.set(instance, value);
    }

    public static boolean exist(Object instance, String key) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            if (name.toLowerCase().equals(key.toLowerCase())) return true;
        }
        return false;
    }

    public static Field findByName(Object instance, String key) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            if (name.toLowerCase().equals(key.toLowerCase())) return field;
        }
        return null;
    }
}

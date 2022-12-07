package com.zhide.dtsystem.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntityHelper {
    static Logger logger = LoggerFactory.getLogger(EntityHelper.class);

    public static Map<String, Object> toMap(Object entityObj) throws IllegalAccessException {
        Map<String, Object> res = new HashMap<>();
        if (entityObj == null) return res;
        Field[] fields = entityObj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers()) == true) continue;
            ;
            String fieldName = field.getName();
            Object OO = field.get(entityObj);
            res.put(fieldName, OO);
        }
        return res;
    }

    public static <T> T copyObject(Object sourceObject, Class<T> classType) throws Exception {
        T t = classType.newInstance();
        Class<?> sourceTypes = sourceObject.getClass();
        Class<?> targetTypes = t.getClass();
        Field[] sourceFields = sourceTypes.getDeclaredFields();
        Field[] targetFields = targetTypes.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            String fieldName = sourceField.getName().toLowerCase();
            Optional<Field> ttargetField = Arrays.stream(targetFields)
                    .filter(f -> f.getName().toLowerCase().equals(fieldName))
                    .findFirst();
            if (ttargetField.isPresent() == true) {
                sourceField.setAccessible(true);
                Field targetField = ttargetField.get();
                targetField.setAccessible(true);
                Object sourceValue = sourceField.get(sourceObject);
                targetField.set(t, sourceValue);
            }
        }
        return t;
    }

    public static <T> T copyFrom(Map<String, Object> source, Class<T> classType) throws Exception {
        T t = classType.newInstance();
        Class<?> targetTypes = t.getClass();
        Field[] targetFields = targetTypes.getDeclaredFields();
        for (String fieldName : source.keySet()) {
            Optional<Field> ttargetField = Arrays.stream(targetFields)
                    .filter(f -> f.getName().toLowerCase().equals(fieldName.toLowerCase()))
                    .findFirst();
            if (ttargetField.isPresent() == true) {
                Object sourceValue = source.get(fieldName);
                if (sourceValue != null) {
                    Field targetField = ttargetField.get();
                    targetField.setAccessible(true);
                    Class<?> targetClass = targetField.getType();
                    if (targetClass == Date.class) {
                        String SX = sourceValue.toString().replace("T", " ");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        if (SX.indexOf(":") == -1) {
                            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        }
                        sourceValue = simpleDateFormat.parse(SX);
                    } else if (targetClass == Integer.class || targetClass == int.class) {
                        sourceValue = Integer.parseInt(sourceValue.toString());
                    } else if (targetClass == boolean.class || targetClass == Boolean.class) {
                        sourceValue = Boolean.parseBoolean(sourceValue.toString());
                    } else if (targetClass == double.class ||
                            targetClass == float.class ||
                            targetClass == long.class ||
                            targetClass == Double.class ||
                            targetClass == Float.class ||
                            targetClass == Long.class) {
                        sourceValue = Double.parseDouble(source.toString());
                    } else if (targetClass == String.class) {
                        sourceValue = sourceValue.toString();
                    }
                    targetField.set(t, sourceValue);
                }
            }
        }
        return t;
    }

    public static void copyObject(Object source, Object target) throws Exception {
        if (source == null || target == null) return;
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        for (int i = 0; i < sourceFields.length; i++) {
            Field sourceField = sourceFields[i];
            String sourceName = sourceField.getName().toLowerCase();
            Optional<Field> ftargetField = Arrays.stream(targetFields)
                    .filter(f -> f.getName().toLowerCase().equals(sourceName)).findFirst();
            if (ftargetField.isPresent()) {
                Field targetField = ftargetField.get();
                sourceField.setAccessible(true);
                Object sourceValue = sourceField.get(source);
                if (sourceValue == null) continue;
                targetField.setAccessible(true);
                targetField.set(target, sourceValue);
            }
        }
    }

    public static <T> T parseFrom(Map<String, Object> objectMap, Class<T> tClass) throws IllegalAccessException,
            InstantiationException, InvocationTargetException {
        T t = tClass.newInstance();
        Class<?> tt = t.getClass();
        Map<String, Method> setMethodHash = new HashMap<>();
        Method[] Ms = tt.getMethods();
        Arrays.stream(Ms).filter(f -> f.getName().startsWith("set")).forEach(f -> {
            Parameter fs = f.getParameters()[0];
            setMethodHash.put(fs.getName(), f);
        });
        for (String Key : objectMap.keySet()) {
            Object value = objectMap.get(Key);
            if (value == null) continue;
            Method setMethd = setMethodHash.get(Key);
            if (setMethd != null) {
                setMethd.setAccessible(true);
                setMethd.invoke(t, value);
            }
        }
        return t;
    }

    public static boolean changeValue(Object instance, String field, Object val) throws InvocationTargetException,
            IllegalAccessException {
        if (instance == null) return false;
        Method[] ms = instance.getClass().getMethods();
        Optional<Method> findMethod = Arrays.stream(ms).filter(f -> {
            String methodName = f.getName();
            if (methodName.length() <= 3) return false;
            if (methodName.toLowerCase().startsWith("set") == false) return false;
            String pureName = methodName.substring(3);
            return pureName.toLowerCase().equals(field.toLowerCase());
        }).findFirst();
        if (findMethod.isPresent()) {
            Method method = findMethod.get();
            method.invoke(instance, val);
            return true;
        } else return false;
    }
}

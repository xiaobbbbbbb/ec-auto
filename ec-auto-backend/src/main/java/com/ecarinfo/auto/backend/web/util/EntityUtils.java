package com.ecarinfo.auto.backend.web.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author zhanglu
 *
 */
public class EntityUtils {
	private static final Logger logger = Logger.getLogger(EntityUtils.class);
	
	public static List<Object> getOneFieldValues(Collection<?> entitys, String fieldName) {
		List<Object> values = new ArrayList<Object>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (Object object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					values.add(field.get(object));
				}
			}	
		} catch (Exception e) {
			try {
				for (Object object : entitys) {
					Class<?> c = object.getClass().getSuperclass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					values.add(field.get(object));
				}
			} catch (Exception e2) {
				logger.error("getOneFieldValues..", e);
			}			
		}			
		return values;		
	}
	
	public static <T> List<T> getOneFieldValues(Collection<?> entitys, String fieldName, Class<T> clazz) {
		List<T> values = new ArrayList<T>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (Object object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					values.add((T) field.get(object));
				}
			}	
		} catch (Exception e) {
			try {
				for (Object object : entitys) {
					Class<?> c = object.getClass().getSuperclass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					values.add((T) field.get(object));
				}
			} catch (Exception e2) {
				logger.error("getOneFieldValues..", e);
			}			
		}			
		return values;		
	}
	
	public static  <T>  Map<Object,T> getField2EntityMap(List<T> entitys, String fieldName) {
		Map<Object, T> map = new HashMap<Object, T>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (T object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					map.put(field.get(object), object);
				}
			}	
		} catch (Exception e) {
			try {
				for (T object : entitys) {
					Class<?> c = object.getClass().getSuperclass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					map.put(field.get(object), object);
				}
			} catch (Exception e2) {
				logger.error("getOneFieldValues..", e);
			}	
		}
		return map;
	} 
	
	public static  <T,T2>   Map<T2,T> getField2EntityMap(List<T> entitys, String fieldName, Class<T2> clazz) {
		Map<T2, T> map = new HashMap<T2, T>();
		try {
			if (!CollectionUtils.isEmpty(entitys)) {
				for (T object : entitys) {
					Class<?> c = object.getClass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					map.put((T2) field.get(object), object);
				}
			}	
		} catch (Exception e) {
			try {
				for (T object : entitys) {
					Class<?> c = object.getClass().getSuperclass();
					Field field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					map.put((T2) field.get(object), object);
				}
			} catch (Exception e2) {
				logger.error("getOneFieldValues..", e);
			}	
		}
		return map;
	} 
}

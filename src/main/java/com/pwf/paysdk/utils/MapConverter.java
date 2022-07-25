package com.pwf.paysdk.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pwf.paysdk.base.NameInMap;

import java.util.Map.Entry;


public class MapConverter {

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> merge(Class<T> t, Map<String, ?>... maps) {
        Map<String, T> out = new HashMap<String, T>();
        for (int i = 0; i < maps.length; i++) {
            Map<String, ?> map = maps[i];
            if (null == map) {
                continue;
            }
            Set<? extends Entry<String, ?>> entries = map.entrySet();
            for (Entry<String, ?> entry : entries) {
                if (null != entry.getValue()) {
                    out.put(entry.getKey(), (T) entry.getValue());
                }
            }
        }
        return out;
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> merge(Class<T> t, Object... maps) {
        Map<String, T> out = new HashMap<String, T>();
        Map<String, ?> map = null;
        for (int i = 0; i < maps.length; i++) {
            if (null == maps[i]) {
                continue;
            }

            map = (Map<String, T>) maps[i];

            Set<? extends Entry<String, ?>> entries = map.entrySet();
            for (Entry<String, ?> entry : entries) {
                if (null != entry.getValue()) {
                    out.put(entry.getKey(), (T) entry.getValue());
                }
            }
        }
        return out;
    }

    public static Map<String, Object> getSortedMap(Map<String, ?> params) throws Exception {
    	TreeMap<String, Object> sortedMap = new TreeMap<String, Object>(new Comparator<String>() {
            public int compare(String obj1, String obj2) {
                return obj1.compareTo(obj2);//升序排序
            }
        });
    	sortedMap.putAll(params);
		return sortedMap;
	}
    
    
    public static String toJsonString(Map<String, ?> input) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(input);
    }
    
    public static Map<String, Object> objectToMap(Object object) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            for (Field field : object.getClass().getFields()) {
                NameInMap anno = field.getAnnotation(NameInMap.class);
                String key;
                if (anno == null) {
                    key = field.getName();
                } else {
                    key = anno.value();
                }

                map.put(key, field.get(object));
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return map;
    }
}

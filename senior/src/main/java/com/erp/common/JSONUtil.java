package com.erp.common;


import org.codehaus.jackson.map.ObjectMapper;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JSONUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private JSONUtil(){

    }

    public static ObjectMapper getInstance(){
        return objectMapper;
    }


    public static String obj2json(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {

        }
        return "{}";
    }


    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {

        }
        return null;
    }


    public static <T> Map<String, Object> json2map(String jsonStr) {
        try {
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (Exception e) {

        }
        return null;
    }

    public static Map<String,Object> object2map(Object object) throws Exception{
        Map<String,Object> map = new HashMap<>();
        Class<?> clazz = object.getClass();
        for(Field field : clazz.getDeclaredFields()){
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = field.get(object);
            map.put(fieldName,value);
        }
        return map;
    }

}

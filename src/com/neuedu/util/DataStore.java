package com.neuedu.util;

import java.util.HashMap;
import java.util.Map;

public class DataStore {

    private static final Map<String, Object> map = new HashMap<>();

    public static void put (String key, Object value){
        map.put(key, value);
    }

    public static <T> T get(String key){
        return (T) map.get(key);
    }


}

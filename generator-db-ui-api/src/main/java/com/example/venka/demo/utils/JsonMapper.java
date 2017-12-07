package com.example.venka.demo.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public final class JsonMapper {
    private static final Gson GSON = new Gson();
    private static final Type type = new TypeToken<Map<String, Object>>() {
        // empty
    }.getType();

    private JsonMapper() {
        // empty
    }

    public static Map<String, String> toMap(final String jsonString) {
        return GSON.fromJson(jsonString, type);
    }
}
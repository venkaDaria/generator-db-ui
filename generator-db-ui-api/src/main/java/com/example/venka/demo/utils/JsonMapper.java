package com.example.venka.demo.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.venka.demo.utils.Replaces.START_TAB;

public final class JsonMapper {
    private static final Gson GSON = new Gson();
    private static final Type type = new TypeToken<Map<String, Object>>() {
        // empty
    }.getType();

    private JsonMapper() {
        // empty
    }

    public static Map<String, Object> toMap(final String jsonString) {
        return GSON.fromJson(jsonString, type);
    }

    @SuppressWarnings("unchecked")
    public static List<LinkedTreeMap<String, Object>> filterBounds(final Map<String, Object> body, final String name) {
        return ((ArrayList<LinkedTreeMap<String, Object>>) body.get("bounds"))
                .stream()
                .filter(field -> field.get("option1").equals(name) || field.get("option2").equals(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<LinkedTreeMap<String, Object>> filterFields(final Map<String, Object> body, final String name) {
        return ((ArrayList<LinkedTreeMap<String, Object>>) body.get("fields"))
                .stream()
                .filter(field -> field.get("parent").equals(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static String toCode(final Set<JsonObject> paths) {
        final StringBuilder sb = new StringBuilder();

        paths.forEach(path -> {
            final String name = path.get("name").getAsString();

            sb.append(System.lineSeparator());
            sb.append(START_TAB).append(String.format("final JsonObject %sObject = new JsonObject();", name))
                    .append(System.lineSeparator());
            sb.append(START_TAB).append(String.format("%1$sObject.addProperty(\"name\", \"%1$s\");", name))
                    .append(System.lineSeparator());
            sb.append(START_TAB).append(String.format("%sObject.addProperty(\"href\", \"%s\");", name,
                    path.get("href").getAsString())).append(System.lineSeparator());
            sb.append(START_TAB).append(String.format("PATHS.add(%sObject);", name)).append(System.lineSeparator());
        });

        return sb.toString();
    }
}
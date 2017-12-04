package com.example.venka.lab5.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

public abstract class BaseEntity {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MMM/uuuu");

    public static Field[] getHeaders(final Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .toArray(Field[]::new);
    }

    public static String[] getFields(final Class entity, final Function<? super Field, ?> mapper) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> Modifier.isPrivate(field.getModifiers()))
                .map(mapper)
                .toArray(String[]::new);
    }

    public String[] getFields() {
        return getFields(getClass(), this::getFieldValue);
    }

    @Nullable
    private Object getFieldValue(final Field field, final boolean toString) {
        try {
            field.setAccessible(true);
            return parse(field.get(this), toString);
        } catch (final IllegalAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Object getFieldValue(final Field field) {
        return getFieldValue(field, true);
    }

    @Contract("null, _ -> !null")
    private Object parse(final Object object, final boolean toString) {
        if (object == null || object instanceof Set && ((Set) object).isEmpty()) {
            return " — ";
        } else if (object instanceof LocalDateTime) {
            return FMT.format((LocalDateTime) object);
        } else {
            return toString ? object.toString() : object;
        }
    }

    public boolean isSelected(final Field header, final BaseEntity choice) {
        final Object field = getFieldValue(header, false);
        return field != null && !field.equals(" — ") && ((Set) field).contains(choice);
    }
}
package com.example.venka.lab5.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTransformer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parse(final String dateStart) {
        return LocalDate.parse(dateStart, formatter);
    }
}

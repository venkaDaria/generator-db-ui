package com.example.venka.lab5.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTransformer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate parse(final String dateStart) {
        return LocalDate.parse(dateStart, formatter);
    }

    public static LocalDateTime parseWithTime(final String dateStart) {
        return LocalDateTime.parse(dateStart.replace("T", " "), formatterWithTime);
    }
}

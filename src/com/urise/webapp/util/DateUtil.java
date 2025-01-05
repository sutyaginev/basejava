package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parseDate(String date) {
        if (ResumeUtil.isEmpty(date)) {
            return NOW;
        } else {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        } else {
            return date.equals(NOW) ? "н.в." : date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}

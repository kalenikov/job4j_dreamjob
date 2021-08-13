package ru.job4j.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtil {

    // DB doesn't support LocalDate.MIN/MAX
    private static final LocalDateTime MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0);

    public static LocalDate strToLocalDate(String str) {
        return str != null && !str.isEmpty() ? LocalDate.parse(str) : null;
    }

    public static Timestamp localDateTimeToTimestamp(LocalDateTime date) {
        return date != null ? Timestamp.valueOf(date) : null;
    }

    public static LocalDateTime atStartOfDayOrMin(LocalDate date) {
        return date != null ? date.atStartOfDay() : MIN_DATE;
    }

    public static LocalDateTime atStartOfNextDayOrMax(LocalDate date) {
        return date != null ? date.plusDays(1).atStartOfDay() : MAX_DATE;
    }
}

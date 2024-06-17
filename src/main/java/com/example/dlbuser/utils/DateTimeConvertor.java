package com.example.dlbuser.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeConvertor {

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ZoneId timeZone = ZoneId.of("Asia/Seoul");

    public static String longToString(Long milli) {
        LocalDateTime scheduleDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(milli),
                timeZone);

        return scheduleDateTime.format(dateTimeFormatter);
    }

    public static Long stringToLong(String datetime) {

        return LocalDateTime.parse(datetime, dateTimeFormatter).atZone(timeZone)
                .toEpochSecond();

    }

}

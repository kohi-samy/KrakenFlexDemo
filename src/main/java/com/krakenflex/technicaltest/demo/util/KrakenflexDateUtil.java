package com.krakenflex.technicaltest.demo.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class KrakenflexDateUtil {


    public static ZonedDateTime parseZoneDate(String date){
        try {
            DateTimeFormatter zdtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            return ZonedDateTime.parse(date,zdtf);
        }catch (DateTimeParseException e){
            log.error("Error while parsing the date", e);
        }
        return null;
    }

    public static String formatDate(ZonedDateTime date){
        try {
            DateTimeFormatter zdtf = DateTimeFormatter.ISO_ZONED_DATE_TIME;
            return zdtf.format(date);
        }catch (DateTimeParseException e){
            log.error("Error while parsing the date", e);
        }
        return null;
    }
}

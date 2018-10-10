package com.epam.conference.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateTimeConverter {
   
    private static final Logger LOGGER = LogManager
	    .getLogger(DateTimeConverter.class);

    private static final String TIME_ZONE = "+3";
    private static final int FACTOR = 1000;
    
    public static LocalDateTime convertFromLong(long time) {
	return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of(TIME_ZONE));
    }

    public static long convertToLong(String date) {
	long result = 0;
	try {
	    result = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date)
		    .getTime() / FACTOR;
	} catch (ParseException e) {
	    LOGGER.error("Fail to parse date=" + date, e);
	}
	return result;
    }

    public static long convertToLong(String template, String date) {
	long result = 0;
	try {
	    result = new SimpleDateFormat(template).parse(date).getTime();
	} catch (ParseException e) {
	    LOGGER.error("Fail to parse date=" + date, e);
	}
	return result;
    }

    public static long convertToLong(LocalDateTime date) {
	return date.toEpochSecond(ZoneOffset.of(TIME_ZONE));
    }
}

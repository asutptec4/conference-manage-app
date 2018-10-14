package com.epam.conference.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Utility class to convert date and time to long and back.
 * 
 * @author Alexander Shishonok
 *
 */
public final class DateTimeConverter {

    private static final int FACTOR = 1000;
    private static final String TEMPLATE = "yyyy-MM-dd'T'HH:mm";
    private static final String TIME_ZONE = "+3";

    private DateTimeConverter() {
    }

    /**
     * Convert date in long to {@code LocalDateTime} object.
     * 
     * @param time
     *            time in second.
     * @return an {@code LocalDateTime} object.
     */
    public static LocalDateTime convertFromLong(long time) {
	return LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.of(TIME_ZONE));
    }

    /**
     * Method converts the string to time in long using template like format of
     * input html tag with attribute "datetime-local".
     * 
     * @param date
     *            string represents date.
     * @return time in second.
     */
    public static long convertToLong(String date) {
	return convertToLong(TEMPLATE, date);
    }

    /**
     * Method converts the string matching the pattern describing the date and
     * time format to time in long.
     * 
     * @param template
     *            the pattern describing the date and time.
     * @param date
     *            string represents date.
     * @return time in second. Return 0 if the date does not match the pattern.
     */
    public static long convertToLong(String template, String date) {
	long result;
	try {
	    result = new SimpleDateFormat(template).parse(date).getTime()
		    / FACTOR;
	} catch (ParseException e) {
	    result = 0;
	}
	return result;
    }

    /**
     * Convert {@code LocalDateTime} object to date in long.
     * 
     * @param date
     *            an {@code LocalDateTime} object.
     * @return time in second.
     */
    public static long convertToLong(LocalDateTime date) {
	return date.toEpochSecond(ZoneOffset.of(TIME_ZONE));
    }

    /**
     * Return current time in second.
     * 
     * @return time in second.
     */
    public static long now() {
	return System.currentTimeMillis() / FACTOR;
    }
}
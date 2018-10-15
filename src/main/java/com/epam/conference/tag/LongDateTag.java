package com.epam.conference.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.epam.conference.util.DateTimeConverter;

/**
 * Simple tag handler. Used for converting date in long to date in string
 * representation.
 * 
 * @author Alexander Shishonok
 */
public class LongDateTag extends SimpleTagSupport {

    private static final String DATE_DELIM = "/";
    private static final int DEC_BASE = 10;
    private static final String DEF_DELIM = "-";
    private static final String FORMAT_DATE = "date";
    private static final String FORMAT_DATETIME = "datetime";
    private static final String FORMAT_HTML = "html";
    private static final String FORMAT_TIME = "time";
    private static final String NUMBER = "\\d+";
    private static final String SPACE = " ";
    private static final String T_DELIM = "T";
    private static final String TIME_DELIM = ":";
    private static final String ZERO = "0";

    /**
     * Tag attribute format.
     */
    private String format;

    public void setFormat(String format) {
	this.format = format;
    }

    public String getFormat() {
	return format;
    }

    @Override
    public void doTag() throws JspException, IOException {
	String time;
	try (StringWriter sw = new StringWriter()) {
	    getJspBody().invoke(sw);
	    time = sw.toString();
	}
	String result;
	if (time != null && !time.isEmpty() && time.matches(NUMBER)) {
	    LocalDateTime dateTime = DateTimeConverter
		    .convertFromLong(Long.parseLong(time));
	    int day = dateTime.getDayOfMonth();
	    int month = dateTime.getMonthValue();
	    int year = dateTime.getYear();
	    int hour = dateTime.getHour();
	    int minute = dateTime.getMinute();
	    int second = dateTime.getSecond();
	    if (FORMAT_DATE.equals(format)) {
		result = (day < DEC_BASE ? ZERO + day : day) + DATE_DELIM
			+ (month < DEC_BASE ? ZERO + month : month) + DATE_DELIM
			+ year;
	    } else if (FORMAT_DATETIME.equals(format)) {
		result = (day < DEC_BASE ? ZERO + day : day) + DATE_DELIM
			+ (month < DEC_BASE ? ZERO + month : month) + DATE_DELIM
			+ year + SPACE + (hour < DEC_BASE ? ZERO + hour : hour)
			+ TIME_DELIM
			+ (minute < DEC_BASE ? ZERO + minute : minute)
			+ TIME_DELIM
			+ (second < DEC_BASE ? ZERO + second : second);
	    } else if (FORMAT_TIME.equals(format)) {
		result = (hour < DEC_BASE ? ZERO + hour : hour) + TIME_DELIM
			+ (minute < DEC_BASE ? ZERO + minute : minute)
			+ TIME_DELIM
			+ (second < DEC_BASE ? ZERO + second : second);
	    } else if (FORMAT_HTML.equals(format)) {
		result = year + DEF_DELIM
			+ (month < DEC_BASE ? ZERO + month : month) + DEF_DELIM
			+ (day < DEC_BASE ? ZERO + day : day) + T_DELIM
			+ (hour < DEC_BASE ? ZERO + hour : hour) + TIME_DELIM
			+ (minute < DEC_BASE ? ZERO + minute : minute);
	    } else {
		result = (day < DEC_BASE ? ZERO + day : day) + SPACE
			+ dateTime.getMonth().toString().toLowerCase() + SPACE
			+ dateTime.getYear();
	    }
	} else {
	    result = "";
	}
	getJspContext().getOut().write(result);
    }
}
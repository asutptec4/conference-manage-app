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

    private static final String NUMBER = "\\d+";
    private static final String FORMAT_DATE = "date";
    private static final String FORMAT_DATETIME = "datetime";
    private static final String FORMAT_TIME = "time";
    private static final String FORMAT_HTML = "html";

    private String format;

    public void setFormat(String format) {
	this.format = format;
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
		result = (day < 10 ? "0" + day : day) + "/"
			+ (month < 10 ? "0" + month : month) + "/" + year;
	    } else if (FORMAT_DATETIME.equals(format)) {
		result = (day < 10 ? "0" + day : day) + "/"
			+ (month < 10 ? "0" + month : month) + "/" + year + " "
			+ (hour < 10 ? "0" + hour : hour) + ":"
			+ (minute < 10 ? "0" + minute : minute) + ":"
			+ (second < 10 ? "0" + second : second);
	    } else if (FORMAT_TIME.equals(format)) {
		result = (hour < 10 ? "0" + hour : hour) + ":"
			+ (minute < 10 ? "0" + minute : minute) + ":"
			+ (second < 10 ? "0" + second : second);
	    } else if (FORMAT_HTML.equals(format)) {
		result = year + "-" + (month < 10 ? "0" + month : month) + "-"
			+ (day < 10 ? "0" + day : day) + "T"
			+ (hour < 10 ? "0" + hour : hour) + ":"
			+ (minute < 10 ? "0" + minute : minute);
	    } else {
		result = (day < 10 ? "0" + day : day) + " "
			+ dateTime.getMonth().toString().toLowerCase() + " "
			+ dateTime.getYear();
	    }
	} else {
	    result = "";
	}
	getJspContext().getOut().write(result);
    }
}

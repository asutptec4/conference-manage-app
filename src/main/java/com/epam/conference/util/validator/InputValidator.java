package com.epam.conference.util.validator;

import java.util.regex.Pattern;

import com.epam.conference.util.DateTimeConverter;

public final class InputValidator {

    private static final int CONF_DESC_MAXLEN = 255;
    private static final int CONF_NAME_MAXLEN = 100;
    private static final int EMAIL_MAXLEN = 60;
    private static final int LOCAT_MAXLEN = 255;
    private static final int LOGIN_MAXLEN = 40;
    private static final int MESS_MAXLEN = 255;
    private static final int NAME_MAXLEN = 40;
    private static final int PASS_MAXLEN = 40;
    private static final int PASS_MINLEN = 0;
    private static final int PHONE_MAXLEN = 30;
    private static final String REGEXP_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private static final String REGEXP_PHONE = "([+][\\d]+)";
    private static final String REGEXP_SCRIPT = "</?script>";
    private static final String REGEXP_USER = "[\\p{Alpha}][\\p{Alnum}]{4,39}";
    private static final int REP_DESC_MAXLEN = 600;
    private static final int REP_NAME_MAXLEN = 200;
    private static final String SCRIPT_REPLACE = "";
    private static final int SECT_DESC_MAXLEN = 255;
    private static final int SECT_NAME_MAXLEN = 100;

    private InputValidator() {
    }

    public static String removeScript(String str) {
	return str != null && !str.isEmpty()
		? str.replaceAll(REGEXP_SCRIPT, SCRIPT_REPLACE)
		: str;
    }

    public static boolean validateConference(String name, String location,
	    String startDate, String endDate, String description) {
	if (name != null && location != null && startDate != null
		&& endDate != null) {
	    boolean result = true;
	    result = result && name.length() < CONF_NAME_MAXLEN
		    && location.length() < LOCAT_MAXLEN
		    && (description != null
			    ? description.length() < CONF_DESC_MAXLEN
			    : true);
	    // TODO better time validation
	    result = DateTimeConverter.convertToLong(
		    startDate) < DateTimeConverter.convertToLong(endDate);
	    return result;
	} else {
	    return false;
	}
    }

    public static boolean validateMessage(String text) {
	return (text != null) ? text.length() < MESS_MAXLEN : false;
    }

    public static boolean validatePassword(String password) {
	return password != null
		? password.length() > PASS_MINLEN
			&& password.length() < PASS_MAXLEN
		: false;
    }

    public static boolean validateReport(String name, String description) {
	return (name != null ? name.length() < REP_NAME_MAXLEN : false)
		&& (description != null ? description.length() < REP_DESC_MAXLEN
			: false);
    }

    public static boolean validateSection(String name, String description) {
	return (name != null ? name.length() < SECT_NAME_MAXLEN : false)
		&& (description != null
			? description.length() < SECT_DESC_MAXLEN
			: true);
    }

    public static boolean validateUser(String login, String firstName,
	    String lastName, String email, String phone) {
	if (login != null && firstName != null && lastName != null
		&& email != null && phone != null) {
	    boolean result = true;
	    result = result && login.length() < LOGIN_MAXLEN
		    && Pattern.matches(REGEXP_USER, login);
	    result = result && firstName.length() < NAME_MAXLEN
		    && lastName.length() < NAME_MAXLEN;
	    result = result && email.length() < EMAIL_MAXLEN
		    && Pattern.matches(REGEXP_EMAIL, email);
	    result = result && phone.length() < PHONE_MAXLEN
		    && Pattern.matches(REGEXP_PHONE, phone);
	    return result;
	} else {
	    return false;
	}
    }

    public static boolean validateUserName(String userName) {
	return userName != null ? Pattern.matches(REGEXP_USER, userName)
		: false;
    }
}
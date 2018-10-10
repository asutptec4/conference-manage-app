package com.epam.conference.util.validator;

import java.util.regex.Pattern;

import com.epam.conference.util.DateTimeConverter;

public class InputValidator {

    private static final String REGEXP_USER = "[\\p{Alpha}][\\p{Alnum}]{4,39}";
    private static final String REGEXP_EMAIL = "(\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6})";
    private static final String REGEXP_PHONE = "([+][\\d]+)";

    public static boolean validateConference(String name, String location,
	    String startDate, String endDate, String description) {
	if (name != null && location != null && startDate != null
		&& endDate != null) {
	    boolean result = true;
	    result = result && name.length() < 100 && location.length() < 255
		    && (description != null ? description.length() < 255
			    : true);
	    //TODO better time validation
	    result = DateTimeConverter.convertToLong(
		    startDate) < DateTimeConverter.convertToLong(endDate);
	    return result;
	} else {
	    return false;
	}
    }

    public static boolean validateReport(String name, String description) {
	if (name != null && description != null) {
	    return name.length() < 200 && description.length() < 600;
	} else {
	    return false;
	}

    }

    public static boolean validateSection(String name, String description) {
	return (name != null ? name.length() < 100 : false)
		&& (description != null ? description.length() < 255 : true);
    }

    public static boolean validateUser(String login, String firstName,
	    String lastName, String email, String phone) {
	if (login != null && firstName != null && lastName != null
		&& email != null && phone != null) {
	    boolean result = true;
	    result = result && login.length() < 40
		    && Pattern.matches(REGEXP_USER, login);
	    result = result && firstName.length() < 40
		    && lastName.length() < 40;
	    result = result && email.length() < 60
		    && Pattern.matches(REGEXP_EMAIL, email);
	    result = result && phone.length() < 30
		    && Pattern.matches(REGEXP_PHONE, phone);
	    return result;
	} else {
	    return false;
	}
    }

    public static boolean validatePassword(String password) {
	if (password != null) {
	    return password.length() > 0 && password.length() < 40;
	} else {
	    return false;
	}
    }

    public static boolean validateUserName(String userName) {
	if (userName != null) {
	    return Pattern.matches(REGEXP_USER, userName);
	} else {
	    return false;
	}
    }

    public static String removeScript(String str) {
	if (str != null && !str.isEmpty()) {
	    return str.replaceAll("</?script>", "");
	} else {
	    return str;
	}
    }
}
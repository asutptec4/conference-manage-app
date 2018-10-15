package com.epam.conference.util.validator;

import com.epam.conference.controller.RequestContent;
import com.epam.conference.util.PasswordEncryptor;
import com.epam.conference.util.constant.SessionConstant;

/**
 * Utility class used to check whether user input is repeated. Create in session
 * scope variable with {@code SessionConstant.INPUT} key, which contains MD5
 * hash sum of input field value. Class used in the algorithm to prevent sending
 * some duplicate form submissions.
 * 
 * @author Alexander Shishonok
 *
 */
public final class ReinputValidator {

    private ReinputValidator() {
    }

    /**
     * Check if fields of user's request are repeated. Return true if re-input
     * detected. If session scope variable ({@code SessionConstant.INPUT}) not
     * exist or input field MD5 hash differ from stored hash in session scope
     * variable, method rewrite MD5 hash sum in that variable.
     * 
     * @param content
     *            {@code RequestContent} object that encapsulate HttpRequest
     *            parameters.
     * @param field
     *            name of field of HttpRequest which necessary to check.
     * @return true if re-input detected.
     */
    public static boolean validate(RequestContent content, String... field) {
	boolean result = false;
	String inputField = (String) content
		.getSessionAttribute(SessionConstant.INPUT);
	if (inputField != null && !inputField.isEmpty()) {
	    if (inputField.equals(calcFieldCheckSum(content, field))) {
		result = true;
	    } else {
		content.setSessionAttribute(SessionConstant.INPUT,
			calcFieldCheckSum(content, field));
	    }
	} else {
	    content.setSessionAttribute(SessionConstant.INPUT,
		    calcFieldCheckSum(content, field));
	}
	return result;
    }

    /*
     * Method concatenate values of request parameters to string and then
     * calculate MD5 hash.
     */
    private static String calcFieldCheckSum(RequestContent content,
	    String... field) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < field.length; i++) {
	    builder.append(content.getRequestParameter(field[i]));
	}
	return PasswordEncryptor.encrypt(builder.toString(), null);
    }
}

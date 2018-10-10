package com.epam.conference.util.validator;

import com.epam.conference.util.PasswordEncryptor;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.SessionConstant;

public class ReinputValidator {

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

    private static String calcFieldCheckSum(RequestContent content,
	    String... field) {
	StringBuilder builder = new StringBuilder();
	for (int i = 0; i < field.length; i++) {
	    builder.append(content.getRequestParameter(field[i]));
	}
	return PasswordEncryptor.encrypt(builder.toString());
    }
}

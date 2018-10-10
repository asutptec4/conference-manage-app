package com.epam.conference.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {

    private static final String ALG_NAME = "MD5";
    private static final String CHARSET = "UTF-8";

    public static String encrypt(String input) {
	if (input == null) {
	    return "InvalidInput";
	}
	StringBuilder output = new StringBuilder();
	try {
	    byte[] byteOfInput = input.getBytes(CHARSET);
	    MessageDigest messageDigest = MessageDigest.getInstance(ALG_NAME);
	    byte[] byteOfOutput = messageDigest.digest(byteOfInput);
	    for (int i = 0; i < byteOfOutput.length; i++) {
		int temp = byteOfOutput[i] & 0xff;
		String str = Integer.toHexString(temp);
		if (str.length() < 2) {
		    str = "0" + str;
		}
		output.append(str);
	    }
	} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	    Thread.currentThread().interrupt();
	}
	return output.toString();
    }
}

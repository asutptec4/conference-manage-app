package com.epam.conference.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class used for encrypting input string according to the MD5 algorithm
 * with salt.
 * 
 * @author Alexander Shishonok
 *
 */
public final class PasswordEncryptor {

    private static final String ALG_NAME = "MD5";
    private static final String CHARSET = "UTF-8";
    private static final String FORMAT = "%02x";

    private PasswordEncryptor() {
    }

    /**
     * Encrypt entered string. Default salt is input string. If input parameter
     * is null return empty string.
     * 
     * @param input
     *            entered string.
     * @return MD5 hash as {@code String}.
     */
    public static String encrypt(String input) {
	return encrypt(input, input);
    }

    /**
     * Encrypt entered string. If input parameter is null return empty string.
     * 
     * @param input
     *            entered string.
     * @param salt
     *            is used as an additional input to hash function.
     * @return MD5 hash as {@code String}.
     */
    public static String encrypt(String input, String salt) {
	String result = "";
	if (input != null) {
	    StringBuilder sb = new StringBuilder();
	    byte[] hash = {};
	    try {
		MessageDigest md = MessageDigest.getInstance(ALG_NAME);
		md.update(input.getBytes(CHARSET));
		if (salt != null) {
		    md.update(salt.getBytes(CHARSET));
		}
		hash = md.digest();
	    } catch (NoSuchAlgorithmException
		    | UnsupportedEncodingException e) {
		return result;
	    }
	    for (byte b : hash) {
		sb.append(String.format(FORMAT, b));
	    }
	    result = sb.toString();
	}
	return result;
    }
}
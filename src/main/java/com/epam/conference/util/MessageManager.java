package com.epam.conference.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.epam.conference.util.constant.LocaleConstant;

/**
 * Enum determines the type of message manager depending on locale chosen in
 * application.
 * 
 * @author Alexander Shishonok
 *
 */
public enum MessageManager {

    EN(ResourceBundle.getBundle("properties/pagecontent",
	    new Locale(LocaleConstant.EN, LocaleConstant.EN.toUpperCase()))),
    RU(ResourceBundle.getBundle("properties/pagecontent",
	    new Locale(LocaleConstant.RU, LocaleConstant.RU.toUpperCase())));

    /**
     * Resource, an {@code ResourceBundle} object, contains localized messages.
     */
    private ResourceBundle bundle;

    private MessageManager(ResourceBundle bundle) {
	this.bundle = bundle;
    }

    /**
     * Return a string from resource bundle by key.
     * 
     * @param key
     *            the key for the desired string.
     * @return the string for the given key.
     */
    public String getProperty(String key) {
	return bundle.getString(key);
    }

    /**
     * Method choose instance of {@code MessageManager} depends on locale in
     * application.
     * 
     * @param locale
     *            the locale for message.
     * @return instance of {@code MessageManager}.
     */
    public static MessageManager choose(String locale) {
	MessageManager messageManager;
	if (locale == null) {
	    messageManager = EN;
	} else {
	    switch (locale.trim()) {
	    case LocaleConstant.RU:
		messageManager = RU;
		break;
	    case LocaleConstant.EN:
		messageManager = EN;
		break;
	    default:
		messageManager = EN;
		break;
	    }
	}
	return messageManager;
    }
}
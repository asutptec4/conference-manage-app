package com.epam.conference.util;

import java.util.Locale;
import java.util.ResourceBundle;

import com.epam.conference.util.constant.LocaleConstant;

public enum MessageManager {

    EN(ResourceBundle.getBundle("properties/pagecontent", new Locale("en", "EN"))),
    RU(ResourceBundle.getBundle("properties/pagecontent", new Locale("ru", "RU")));
    
    private ResourceBundle bundle;

    private MessageManager(ResourceBundle bundle) {
	this.bundle = bundle;
    }

    public String getProperty(String key) {
	return bundle.getString(key);
    }

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
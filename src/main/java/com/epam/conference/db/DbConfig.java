package com.epam.conference.db;

import java.util.ResourceBundle;

/**
 * Read configuration for database from property-file.
 * 
 * @author Alexander Shishonok
 *
 */
public class DbConfig {

    private static final String FILEPATH = "properties/dbconf";
    private static final String KEY_URL = "url";
    private static final String KEY_USER = "user";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_POOLSIZE = "poolSize";
    private static final String KEY_AUTOREC = "autoReconnect";
    private static final String KEY_SSL = "useSSL";
    private static final String KEY_USEUNICODE = "useUnicode";
    private static final String KEY_CHARENCOD = "characterEncoding";
    private static final int DEFAULT_POOL_SIZE = 20;

    /**
     * Database url.
     */
    private String url;

    /**
     * Database user.
     */
    private String user;

    /**
     * Database user's password.
     */
    private String password;

    /**
     * Number of connection in pool.
     */
    private int poolSize;

    DbConfig() {
	ResourceBundle resourceBundle = ResourceBundle.getBundle(FILEPATH);
	url = buildUrl(resourceBundle);
	user = resourceBundle.getString(KEY_USER);
	password = resourceBundle.getString(KEY_PASSWORD);
	if (resourceBundle.containsKey(KEY_POOLSIZE)) {
	    poolSize = Integer.parseInt(resourceBundle.getString(KEY_POOLSIZE));
	} else {
	    poolSize = DEFAULT_POOL_SIZE;
	}
    }

    public String getPassword() {
        return password;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getUrl() {
	return url;
    }

    public String getUser() {
	return user;
    }

    private String buildUrl(ResourceBundle resourceBundle) {
	StringBuilder sb = new StringBuilder();
	sb.append(resourceBundle.getString(KEY_URL)).append("?");
	if (resourceBundle.containsKey(KEY_AUTOREC)) {
	    sb.append(KEY_AUTOREC).append("=")
		    .append(resourceBundle.getString(KEY_AUTOREC)).append("&");
	}
	if (resourceBundle.containsKey(KEY_SSL)) {
	    sb.append(KEY_SSL).append("=")
		    .append(resourceBundle.getString(KEY_SSL)).append("&");
	}
	if (resourceBundle.containsKey(KEY_USEUNICODE)) {
	    sb.append(KEY_USEUNICODE).append("=")
		    .append(resourceBundle.getString(KEY_USEUNICODE))
		    .append("&");
	}
	if (resourceBundle.containsKey(KEY_CHARENCOD)) {
	    sb.append(KEY_CHARENCOD).append("=")
		    .append(resourceBundle.getString(KEY_CHARENCOD))
		    .append("&");
	}
	return sb.toString();
    }
}
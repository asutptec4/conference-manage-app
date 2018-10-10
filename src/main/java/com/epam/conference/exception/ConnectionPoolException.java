package com.epam.conference.exception;

/**
 * This exception is thrown when there is a problem with {@code ConnectionPool}
 * instance.
 * 
 * @author Alexander Shishonok
 */
public class ConnectionPoolException extends ConferenceAppException {

    private static final long serialVersionUID = -6164737102941474982L;

    public ConnectionPoolException() {
	super();
    }

    public ConnectionPoolException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }

    public ConnectionPoolException(String arg0) {
	super(arg0);
    }

    public ConnectionPoolException(Throwable arg0) {
	super(arg0);
    }

}

package com.epam.conference.exception;

/**
 * This exception is thrown when there is problem with DAO instance.
 * 
 * @author Alexander Shishonok
 */
public class ConferenceAppDaoException extends ConferenceAppException {

    private static final long serialVersionUID = 1155847195814192484L;

    public ConferenceAppDaoException() {
	super();
    }

    public ConferenceAppDaoException(String message, Throwable cause) {
	super(message, cause);
    }

    public ConferenceAppDaoException(String message) {
	super(message);
    }

    public ConferenceAppDaoException(Throwable cause) {
	super(cause);
    }
}

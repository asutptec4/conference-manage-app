package com.epam.conference.exception;

public class ConferenceAppServiceException extends ConferenceAppException {

    private static final long serialVersionUID = -356168538025344532L;

    public ConferenceAppServiceException() {
    }

    public ConferenceAppServiceException(String message) {
	super(message);
    }

    public ConferenceAppServiceException(Throwable cause) {
	super(cause);
    }

    public ConferenceAppServiceException(String message, Throwable cause) {
	super(message, cause);
    }

}

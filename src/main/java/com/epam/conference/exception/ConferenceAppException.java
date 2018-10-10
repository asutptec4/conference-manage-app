package com.epam.conference.exception;

public class ConferenceAppException extends Exception {
    
    private static final long serialVersionUID = -2901555591010707833L;

    public ConferenceAppException() {
    }

    public ConferenceAppException(String arg0) {
	super(arg0);
    }

    public ConferenceAppException(Throwable arg0) {
	super(arg0);
    }

    public ConferenceAppException(String arg0, Throwable arg1) {
	super(arg0, arg1);
    }
}

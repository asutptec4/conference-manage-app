package com.epam.conference.command;

import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;

public interface Command {
    	public static final String MESSAGE = "message";
    	public static final String ERROR_MESSAGE = "errorMessage";
	PageRouter execute(RequestContent requestContent);
}

package com.epam.conference.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestContent {

    private static final int FIRST_PARAMETER = 0;

    private static final Logger LOGGER = LogManager
	    .getLogger(RequestContent.class);

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;

    private boolean invalidateSession;

    public RequestContent() {
	requestAttributes = new HashMap<String, Object>();
	requestParameters = new HashMap<String, String[]>();
	sessionAttributes = new HashMap<String, Object>();
    }

    public void readRequest(HttpServletRequest request) {
	if (request == null) {
	    LOGGER.error("Request is null.");
	    return;
	}
	requestAttributes = Collections.list(request.getAttributeNames())
		.stream().collect(Collectors.toMap(Function.identity(),
			request::getAttribute));
	sessionAttributes = Collections
		.list(request.getSession().getAttributeNames()).stream()
		.collect(Collectors.toMap(Function.identity(),
			request.getSession()::getAttribute));
	requestParameters = Collections.list(request.getParameterNames())
		.stream().collect(Collectors.toMap(Function.identity(),
			request::getParameterValues));
    }

    public void updateRequest(HttpServletRequest request) {
	if (request == null) {
	    LOGGER.error("Request is null.");
	    return;
	}
	requestAttributes.forEach(request::setAttribute);
	sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    public Object getRequestAttribute(String key) {
	return requestAttributes.get(key);
    }

    public void setRequestAttribute(String key, Object value) {
	requestAttributes.put(key, value);
    }

    public Object getSessionAttribute(String key) {
	return sessionAttributes.get(key);
    }

    public void setSessionAttribute(String key, Object value) {
	sessionAttributes.put(key, value);
    }

    public String[] getRequestParameters(String key) {
	return requestParameters.get(key);
    }

    public String getRequestParameter(String key) {
	return requestParameters.get(key)[FIRST_PARAMETER];
    }

    public void setRequestParameters(String key, String[] value) {
	requestParameters.put(key, value);
    }

    public boolean isInvalidateSession() {
	return invalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
	this.invalidateSession = invalidateSession;
    }

}

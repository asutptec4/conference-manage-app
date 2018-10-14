package com.epam.conference.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Object of {@code RequestContent} encapsulate data from clients request.
 * 
 * @author Alexander Shishonok
 *
 */
public class RequestContent {

    private static final int FIRST_PARAMETER = 0;

    private static final Logger LOGGER = LogManager
	    .getLogger(RequestContent.class);

    /**
     * Stored request attributes.
     */
    private Map<String, Object> requestAttributes;

    /**
     * Stored request parameters.
     */
    private Map<String, String[]> requestParameters;

    /**
     * Stored session attributes.
     */
    private Map<String, Object> sessionAttributes;

    /**
     * Boolean flag indicates when is needed to invalidate session.
     */
    private boolean invalidateSession;

    /**
     * Constructor create empty collections for stored attributes and
     * parameters. Creating an object is allowed only to the controller in same
     * package.
     */
    RequestContent() {
	requestAttributes = new HashMap<String, Object>();
	requestParameters = new HashMap<String, String[]>();
	sessionAttributes = new HashMap<String, Object>();
    }

    /**
     * Read all parameters and attributes from {@code HttpServletRequest} object
     * and store to assigned collections.
     * 
     * @param request
     *            an {@code HttpServletRequest} object from client.
     */
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

    /**
     * Update {@code HttpServletRequest} object with stored attributes.
     * 
     * @param request
     *            an {@code HttpServletRequest} object from client.
     */
    public void updateRequest(HttpServletRequest request) {
	if (request == null) {
	    LOGGER.error("Request is null.");
	    return;
	}
	requestAttributes.forEach(request::setAttribute);
	sessionAttributes.forEach(request.getSession()::setAttribute);
    }

    /**
     * Returns the value of the named attribute of request.
     * 
     * @param key
     *            a string name of attribute.
     * @return an Object containing the value of the attribute
     */
    public Object getRequestAttribute(String key) {
	return requestAttributes.get(key);
    }

    /**
     * Stores an attribute in request.
     * 
     * @param key
     *            a string name of attribute.
     * @param value
     *            the Object to be stored.
     */
    public void setRequestAttribute(String key, Object value) {
	requestAttributes.put(key, value);
    }

    /**
     * Returns the value of the named attribute of session.
     * 
     * @param key
     *            a string name of attribute.
     * @return an Object containing the value of the attribute
     */
    public Object getSessionAttribute(String key) {
	return sessionAttributes.get(key);
    }

    /**
     * Stores an attribute in session.
     * 
     * @param key
     *            a string name of attribute.
     * @param value
     *            the Object to be stored.
     */
    public void setSessionAttribute(String key, Object value) {
	sessionAttributes.put(key, value);
    }

    /**
     * Returns an array of {@code String} objects containing values of the given
     * request parameter.
     * 
     * @param key
     *            a string name of parameter.
     * @return an array of string values
     */
    public String[] getRequestParameters(String key) {
	return requestParameters.get(key);
    }

    /**
     * Returns an first {@code String} value of the given request parameter.
     * 
     * @param key
     *            a string name of parameter.
     * @return parameter's value.
     */
    public String getRequestParameter(String key) {
	return requestParameters.get(key) != null
		? requestParameters.get(key)[FIRST_PARAMETER]
		: null;
    }

    /**
     * Check value of invalidateSession flag.
     * 
     * @return true if necessary to invalidate session.
     */
    public boolean isInvalidateSession() {
	return invalidateSession;
    }

    /**
     * Set invalidateSession flag.
     * 
     * @param invalidateSession
     */
    public void setInvalidateSession(boolean invalidateSession) {
	this.invalidateSession = invalidateSession;
    }

}

package com.epam.conference.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class LogFilter
 */
@WebFilter(urlPatterns = "/*", dispatcherTypes = { DispatcherType.FORWARD,
	DispatcherType.INCLUDE, DispatcherType.REQUEST })

public class LogFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(LogFilter.class);

    /**
     * Default constructor.
     */
    public LogFilter() {
	// TODO Auto-generated constructor stub
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
	// TODO Auto-generated method stub
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	// TODO Auto-generated method stub
	// place your code here
	HttpServletRequest req = (HttpServletRequest) request;
	StringBuilder builder = new StringBuilder();
	builder.append(req.getRequestURI()).append(" method=")
		.append(req.getMethod());
	builder.append("\nReceive params:\n");
	Collections.list(req.getParameterNames()).stream()
		.collect(Collectors.toMap(Function.identity(),
			req::getParameterValues))
		.forEach((key, value) -> builder
			.append(key + ":" + Arrays.toString(value) + "\n"));
	builder.append("Request attribube:\n");
	Collections.list(req.getAttributeNames()).stream()
		.collect(Collectors.toMap(Function.identity(),
			req::getAttribute))
		.forEach((key, value) -> builder
			.append(key + ":" + value + "\n"));
	builder.append("Session attributes:\n");
	Collections.list(req.getSession().getAttributeNames()).stream()
		.collect(Collectors.toMap(Function.identity(),
			req.getSession()::getAttribute))
		.forEach((key, value) -> builder
			.append(key + ":" + value + "\n"));
	LOGGER.debug(builder.toString());
	// pass the request along the filter chain
	chain.doFilter(request, response);
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	// TODO Auto-generated method stub
    }

}

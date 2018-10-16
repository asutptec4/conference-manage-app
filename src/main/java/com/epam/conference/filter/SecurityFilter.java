package com.epam.conference.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.CommandType;
import com.epam.conference.entity.UserRole;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * SecurityFilter provides protection against executing commands and accessing
 * pages for not authenticated users and executing commands for not authorized
 * users.
 */
@WebFilter(urlPatterns = { UriPathConstant.PATH_JSP,
	UriPathConstant.PATH_CONTROLLER })
public class SecurityFilter implements Filter {

    private static final String ENUM_WORD_DELIMITER = "_";
    private static final String COMMAND_WORD_DELIMITER = "-";

    private static final Logger LOGGER = LogManager
	    .getLogger(SecurityFilter.class);

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
	    FilterChain chain) throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	HttpSession session = req.getSession(false);
	// available page for not authenticated user
	boolean noLoginCondition = req.getRequestURI()
		.contains(UriPathConstant.PATH_INDEX)
		|| req.getRequestURI().contains(UriPathConstant.PATH_LOGIN)
		|| req.getRequestURI().contains(UriPathConstant.PATH_USER)
		|| checkCommandInRequest(req, CommandType.LOGIN,
			CommandType.SET_LOCALE, CommandType.ADD_USER);
	if (!noLoginCondition) {
	    if (session != null) {
		UserRole role = (UserRole) session
			.getAttribute(SessionConstant.ROLE);
		if (role != null) {
		    // disable administrator command for user
		    if (role == UserRole.USER && checkCommandInRequest(req,
			    CommandType.ADD_CONFERENCE, CommandType.ADD_SECTION,
			    CommandType.DELETE_CONFERENCE,
			    CommandType.DELETE_SECTION, CommandType.EDIT_APPLIC,
			    CommandType.EDIT_CONFERENCE,
			    CommandType.EDIT_SECTION, CommandType.SEARCH_APPLIC,
			    CommandType.SEARCH_APPLICATION,
			    CommandType.SHOW_ALL_USER)) {
			LOGGER.warn(session.getAttribute(SessionConstant.USER)
				+ " trying execute unauthorized command");
			resp.sendRedirect(req.getContextPath()
				+ UriPathConstant.PATH_INDEX);
		    } else {
			chain.doFilter(request, response);
		    }
		} else {
		    LOGGER.warn(
			    "Unauthenticated user trying execute unauthorized command from address "
				    + req.getRemoteAddr());
		    resp.sendRedirect(
			    req.getContextPath() + UriPathConstant.PATH_INDEX);
		}
	    } else {
		resp.sendRedirect(
			req.getContextPath() + UriPathConstant.PATH_INDEX);
	    }
	} else {
	    chain.doFilter(request, response);
	}
    }

    /*
     * Return true if one of command is in request.
     */
    private boolean checkCommandInRequest(HttpServletRequest request,
	    CommandType... commands) {
	boolean result = false;
	for (CommandType command : commands) {
	    result = result || commandTypeToString(command)
		    .equals(request.getParameter(RequestConstant.COMMAND));
	}
	return result && (request.getRequestURI()
		.contains(UriPathConstant.PATH_CONTROLLER));
    }

    private String commandTypeToString(CommandType command) {
	return command.toString().toLowerCase().replaceAll(ENUM_WORD_DELIMITER,
		COMMAND_WORD_DELIMITER);
    }
}

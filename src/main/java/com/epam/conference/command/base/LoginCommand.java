package com.epam.conference.command.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.UserService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(LoginCommand.class);

    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService service = UserService.getInstance();
	String login = requestContent
		.getRequestParameter(RequestConstant.USER_LOGIN);
	String pass = requestContent
		.getRequestParameter(RequestConstant.USER_PASSWORD);
	boolean flag = false;
	try {
	    flag = InputValidator.validateUserName(login)
		    && service.checkLogin(login, pass);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("User is not valid", e);
	}
	if (flag) {
	    requestContent.setSessionAttribute(SessionConstant.USER, login);
	    try {
		requestContent.setSessionAttribute(SessionConstant.ROLE,
			service.getUserRole(login).orElse(UserRole.USER));
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Can't get user role, role by default is USER", e);
		requestContent.setSessionAttribute(SessionConstant.ROLE,
			UserRole.USER);
	    }
	    router.setRouterType(PageRouterType.REDIRECT);
	    router.setPagePath(UriPathConstant.PATH_INDEX);
	} else {
	    requestContent.setRequestAttribute(ERROR_MESSAGE,
		    MessageManager
			    .choose((String) requestContent.getSessionAttribute(
				    SessionConstant.LOCALE))
			    .getProperty("message.error.login"));
	    router.setPagePath(UriPathConstant.PATH_LOGIN);
	}
	return router;
    }

}

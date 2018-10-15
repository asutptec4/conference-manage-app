package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.UserService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;

/**
 * {@code EditUserCommand} class implements {@link Command} interface. It is
 * used to change user information or user password.
 * 
 * @author Alexander Shishonok
 *
 */
public class EditUserCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(EditUserCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService service = UserService.getInstance();
	String login = InputValidator.removeScript(
		requestContent.getRequestParameter(RequestConstant.USER_LOGIN));
	String password = InputValidator.removeScript(requestContent
		.getRequestParameter(RequestConstant.USER_PASSWORD));
	String firstName = InputValidator.removeScript(requestContent
		.getRequestParameter(RequestConstant.USER_FIRSTNAME));
	String lastName = InputValidator.removeScript(requestContent
		.getRequestParameter(RequestConstant.USER_LASTNAME));
	String email = InputValidator.removeScript(
		requestContent.getRequestParameter(RequestConstant.USER_EMAIL));
	String phone = InputValidator.removeScript(
		requestContent.getRequestParameter(RequestConstant.USER_PHONE));
	boolean flag = false;
	try {
	    flag = InputValidator.validateUser(login, firstName, lastName,
		    email, phone) && InputValidator.validatePassword(password)
		    && service.updateUser(login, password, firstName, lastName,
			    email, phone);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to update users with login =" + login, e);
	}
	if (flag) {
	    router.setRouterType(PageRouterType.REDIRECT);
	    router.setPagePath(UriPathConstant.PATH_INDEX);
	} else {
	    requestContent.setRequestAttribute(RequestConstant.ERROR_MESSAGE,
		    MessageManager
			    .choose((String) requestContent.getSessionAttribute(
				    SessionConstant.LOCALE))
			    .getProperty("message.error.edituser"));
	    router.setPagePath(UriPathConstant.PATH_USER_SEARCH);
	}
	return router;
    }
}

package com.epam.conference.command.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.MessageService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;
import com.epam.conference.util.validator.ReinputValidator;

/**
 * Send message command. Allow user send message to admin and admin send respond
 * to user. Implements {@link Command} interface.
 * 
 * @author Alexander Shishonok
 *
 */
public class SendMessageCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SendMessageCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	router.setPagePath(UriPathConstant.PATH_MESSAGES);
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.MESSAGE_TEXT)) {
	    String text = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.MESSAGE_TEXT));
	    String receiver = requestContent
		    .getRequestParameter(RequestConstant.RECEIVER);
	    String login = (String) requestContent
		    .getSessionAttribute(SessionConstant.USER);
	    MessageService service = MessageService.getInstance();
	    boolean flag = false;
	    if (UserRole.USER == requestContent
		    .getSessionAttribute(SessionConstant.ROLE)) {
		try {
		    flag = InputValidator.validateMessage(text)
			    && service.sendMessage(login, MessageManager.EN
				    .getProperty("message.admin.login"), text);
		} catch (ConferenceAppServiceException e) {
		    LOGGER.error("Fail to add new message from user=" + login,
			    e);
		}
	    } else {
		try {
		    flag = InputValidator.validateMessage(text)
			    && service.sendMessage(
				    MessageManager.EN
					    .getProperty("message.admin.login"),
				    receiver, text);
		} catch (ConferenceAppServiceException e) {
		    LOGGER.error("Fail to add new message from user=" + login,
			    e);
		}
	    }
	    if (flag) {
		requestContent.setRequestAttribute(RequestConstant.MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.addmessage"));
	    } else {
		requestContent.setRequestAttribute(
			RequestConstant.ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.addmessage"));
	    }
	    if (receiver != null && !receiver.isEmpty()) {
		requestContent.setRequestAttribute(RequestConstant.RECEIVER,
			receiver);
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	    if (UserRole.ADMIN == requestContent
		    .getSessionAttribute(SessionConstant.ROLE)) {
		router.setPagePath(UriPathConstant.PATH_ALL_USER);
	    }
	}
	return router;
    }
}

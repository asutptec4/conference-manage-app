package com.epam.conference.command.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.entity.Message;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.MessageService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * Command find all message between concrete user and administrators.
 * 
 * @author Alexander Shishonok
 *
 */
public class ShowMessagesCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(ShowMessagesCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	MessageService service = MessageService.getInstance();
	List<Message> messages = new ArrayList<>();
	// get user role from session
	if (UserRole.USER == requestContent
		.getSessionAttribute(SessionConstant.ROLE)) {
	    // if role USER
	    String login = (String) requestContent
		    .getSessionAttribute(SessionConstant.USER);
	    try {
		messages = service.findUserMessages(login);
		service.markIsRead(messages, login);
		requestContent.setRequestAttribute(RequestConstant.MESSAGES,
			messages);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to find messages for user=" + login, e);
	    }
	} else {
	    // if role ADMIN
	    String receiver = requestContent
		    .getRequestParameter(RequestConstant.RECEIVER);
	    if (receiver != null && !receiver.isEmpty()) {
		requestContent.setRequestAttribute(RequestConstant.RECEIVER,
			receiver);
	    }
	    try {
		messages = service.findUserMessages(receiver);
		service.markIsRead(messages,
			MessageManager.EN.getProperty("message.admin.login"));
		requestContent.setRequestAttribute(RequestConstant.MESSAGES,
			messages);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to find messages for user=" + receiver, e);
	    }
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_MESSAGE);
	return router;
    }
}

package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.MessageService;
import com.epam.conference.service.UserService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class ShowAllUserCommand implements Command {


    private static final Logger LOGGER = LogManager
	    .getLogger(ShowAllUserCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService service = UserService.getInstance();
	try {
	    requestContent.setRequestAttribute(RequestConstant.USERS,
		    service.getUserList());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return user list", e);
	}
	MessageService messageService = MessageService.getInstance();
	try {
	    requestContent.setRequestAttribute(RequestConstant.UNREAD_LIST,
		    messageService.findUserWithUnreadMessage());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to find user with unread message", e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_USER);
	return router;
    }
}

package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ApplicationService;
import com.epam.conference.service.UserService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchUserApplicCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchUserApplicCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService userService = UserService.getInstance();
	ApplicationService applicationService = ApplicationService
		.getInstance();
	String login = (String) requestContent
		.getSessionAttribute(SessionConstant.USER);
	if (login != null) {
	    try {
		long userId = userService.findUserByLogin(login).get().getId();
		requestContent.setRequestAttribute(
			RequestConstant.APPLICATION_INFO,
			applicationService.findApplicationInfoByUser(userId));
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Can't find apllications by login=" + login, e);
	    }
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_APPLIC);
	return router;
    }

}

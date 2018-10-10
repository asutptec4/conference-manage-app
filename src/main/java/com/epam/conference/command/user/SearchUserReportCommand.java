package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ReportService;
import com.epam.conference.service.UserService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchUserReportCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchUserReportCommand.class);
    private static final int FIRST_INDEX = 0;

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService userService = new UserService();
	ReportService reportsService = new ReportService();
	String login = (String) requestContent
		.getSessionAttribute(SessionConstant.USER);
	if (login != null) {
	    try {
		long userId = userService.findUserByLogin(login).get().getId();
		requestContent.setRequestAttribute(RequestConstant.REPORTS,
			reportsService.findUserReports(userId));
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Can't find report list by login=" + login, e);
	    }
	}
	String sectionId = processRequestParameter(requestContent
		.getRequestParameters(RequestConstant.SECTION_ID));
	if (sectionId != null) {
	    requestContent.setRequestAttribute(RequestConstant.SECTION_ID,
		    sectionId);
	    router.setPagePath(UriPathConstant.PATH_APPLICATION_NEW);
	} else {
	    router.setPagePath(UriPathConstant.PATH_VIEW_REPORT);
	}
	return router;
    }

    private String processRequestParameter(String[] str) {
	String result = null;
	if (str != null && str.length > 0) {
	    if (str[FIRST_INDEX].length() > 0) {
		result = str[FIRST_INDEX];
	    }
	}
	return result;
    }
}

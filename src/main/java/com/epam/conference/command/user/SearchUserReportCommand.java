package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ReportService;
import com.epam.conference.service.UserService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * {@code SearchUserReportCommand} class implements {@link Command} interface.
 * Command used to display to view user reports. If request have parameter
 * contain section id value, command forward to page for adding new application.
 * 
 * @author Alexander Shishonok
 *
 */
public class SearchUserReportCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchUserReportCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService userService = UserService.getInstance();
	ReportService reportsService = ReportService.getInstance();
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
	String sectionId = requestContent
		.getRequestParameter(RequestConstant.SECTION_ID);
	if (sectionId != null) {
	    requestContent.setRequestAttribute(RequestConstant.SECTION_ID,
		    sectionId);
	    router.setPagePath(UriPathConstant.PATH_APPLICATION_NEW);
	} else {
	    router.setPagePath(UriPathConstant.PATH_VIEW_REPORT);
	}
	return router;
    }
}

package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ReportService;
import com.epam.conference.service.UserService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;
import com.epam.conference.util.validator.ReinputValidator;

public class EditReportCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(EditReportCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.REPORT_NAME, RequestConstant.REPORT_DESC)) {
	    long id = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.REPORT_ID));
	    String name = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.REPORT_NAME));
	    String description = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.REPORT_DESC));
	    String userLogin = (String) requestContent
		    .getSessionAttribute(SessionConstant.USER);
	    UserService userService = UserService.getInstance();
	    long userId = 0;
	    try {
		userId = userService.findUserByLogin(userLogin).get().getId();
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to find userId by login", e);
	    }
	    boolean flag = false;
	    ReportService reportService = ReportService.getInstance();
	    try {
		flag = (userId > 0)
			&& InputValidator.validateReport(name, description)
			&& reportService.updateReport(id, userId, name,
				description);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to update report with id=" + id, e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.editreport"));
	    } else {
		requestContent.setRequestAttribute(ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.editreport"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_REPORT_SEARCH);
	return router;
    }

}

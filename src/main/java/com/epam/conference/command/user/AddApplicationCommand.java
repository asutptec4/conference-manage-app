package com.epam.conference.command.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ApplicationService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;
import com.epam.conference.util.validator.ReinputValidator;

public class AddApplicationCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(AddApplicationCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.REPORT_ID, RequestConstant.SECTION_ID,
		RequestConstant.REPORT_DATE)) {
	    String repordDate = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.REPORT_DATE));
	    long reportId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.REPORT_ID));
	    long sectionId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.SECTION_ID));
	    ApplicationService service = ApplicationService.getInstance();
	    boolean flag = false;
	    try {
		flag = service.addApplication(sectionId, reportId, repordDate);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to add new application", e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.addapplic"));
		LOGGER.info("User=" + requestContent.getSessionAttribute(
			SessionConstant.USER) + " add application");
	    } else {
		requestContent.setRequestAttribute(ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.addapplic"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_USER_APPLIC);
	return router;
    }

}

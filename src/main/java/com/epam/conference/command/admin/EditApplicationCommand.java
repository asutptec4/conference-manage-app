package com.epam.conference.command.admin;

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
import com.epam.conference.util.validator.ReinputValidator;

/**
 * {@code EditApplicationCommand} class implements {@link Command} interface.
 * Edit user application for participation in the conference. Administrator
 * change date of report and current status of the application.
 * 
 * @author Alexander Shishonok
 *
 */
public class EditApplicationCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(EditApplicationCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.APPLICATION_ID, RequestConstant.REPORT_DATE,
		RequestConstant.STATUSE_ID)) {
	    String reportDate = requestContent
		    .getRequestParameter(RequestConstant.REPORT_DATE);
	    long applicId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.APPLICATION_ID));
	    long statusId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.STATUSE_ID));
	    ApplicationService service = ApplicationService.getInstance();
	    boolean flag = false;
	    try {
		flag = service.changeApplicStatus(applicId, statusId);
		if (reportDate != null && reportDate.length() > 0) {
		    flag = service.changeApplicDate(applicId, reportDate);
		}
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to change application id=" + applicId, e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(RequestConstant.MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.editapplic"));
	    } else {
		requestContent.setRequestAttribute(
			RequestConstant.ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.editapplic"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_APPLICATION_ALL);
	return router;
    }
}

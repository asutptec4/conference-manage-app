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
import com.epam.conference.util.validator.ReinputValidator;

/**
 * User command that remove the user application for participation in the
 * conference.
 * 
 * @author Alexander Shishonok
 *
 */
public class RemoveApplicationCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(RemoveApplicationCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.APPLICATION_ID)) {
	    long applicId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.APPLICATION_ID));
	    ApplicationService service = ApplicationService.getInstance();
	    boolean flag = false;
	    try {
		flag = service.removeApplication(applicId);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to remove application id=" + applicId, e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(RequestConstant.MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.removeapplic"));
		LOGGER.info("User="
			+ requestContent
				.getSessionAttribute(SessionConstant.USER)
			+ " remove application" + applicId);
	    } else {
		requestContent.setRequestAttribute(
			RequestConstant.ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.removeapplic"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_USER_APPLIC);
	return router;
    }
}

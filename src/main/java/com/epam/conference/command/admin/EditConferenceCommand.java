package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.servlet.PageRouter.PageRouterType;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;
import com.epam.conference.util.validator.ReinputValidator;

public class EditConferenceCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(EditConferenceCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.CONFERENCE_NAME,
		RequestConstant.CONFERENCE_DESC, RequestConstant.CONFERENCE_LOC,
		RequestConstant.CONFERENCE_START,
		RequestConstant.CONFERENCE_END)) {
	    long id = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_ID));
	    String name = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_NAME));
	    String description = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_DESC));
	    String location = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_LOC));
	    String startDate = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_START));
	    String endDate = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_END));
	    ConferenceService service = new ConferenceService();
	    boolean flag = false;
	    try {
		flag = InputValidator.validateConference(name, location,
			startDate, endDate, description)
			&& service.updateConference(id, name, startDate,
				endDate, location, description);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to edit conference", e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.editconfer"));
	    } else {
		requestContent.setRequestAttribute(ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.editconfer"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	return router;
    }

}

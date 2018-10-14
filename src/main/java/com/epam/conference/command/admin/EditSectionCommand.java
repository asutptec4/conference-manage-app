package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.InputValidator;
import com.epam.conference.util.validator.ReinputValidator;

public class EditSectionCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(EditSectionCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.SECTION_NAME, RequestConstant.SECTION_DESC)) {
	    String name = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.SECTION_NAME));
	    String description = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.SECTION_DESC));
	    long id = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.SECTION_ID));
	    ConferenceService service = ConferenceService.getInstance();
	    boolean flag = false;
	    try {
		flag = InputValidator.validateSection(name, description)
			&& service.updateSection(id, name, description);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to editsection", e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.editsection"));
	    } else {
		requestContent.setRequestAttribute(ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.editsection"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	return router;
    }

}

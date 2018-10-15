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

/**
 * {@code AddSectionCommand} class implements {@link Command} interface.
 * Command used to add new section to existing conference.
 * 
 * @author Alexander Shishonok
 *
 */
public class AddSectionCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(AddSectionCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.SECTION_NAME, RequestConstant.SECTION_DESC)) {
	    String name = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.SECTION_NAME));
	    String description = InputValidator.removeScript(requestContent
		    .getRequestParameter(RequestConstant.SECTION_DESC));
	    long conferenceId = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_ID));
	    ConferenceService service = ConferenceService.getInstance();
	    boolean flag = false;
	    try {
		flag = InputValidator.validateSection(name, description)
			&& service.addSection(name, description, conferenceId);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to add new section", e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(RequestConstant.MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.addsection"));
		router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
		LOGGER.info("Add new section=" + name + " in conference ="
			+ conferenceId);
	    } else {
		requestContent.setRequestAttribute(
			RequestConstant.ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.addsection"));
		router.setPagePath(UriPathConstant.PATH_SECTION_NEW);
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	    router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	}
	return router;
    }
}

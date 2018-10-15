package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.MessageManager;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;
import com.epam.conference.util.validator.ReinputValidator;

/**
 * Implements {@link Command} interface. Execution of this command delete chosen
 * conference.
 * 
 * @author Alexander Shishonok
 *
 */
public class DeleteConferenceCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(DeleteConferenceCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	if (!ReinputValidator.validate(requestContent,
		RequestConstant.CONFERENCE_ID)) {
	    long id = Long.parseLong(requestContent
		    .getRequestParameter(RequestConstant.CONFERENCE_ID));
	    boolean flag = false;
	    ConferenceService service = ConferenceService.getInstance();
	    try {
		flag = service.deleteConference(id);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to remove conference id=" + id, e);
	    }
	    if (flag) {
		requestContent.setRequestAttribute(RequestConstant.MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.success.delete"));
	    } else {
		requestContent.setRequestAttribute(
			RequestConstant.ERROR_MESSAGE,
			MessageManager.choose((String) requestContent
				.getSessionAttribute(SessionConstant.LOCALE))
				.getProperty("message.error.delete"));
	    }
	} else {
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	return router;
    }
}

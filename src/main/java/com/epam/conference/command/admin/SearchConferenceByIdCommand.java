package com.epam.conference.command.admin;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.entity.Conference;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.servlet.PageRouter.PageRouterType;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchConferenceByIdCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchConferenceByIdCommand.class);
    private static final String COMMAND_EDIT_CONFER = "editConference";

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = new ConferenceService();
	long conferenceId = Long.parseLong(requestContent
		.getRequestParameter(RequestConstant.CONFERENCE_ID));
	Optional<Conference> optional = Optional.empty();
	try {
	    optional = service.findConferenceById(conferenceId);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to find report by id", e);
	}
	if (optional.isPresent()) {
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCE,
		    optional.get());
	    requestContent.setRequestAttribute(RequestConstant.COMMAND,
		    COMMAND_EDIT_CONFER);
	    router.setPagePath(UriPathConstant.PATH_CONFER_NEW);
	} else {
	    // TODO think of something better
	    router.setPagePath(UriPathConstant.PATH_INDEX);
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	return router;
    }

}
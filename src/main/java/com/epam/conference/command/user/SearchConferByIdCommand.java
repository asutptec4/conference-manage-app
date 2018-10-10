package com.epam.conference.command.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.entity.Conference;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchConferByIdCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchConferByIdCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = new ConferenceService();
	long conferenceId = Long.parseLong(requestContent
		.getRequestParameter(RequestConstant.CONFERENCE_ID));
	try {
	    List<Conference> conference = new ArrayList<>();
	    Optional<Conference> optional = service
		    .findConferenceById(conferenceId);
	    if (optional.isPresent()) {
		conference.add(optional.get());
	    }
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCES,
		    conference);
	    requestContent.setRequestAttribute(RequestConstant.SECTIONS,
		    service.findSectionsOfConference(conferenceId));
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return conference detail for conferenceId="
		    + conferenceId, e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_CONFER);
	return router;
    }

}

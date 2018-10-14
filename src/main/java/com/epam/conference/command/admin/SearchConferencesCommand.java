package com.epam.conference.command.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.entity.Conference;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.util.DateTimeConverter;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchConferencesCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchConferencesCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = ConferenceService.getInstance();
	try {
	    List<Conference> conferences = service.getConferenceList();
	    if (UserRole.USER == requestContent
		    .getSessionAttribute(SessionConstant.ROLE)) {
		conferences = conferences.stream().filter(
			conf -> conf.getEndDate() > DateTimeConverter.now())
			.collect(Collectors.toList());
	    }
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCES,
		    conferences);
	    requestContent.setRequestAttribute(RequestConstant.SECTIONS,
		    service.getSectionList());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return conference list from db.", e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_CONFER);
	return router;
    }

}

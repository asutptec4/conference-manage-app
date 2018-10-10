package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchConferencesCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchConferencesCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = new ConferenceService();
	try {
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCES,
		    service.getConferenceList());
	    requestContent.setRequestAttribute(RequestConstant.SECTIONS,
		    service.getSectionList());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return conference list from db.", e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_CONFER);
	return router;
    }

}

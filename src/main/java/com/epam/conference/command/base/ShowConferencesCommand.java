package com.epam.conference.command.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class ShowConferencesCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(ShowConferencesCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = new ConferenceService();
	try {
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCES,
		    service.getConferenceList());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return conference list", e);
	}
	router.setPagePath(UriPathConstant.PATH_MAIN);
	return router;
    }

}

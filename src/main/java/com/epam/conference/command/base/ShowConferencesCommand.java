package com.epam.conference.command.base;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.util.DateTimeConverter;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * Implements {@link Command} interface. Execution of this command return all
 * actual conferences in database, whose end date is greater than the current
 * date.
 * 
 * @author Alexander Shishonok
 *
 */
public class ShowConferencesCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(ShowConferencesCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ConferenceService service = ConferenceService.getInstance();
	try {
	    requestContent.setRequestAttribute(RequestConstant.CONFERENCES,
		    service.getConferenceList().stream().filter(
			    conf -> conf.getEndDate() > DateTimeConverter.now())
			    .collect(Collectors.toList()));
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return conference list", e);
	}
	router.setPagePath(UriPathConstant.PATH_MAIN);
	return router;
    }
}

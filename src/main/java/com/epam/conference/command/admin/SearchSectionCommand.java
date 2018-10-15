package com.epam.conference.command.admin;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.entity.Section;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ConferenceService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * {@code SearchSectionCommand} class implements {@link Command} interface.
 * Command used to forward to new section page or edit section page.
 * 
 * @author Alexander Shishonok
 *
 */
public class SearchSectionCommand implements Command {

    private static final String COMMAND_EDIT_SECTION = "edit-section";
    private static final String COMMAND_ADD_SECTION = "add-section";

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchSectionCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	String sectionId = requestContent
		.getRequestParameter(RequestConstant.SECTION_ID);
	String conferenceId = requestContent
		.getRequestParameter(RequestConstant.CONFERENCE_ID);
	if (sectionId != null) {
	    long id = Long.parseLong(sectionId);
	    Optional<Section> optional = Optional.empty();
	    ConferenceService service = ConferenceService.getInstance();
	    try {
		optional = service.findSectionById(id);
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Fail to find section by id", e);
	    }
	    if (optional.isPresent()) {
		requestContent.setRequestAttribute(RequestConstant.SECTION,
			optional.get());
		requestContent.setRequestAttribute(RequestConstant.COMMAND,
			COMMAND_EDIT_SECTION);
		router.setPagePath(UriPathConstant.PATH_SECTION_NEW);
	    } else {
		router.setRouterType(PageRouterType.REDIRECT);
		router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	    }
	} else {
	    if (conferenceId != null) {
		requestContent.setRequestAttribute(
			RequestConstant.CONFERENCE_ID, conferenceId);
		requestContent.setRequestAttribute(RequestConstant.COMMAND,
			COMMAND_ADD_SECTION);
		router.setPagePath(UriPathConstant.PATH_SECTION_NEW);
	    } else {
		router.setRouterType(PageRouterType.REDIRECT);
		router.setPagePath(UriPathConstant.PATH_CONFER_SEARCH);
	    }
	}
	return router;
    }
}

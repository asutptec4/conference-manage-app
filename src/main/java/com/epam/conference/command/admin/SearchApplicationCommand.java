package com.epam.conference.command.admin;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.entity.Application;
import com.epam.conference.entity.Status;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ApplicationService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * {@code SearchApplicationCommand} class implements {@link Command} interface.
 * Search user application by id and forward to edit application page for
 * administrators.
 * 
 * @author Alexander Shishonok
 *
 */
public class SearchApplicationCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchApplicationCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	long applicId = Long.parseLong(requestContent
		.getRequestParameter(RequestConstant.APPLICATION_ID));
	ApplicationService applicationService = ApplicationService
		.getInstance();
	Optional<Application> optional = Optional.empty();
	try {
	    optional = applicationService.findApplicationById(applicId);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to find application by id", e);
	}
	try {
	    List<Status> statuses = applicationService.getStatusList();
	    requestContent.setRequestAttribute(RequestConstant.STATUSES,
		    statuses);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to get status list", e);
	}
	if (optional.isPresent()) {
	    requestContent.setRequestAttribute(RequestConstant.APPLICATION,
		    optional.get());
	    router.setPagePath(UriPathConstant.PATH_APPLICATION_EDIT);
	} else {
	    router.setPagePath(UriPathConstant.PATH_USER_APPLIC);
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	return router;
    }
}

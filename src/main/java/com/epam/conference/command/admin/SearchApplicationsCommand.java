package com.epam.conference.command.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.entity.ApplicationInfo;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ApplicationService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchApplicationsCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(SearchApplicationsCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ApplicationService service = ApplicationService.getInstance();
	String searchKey = requestContent
		.getRequestParameter(RequestConstant.SEARCH_KEY);
	try {
	    List<ApplicationInfo> applications = service
		    .getApplicationInfoList();
	    if (searchKey != null && !searchKey.isEmpty()) {
		final String key = searchKey.toLowerCase().trim();
		applications = applications.stream().filter(applic -> applic
			.getConferenceName().toLowerCase().contains(key)
			|| applic.getLogin().toLowerCase().contains(key)
			|| applic.getSectionName().toLowerCase().contains(key)
			|| applic.getUserName().toLowerCase().contains(key)
			|| applic.getReportName().toLowerCase().contains(key)
			|| applic.getStatus().getName().toLowerCase()
				.contains(key))
			.collect(Collectors.toList());
	    }
	    requestContent.setRequestAttribute(RequestConstant.APPLICATION_INFO,
		    applications);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return application list", e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_APPLIC);
	return router;
    }
}

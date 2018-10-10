package com.epam.conference.command.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ApplicationService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class ShowAllApplicCommand implements Command {

    private static final Logger LOGGER = LogManager
	    .getLogger(ShowAllApplicCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ApplicationService service = new ApplicationService();
	try {
	    requestContent.setRequestAttribute(RequestConstant.APPLICATION_INFO,
		    service.getApplicationInfoList());
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to return application list", e);
	}
	router.setPagePath(UriPathConstant.PATH_VIEW_APPLIC);
	return router;
    }

}

package com.epam.conference.command.user;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.entity.Report;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.ReportService;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchReportByIdCommand implements Command {

    private static final String COMMAND_EDIT_REPORT = "edit-report";
    private static final Logger LOGGER = LogManager
	    .getLogger(SearchReportByIdCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	ReportService service = ReportService.getInstance();
	long reportId = Long.parseLong(
		requestContent.getRequestParameter(RequestConstant.REPORT_ID));
	Optional<Report> report = Optional.empty();
	try {
	    report = service.findReportByid(reportId);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to find report by id", e);
	}
	if (report.isPresent()) {
	    requestContent.setRequestAttribute(RequestConstant.REPORT,
		    report.get());
	    requestContent.setRequestAttribute(RequestConstant.COMMAND,
		    COMMAND_EDIT_REPORT);
	    router.setPagePath(UriPathConstant.PATH_REPORT);
	} else {
	    router.setPagePath(UriPathConstant.PATH_REPORT_SEARCH);
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	return router;
    }
}

package com.epam.conference.command.base;

import com.epam.conference.command.Command;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.servlet.PageRouter.PageRouterType;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.UriPathConstant;

public class LogoutCommand implements Command {

    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	router.setRouterType(PageRouterType.REDIRECT);
	router.setPagePath(UriPathConstant.PATH_INDEX);
	requestContent.setInvalidateSession(true);
	return router;
    }

}

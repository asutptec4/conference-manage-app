package com.epam.conference.command.base;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * {@code LogoutCommand} class implements {@link Command} interface. Invalidate
 * user session and redirect to welcome page.
 * 
 * @author Alexander Shishonok
 *
 */
public class LogoutCommand implements Command {

    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	router.setRouterType(PageRouterType.REDIRECT);
	router.setPagePath(UriPathConstant.PATH_INDEX);
	requestContent.setInvalidateSession(true);
	return router;
    }
}

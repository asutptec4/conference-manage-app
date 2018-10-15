package com.epam.conference.command.base;

import com.epam.conference.command.Command;
import com.epam.conference.controller.PageRouter;
import com.epam.conference.controller.RequestContent;
import com.epam.conference.controller.PageRouter.PageRouterType;
import com.epam.conference.util.constant.UriPathConstant;

/**
 * Implements {@link Command} interface. Used in case when command type not
 * defined. Redirect to welcome page.
 * 
 * @author Alexander Shishonok
 *
 */
public class EmptyCommand implements Command {

    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	router.setRouterType(PageRouterType.REDIRECT);
	router.setPagePath(UriPathConstant.PATH_INDEX);
	return router;
    }
}

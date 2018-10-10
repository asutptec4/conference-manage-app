package com.epam.conference.command.base;

import com.epam.conference.command.Command;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.servlet.PageRouter.PageRouterType;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.LocaleConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SetLocaleCommand implements Command {

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	String locale = requestContent
		.getRequestParameter(SessionConstant.LOCALE);
	if (locale != null) {
	    if ("ru".equals(locale.trim().toLowerCase())) {
		locale = LocaleConstant.RU;
	    } else {
		locale = LocaleConstant.EN;
	    }
	} else {
	    locale = LocaleConstant.EN;
	}
	requestContent.setSessionAttribute(SessionConstant.LOCALE, locale);
	router.setPagePath(UriPathConstant.PATH_INDEX);
	router.setRouterType(PageRouterType.REDIRECT);
	return router;
    }

}

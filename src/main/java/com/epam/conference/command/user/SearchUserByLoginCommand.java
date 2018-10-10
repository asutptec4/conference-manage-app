package com.epam.conference.command.user;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.command.Command;
import com.epam.conference.entity.User;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.UserService;
import com.epam.conference.servlet.PageRouter;
import com.epam.conference.servlet.PageRouter.PageRouterType;
import com.epam.conference.util.RequestContent;
import com.epam.conference.util.constant.RequestConstant;
import com.epam.conference.util.constant.SessionConstant;
import com.epam.conference.util.constant.UriPathConstant;

public class SearchUserByLoginCommand implements Command {

    private static final String COMMAND_EDIT_USER = "editUser";
    private static final Logger LOGGER = LogManager
	    .getLogger(SearchUserByLoginCommand.class);

    @Override
    public PageRouter execute(RequestContent requestContent) {
	PageRouter router = new PageRouter();
	UserService service = new UserService();
	String login = (String) requestContent
		.getSessionAttribute(SessionConstant.USER);
	Optional<User> user = Optional.empty();
	try {
	    user = service.findUserByLogin(login);
	} catch (ConferenceAppServiceException e) {
	    LOGGER.error("Fail to find user by login =" + login, e);
	}
	if (user.isPresent()) {
	    requestContent.setRequestAttribute(RequestConstant.USER,
		    user.get());
	    requestContent.setRequestAttribute(RequestConstant.COMMAND,
		    COMMAND_EDIT_USER);
	    router.setPagePath(UriPathConstant.PATH_USER);
	} else {
	    // TODO think of something better
	    router.setPagePath(UriPathConstant.PATH_INDEX);
	    router.setRouterType(PageRouterType.REDIRECT);
	}
	return router;
    }

}

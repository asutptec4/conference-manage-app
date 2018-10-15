package com.epam.conference.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.entity.User;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.service.UserService;

/**
 * Tag used for output user first name and last name instead login.
 * 
 * @author Alexander Shishonok
 *
 */
public class UserInfoTag extends SimpleTagSupport {

    private static final String DEFAULT_NIK = "Unknown";

    private static final Logger LOGGER = LogManager
	    .getLogger(UserInfoTag.class);

    /**
     * Tag attribute user login in web app.
     */
    private String login;

    public void setLogin(String login) {
	this.login = login;
    }

    public String getLogin() {
	return login;
    }

    @Override
    public void doTag() throws JspException, IOException {
	String result = DEFAULT_NIK;
	if (login != null) {
	    User user = null;
	    try {
		user = UserService.getInstance().findUserByLogin(login).get();
	    } catch (ConferenceAppServiceException e) {
		LOGGER.error("Can't find user with login= " + login, e);
	    }
	    if (user != null) {
		result = user.getFirstName() + " " + user.getLastName();
	    }
	}
	getJspContext().getOut().write(result);
    }
}

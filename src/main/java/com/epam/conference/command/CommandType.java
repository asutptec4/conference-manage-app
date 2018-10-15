package com.epam.conference.command;

import com.epam.conference.command.admin.AddConferenceCommand;
import com.epam.conference.command.admin.AddSectionCommand;
import com.epam.conference.command.admin.DeleteConferenceCommand;
import com.epam.conference.command.admin.DeleteSectionCommand;
import com.epam.conference.command.admin.EditApplicationCommand;
import com.epam.conference.command.admin.EditConferenceCommand;
import com.epam.conference.command.admin.EditSectionCommand;
import com.epam.conference.command.admin.SearchApplicationCommand;
import com.epam.conference.command.admin.SearchConferenceByIdCommand;
import com.epam.conference.command.admin.SearchConferencesCommand;
import com.epam.conference.command.admin.SearchSectionCommand;
import com.epam.conference.command.admin.SearchApplicationsCommand;
import com.epam.conference.command.admin.ShowAllUserCommand;
import com.epam.conference.command.base.LoginCommand;
import com.epam.conference.command.base.LogoutCommand;
import com.epam.conference.command.base.SendMessageCommand;
import com.epam.conference.command.base.SetLocaleCommand;
import com.epam.conference.command.base.ShowConferencesCommand;
import com.epam.conference.command.base.ShowMessagesCommand;
import com.epam.conference.command.user.AddApplicationCommand;
import com.epam.conference.command.user.AddReportCommand;
import com.epam.conference.command.user.AddUserCommand;
import com.epam.conference.command.user.EditReportCommand;
import com.epam.conference.command.user.EditUserCommand;
import com.epam.conference.command.user.RemoveApplicationCommand;
import com.epam.conference.command.user.SearchConferByIdCommand;
import com.epam.conference.command.user.SearchReportByIdCommand;
import com.epam.conference.command.user.SearchUserApplicCommand;
import com.epam.conference.command.user.SearchUserByLoginCommand;
import com.epam.conference.command.user.SearchUserReportCommand;

/**
 * Enum define a set of available command.
 * 
 * @author Alexander Shishonok
 */
public enum CommandType {
    
    ADD_APPLIC(new AddApplicationCommand()),
    ADD_CONFERENCE(new AddConferenceCommand()),
    ADD_REPORT(new AddReportCommand()),
    ADD_SECTION(new AddSectionCommand()),
    ADD_USER(new AddUserCommand()),
    DELETE_CONFERENCE(new DeleteConferenceCommand()),
    DELETE_SECTION(new DeleteSectionCommand()),
    EDIT_APPLIC(new EditApplicationCommand()),
    EDIT_CONFERENCE(new EditConferenceCommand()),
    EDIT_REPORT(new EditReportCommand()),
    EDIT_SECTION(new EditSectionCommand()),
    EDIT_USER(new EditUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REMOVE_APPLIC(new RemoveApplicationCommand()),
    SEARCH_APPLIC(new SearchApplicationsCommand()),
    SEARCH_APPLICATION(new SearchApplicationCommand()),
    SEARCH_CONFER(new SearchConferencesCommand()),
    SEARCH_CONFER_BYID(new SearchConferByIdCommand()),
    SEARCH_CONFERENCE_BYID(new SearchConferenceByIdCommand()),
    SEARCH_REPORT_BYID(new SearchReportByIdCommand()),
    SEARCH_SECTION(new SearchSectionCommand()),
    SEARCH_USER(new SearchUserByLoginCommand()),
    SEARCH_USER_APPLIC(new SearchUserApplicCommand()),
    SEARCH_USER_REPORT(new SearchUserReportCommand()),
    SEND_MESSAGE(new SendMessageCommand()),
    SET_LOCALE(new SetLocaleCommand()),
    SHOW_ALL_CONFER(new ShowConferencesCommand()),
    SHOW_ALL_USER(new ShowAllUserCommand()),
    SHOW_MESSAGES(new ShowMessagesCommand());
    
    /**
     * Field contain implementation of concrete {@code Command}.
     */
    private Command command;

    /**
     * Type of {@code Command} object define on initialization enum stage.
     * 
     * @param command an instance of class which implement {@code Command}
     * interface.
     */
    private CommandType(Command command) {
	this.command = command;
    }
    
    public Command getCommand() {
	return command;
    }
}

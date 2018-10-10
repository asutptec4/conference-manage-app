package com.epam.conference.command;

import com.epam.conference.command.admin.AddConferenceCommand;
import com.epam.conference.command.admin.AddSectionCommand;
import com.epam.conference.command.admin.EditApplicationCommand;
import com.epam.conference.command.admin.EditConferenceCommand;
import com.epam.conference.command.admin.EditSectionCommand;
import com.epam.conference.command.admin.SearchApplicationCommand;
import com.epam.conference.command.admin.SearchConferenceByIdCommand;
import com.epam.conference.command.admin.SearchConferencesCommand;
import com.epam.conference.command.admin.SearchSectionCommand;
import com.epam.conference.command.admin.ShowAllApplicCommand;
import com.epam.conference.command.admin.ShowAllUserCommand;
import com.epam.conference.command.base.LoginCommand;
import com.epam.conference.command.base.LogoutCommand;
import com.epam.conference.command.base.SetLocaleCommand;
import com.epam.conference.command.base.ShowConferencesCommand;
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

public enum CommandType {
    
    ADDAPPLIC(new AddApplicationCommand()),
    ADDCONFERENCE(new AddConferenceCommand()),
    ADDREPORT(new AddReportCommand()),
    ADDSECTION(new AddSectionCommand()),
    ADDUSER(new AddUserCommand()),
    EDITAPPLIC(new EditApplicationCommand()),
    EDITCONFERENCE(new EditConferenceCommand()),
    EDITREPORT(new EditReportCommand()),
    EDITSECTION(new EditSectionCommand()),
    EDITUSER(new EditUserCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REMOVEAPPLIC(new RemoveApplicationCommand()),
    SEARCHAPPLICATION(new SearchApplicationCommand()),
    SEARCHCONFER(new SearchConferencesCommand()),
    SEARCHCONFERBYID(new SearchConferByIdCommand()),
    SEARCHCONFERENCEBYID(new SearchConferenceByIdCommand()),
    SEARCHREPORTBYID(new SearchReportByIdCommand()),
    SEARCHSECTION(new SearchSectionCommand()),
    SEARCHUSER(new SearchUserByLoginCommand()),
    SEARCHUSERAPPLIC(new SearchUserApplicCommand()),
    SEARCHUSERREPORT(new SearchUserReportCommand()),
    SETLOCALE(new SetLocaleCommand()),
    SHOWALLAPPLIC(new ShowAllApplicCommand()),
    SHOWALLCONFER(new ShowConferencesCommand()),
    SHOWALLUSER(new ShowAllUserCommand());
    
    private Command command;

    private CommandType(Command command) {
	this.command = command;
    }

    public Command getCommand() {
	return command;
    }

}

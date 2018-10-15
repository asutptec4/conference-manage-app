package com.epam.conference.util.constant;

/**
 * String constants represent URL paths used by web application.
 * 
 * @author Alexander Shishonok
 *
 */
public final class UriPathConstant {
    
    public static final String PATH_ALL_USER = "/controller?command=show-all-user";
    public static final String PATH_APPLICATION_ALL = "/controller?command=search-applic";
    public static final String PATH_APPLICATION_EDIT = "/jsp/admin/application.jsp";
    public static final String PATH_APPLICATION_NEW = "/jsp/new/application.jsp";
    public static final String PATH_CONFER_NEW = "/jsp/new/conference.jsp";
    public static final String PATH_CONFER_SEARCH = "/controller?command=search-confer";
    public static final String PATH_ERROR = "/jsp/error/error.jsp";
    public static final String PATH_INDEX = "/index.jsp";
    public static final String PATH_MAIN = "/jsp/main.jsp";
    public static final String PATH_MESSAGES = "/controller?command=show-messages";
    public static final String PATH_LOGIN = "/jsp/login.jsp";
    public static final String PATH_REPORT = "/jsp/new/report.jsp";
    public static final String PATH_REPORT_SEARCH = "/controller?command=search-user-report";
    public static final String PATH_SECTION_NEW = "/jsp/new/section.jsp";
    public static final String PATH_USER = "/jsp/new/user.jsp";
    public static final String PATH_USER_APPLIC = "/controller?command=search-user-applic";
    public static final String PATH_USER_SEARCH = "/controller?command=search-user";
    public static final String PATH_VIEW_APPLIC = "/jsp/view/application-view.jsp";
    public static final String PATH_VIEW_CONFER = "/jsp/view/conference-view.jsp";
    public static final String PATH_VIEW_MESSAGE = "/jsp/view/message-view.jsp";
    public static final String PATH_VIEW_REPORT = "/jsp/view/report-view.jsp";
    public static final String PATH_VIEW_USER = "/jsp/view/user-view.jsp";
    
    private UriPathConstant() {
    }
}

package com.epam.conference.entity;

/**
 * Helper class for working with applications for participation in the
 * conference. Contain all information describe application.
 * 
 * @author Alexander Shishonok
 *
 */
public class ApplicationInfo extends Entity {

    private static final long serialVersionUID = -8192565109139439244L;

    private long conferenceId;
    private String conferenceName;
    private long sectionId;
    private String sectionName;
    private long reportId;
    private String reportName;
    private long userId;
    private String login;
    private String userName;
    private long reportDate;
    private Status status;

    public ApplicationInfo() {
    }

    public ApplicationInfo(Application application) {
	this.setId(application.getId());
	this.sectionId = application.getSectionId();
	this.reportId = application.getReportId();
	this.reportDate = application.getReportDate();
	this.status = application.getStatus();
    }

    public long getConferenceId() {
	return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
	this.conferenceId = conferenceId;
    }

    public String getConferenceName() {
	return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
	this.conferenceName = conferenceName;
    }

    public long getSectionId() {
	return sectionId;
    }

    public void setSectionId(long sectionId) {
	this.sectionId = sectionId;
    }

    public String getSectionName() {
	return sectionName;
    }

    public void setSectionName(String sectionName) {
	this.sectionName = sectionName;
    }

    public long getReportId() {
	return reportId;
    }

    public void setReportId(long reportId) {
	this.reportId = reportId;
    }

    public String getReportName() {
	return reportName;
    }

    public void setReportName(String reportName) {
	this.reportName = reportName;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public long getReportDate() {
	return reportDate;
    }

    public void setReportDate(long reportDate) {
	this.reportDate = reportDate;
    }

    public Status getStatus() {
	return status;
    }

    public void setStatus(Status status) {
	this.status = status;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [conferenceId=")
		.append(conferenceId).append(", conferenceName=")
		.append(conferenceName).append(", sectionId=").append(sectionId)
		.append(", sectionName=").append(sectionName)
		.append(", reportId=").append(reportId).append(", reportName=")
		.append(reportName).append(", userId=").append(userId)
		.append(", login=").append(login).append(", userName=")
		.append(userName).append(", reportDate=").append(reportDate)
		.append(", status=").append(status).append("]");
	return builder.toString();
    }
}

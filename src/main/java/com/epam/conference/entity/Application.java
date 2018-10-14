package com.epam.conference.entity;

/**
 * The {@code Application} class described user's application for participation
 * in the conference.
 * 
 * @author Alexander Shishonok
 *
 */
public class Application extends Entity {

    private static final long serialVersionUID = -6586546264303470881L;

    /**
     * Field identify section for user's report.
     */
    private long sectionId;

    /**
     * Identify user's report for application.
     */
    private long reportId;

    /**
     * Date of report presentation.
     */
    private long reportDate;

    /**
     * Current status or report for conference.
     */
    private Status status;

    public long getSectionId() {
	return sectionId;
    }

    public void setSectionId(long sectionId) {
	this.sectionId = sectionId;
    }

    public long getReportId() {
	return reportId;
    }

    public void setReportId(long reportId) {
	this.reportId = reportId;
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
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (int) (reportDate ^ (reportDate >>> 32));
	result = prime * result + (int) (reportId ^ (reportId >>> 32));
	result = prime * result + (int) (sectionId ^ (sectionId >>> 32));
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Application other = (Application) obj;
	if (reportDate != other.reportDate)
	    return false;
	if (reportId != other.reportId)
	    return false;
	if (sectionId != other.sectionId)
	    return false;
	if (status == null) {
	    if (other.status != null)
		return false;
	} else if (!status.equals(other.status))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [sectionId=")
		.append(sectionId).append(", reportId=").append(reportId)
		.append(", reportDate=").append(reportDate).append(", status=")
		.append(status).append("]");
	return builder.toString();
    }
}

package com.epam.conference.entity;

public class Section extends Entity {

    private static final long serialVersionUID = 1311155836822235709L;

    private String name;
    private long conferenceId;
    private String description;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public long getConferenceId() {
	return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
	this.conferenceId = conferenceId;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (int) (conferenceId ^ (conferenceId >>> 32));
	result = prime * result
		+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	Section other = (Section) obj;
	if (conferenceId != other.conferenceId)
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [name=")
		.append(name).append(", conferenceId=").append(conferenceId)
		.append(", description=").append(description).append("]");
	return builder.toString();
    }

}

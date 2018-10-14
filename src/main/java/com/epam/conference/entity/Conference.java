package com.epam.conference.entity;

/**
 * The {@code Conference} class described conference entity of web app.
 * 
 * @author Alexander Shishonok
 *
 */
public class Conference extends Entity {

    private static final long serialVersionUID = 1600826375502537464L;

    /**
     * Conference name.
     */
    private String name;

    /**
     * Start date of conference.
     */
    private long startDate;

    /**
     * End date of conference.
     */
    private long endDate;

    /**
     * Description of conference.
     */
    private String description;

    /**
     * Conference location.
     */
    private String location;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public long getStartDate() {
	return startDate;
    }

    public void setStartDate(long startDate) {
	this.startDate = startDate;
    }

    public long getEndDate() {
	return endDate;
    }

    public void setEndDate(long endDate) {
	this.endDate = endDate;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((description == null) ? 0 : description.hashCode());
	result = prime * result + (int) (endDate ^ (endDate >>> 32));
	result = prime * result
		+ ((location == null) ? 0 : location.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + (int) (startDate ^ (startDate >>> 32));
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
	Conference other = (Conference) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (endDate != other.endDate)
	    return false;
	if (location == null) {
	    if (other.location != null)
		return false;
	} else if (!location.equals(other.location))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (startDate != other.startDate)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [name=")
		.append(name).append(", startDate=").append(startDate)
		.append(", endDate=").append(endDate).append(", description=")
		.append(description).append(", location=").append(location)
		.append("]");
	return builder.toString();
    }
}

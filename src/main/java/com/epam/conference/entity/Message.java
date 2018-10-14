package com.epam.conference.entity;

/**
 * The {@code Message} class described the message from one user to another.
 * 
 * @author Alexander Shishonok
 *
 */
public class Message extends Entity {

    private static final long serialVersionUID = 1959123396669688740L;

    /**
     * Time of message creation.
     */
    private long time;

    /**
     * Field identify of message sender.
     */
    private String from;

    /**
     * Field identify of message receiver.
     */
    private String to;

    /**
     * Text of message.
     */
    private String text;

    /**
     * Flag define did the receiver see the message.
     */
    private boolean isRead;

    public long getTime() {
	return time;
    }

    public void setTime(long time) {
	this.time = time;
    }

    public String getFrom() {
	return from;
    }

    public void setFrom(String from) {
	this.from = from;
    }

    public String getTo() {
	return to;
    }

    public void setTo(String to) {
	this.to = to;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public boolean isRead() {
	return isRead;
    }

    public void setRead(boolean isRead) {
	this.isRead = isRead;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((from == null) ? 0 : from.hashCode());
	result = prime * result + (isRead ? 1231 : 1237);
	result = prime * result + ((text == null) ? 0 : text.hashCode());
	result = prime * result + (int) (time ^ (time >>> 32));
	result = prime * result + ((to == null) ? 0 : to.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Message other = (Message) obj;
	if (from == null) {
	    if (other.from != null)
		return false;
	} else if (!from.equals(other.from))
	    return false;
	if (isRead != other.isRead)
	    return false;
	if (text == null) {
	    if (other.text != null)
		return false;
	} else if (!text.equals(other.text))
	    return false;
	if (time != other.time)
	    return false;
	if (to == null) {
	    if (other.to != null)
		return false;
	} else if (!to.equals(other.to))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [time=")
		.append(time).append(", from=").append(from).append(", to=")
		.append(to).append(", text=").append(text).append(", isRead=")
		.append(isRead).append("]");
	return builder.toString();
    }
}

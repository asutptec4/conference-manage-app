package com.epam.conference.entity;

import java.io.Serializable;

/**
 * Base class for application entities.
 * 
 * @author Alexander Shishonok
 */
public abstract class Entity implements Serializable, Cloneable {

    private static final long serialVersionUID = -6447342348009533087L;

    /**
     * Unique id for application entities. Identify record in database.
     */
    private long id;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
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
	Entity other = (Entity) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return getClass().getSimpleName() + " [id=" + id + "]";
    }
}

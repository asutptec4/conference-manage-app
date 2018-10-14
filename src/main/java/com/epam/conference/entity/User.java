package com.epam.conference.entity;

/**
 * Class describes the user.
 * 
 * @author Alexander Shishonok
 */
public class User extends Entity {

    private static final long serialVersionUID = -9170079717868693829L;

    /**
     * User's login in app.
     */
    private String login;
    
    /**
     * User's first name.
     */
    private String firstName;
    
    /**
     * User's last name.
     */
    private String lastName;
    
    /**
     * User role in app, for example admin or user.
     */
    private UserRole userRole;
    
    /**
     * User's emali.
     */
    private String email;
    
    /**
     * User's phone number.
     */
    private String phone;
    
    /**
     * Time of user registration.
     */
    private long createTime;
    
    /**
     * Status of user's account.
     */
    private boolean isBlocked;

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public UserRole getUserRole() {
	return userRole;
    }

    public void setUserRole(UserRole userRole) {
	this.userRole = userRole;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public long getCreateTime() {
	return createTime;
    }

    public void setCreateTime(long createTime) {
	this.createTime = createTime;
    }

    public boolean isBlocked() {
	return isBlocked;
    }

    public void setBlocked(boolean isBlocked) {
	this.isBlocked = isBlocked;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (int) (createTime ^ (createTime >>> 32));
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result
		+ ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + (isBlocked ? 1231 : 1237);
	result = prime * result
		+ ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((login == null) ? 0 : login.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result
		+ ((userRole == null) ? 0 : userRole.hashCode());
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
	User other = (User) obj;
	if (createTime != other.createTime)
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (isBlocked != other.isBlocked)
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (login == null) {
	    if (other.login != null)
		return false;
	} else if (!login.equals(other.login))
	    return false;
	if (phone == null) {
	    if (other.phone != null)
		return false;
	} else if (!phone.equals(other.phone))
	    return false;
	if (userRole != other.userRole)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append(getClass().getSimpleName()).append(" [login=")
		.append(login).append(", firstName=").append(firstName)
		.append(", lastName=").append(lastName).append(", userRole=")
		.append(userRole).append(", email=").append(email)
		.append(", phone=").append(phone).append(", createTime=")
		.append(createTime).append(", isBlocked=").append(isBlocked)
		.append("]");
	return builder.toString();
    }
}

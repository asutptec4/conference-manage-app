package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.UserDao;
import com.epam.conference.entity.User;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.PasswordEncryptor;
import com.epam.conference.util.validator.InputValidator;

/**
 * Service class is used for working with {@link User} objects via DAO layer
 * classes. {@code UserService} is singleton.
 * 
 * @author Alexander Shishonok
 *
 */
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
	return INSTANCE;
    }

    /**
     * Create user object and add user in database.
     * 
     * @param login
     *            user login.
     * @param password
     *            user password.
     * @param firstName
     *            user first name.
     * @param lastName
     *            user last name.
     * @param email
     *            user email address.
     * @param phone
     *            user phone.
     * @return true if user add in database.
     * @throws ConferenceAppServiceException
     *             if error is occurred to add new user.
     */
    public boolean addUser(String login, String password, String firstName,
	    String lastName, String email, String phone)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	User user = new User();
	user.setLogin(login);
	user.setUserRole(UserRole.USER);
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setEmail(email);
	user.setPhone(phone);
	user.setBlocked(false);
	try (UserDao dao = new UserDao()) {
	    flag = InputValidator.validateUser(login, firstName, lastName,
		    email, phone) && InputValidator.validatePassword(password)
		    && dao.add(user, password);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Can't add new user in db",
		    e);
	}
	return flag;
    }

    /**
     * Check if user registered in application. This method is used for user
     * authentication.
     * 
     * @param login
     *            user login.
     * @param password
     *            user password
     * @return true if right user.
     * @throws ConferenceAppServiceException
     *             if fail to check user in database.
     */
    public boolean checkUser(String login, String password)
	    throws ConferenceAppServiceException {
	try (UserDao dao = new UserDao()) {
	    if (dao.findByLogin(login).isPresent()) {
		return dao.findPasswordByLogin(login)
			.flatMap(input -> Optional.ofNullable(PasswordEncryptor
				.encrypt(password).equals(input)))
			.orElse(false);
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to check user", e);
	}
	return false;
    }

    /**
     * Find user by login.
     * 
     * @param login
     *            user login wrapped in {@link Optional}.
     * @return an {@link User} object.
     * @throws ConferenceAppServiceException
     *             if fail to find user in database.
     */
    public Optional<User> findUserByLogin(String login)
	    throws ConferenceAppServiceException {
	Optional<User> user = Optional.empty();
	try (UserDao dao = new UserDao()) {
	    user = dao.findByLogin(login);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Can't find user in db with login =" + login, e);
	}
	return user;
    }

    /**
     * Method return all user registered in app, except admins, as list.
     * 
     * @return an {@link List} object contain all users.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public List<User> getUserList() throws ConferenceAppServiceException {
	List<User> users = new ArrayList<>();
	try (UserDao dao = new UserDao()) {
	    users = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to get all user from db.", e);
	}
	List<User> result = users.stream()
		.filter(u -> u.getUserRole() == UserRole.USER)
		.collect(Collectors.toList());
	return result;
    }

    /**
     * Method return type of user role.
     * 
     * @param login
     *            user login in app.
     * @return {@code UserRole} object wrapped in {@link Optional}.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public Optional<UserRole> getUserRole(String login)
	    throws ConferenceAppServiceException {
	Optional<UserRole> role = Optional.empty();
	try (UserDao dao = new UserDao()) {
	    role = Optional.of(dao.findByLogin(login).get().getUserRole());
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to get user role", e);
	}
	return role;
    }

    /**
     * Update user info.
     * 
     * @param login
     *            user login.
     * @param password
     *            user password.
     * @param firstName
     *            user first name.
     * @param lastName
     *            user last name.
     * @param email
     *            user email address.
     * @param phone
     *            user phone.
     * @return true if user updated.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public boolean updateUser(String login, String password, String firstName,
	    String lastName, String email, String phone)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	UserDao dao = new UserDao();
	try {
	    dao.startTransaction();
	    User user = dao.findByLogin(login).get();
	    user.setLogin(login);
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setEmail(email);
	    user.setPhone(phone);
	    flag = InputValidator.validateUser(login, firstName, lastName,
		    email, phone) && dao.update(user);
	    if (InputValidator.validatePassword(password)) {
		flag = flag && dao.updatePasswordByLogin(login, password);
	    }
	    // rollback if pass update false
	    if (flag == false) {
		throw new ConferenceAppDaoException("Rollback all update");
	    }
	} catch (ConferenceAppDaoException e) {
	    dao.rollbackTransaction();
	    throw new ConferenceAppServiceException("Can't update user in db",
		    e);
	} finally {
	    dao.close();
	}
	return flag;
    }
}

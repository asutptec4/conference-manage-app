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

public class UserService {

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
	    flag = dao.add(user, password);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Can't add new user in db",
		    e);
	}
	return flag;
    }

    public boolean checkLogin(String enterLogin, String enterPass)
	    throws ConferenceAppServiceException {
	// TODO check is blocked
	try (UserDao dao = new UserDao()) {
	    if (dao.findByLogin(enterLogin).isPresent()) {
		return dao.findPasswordByLogin(enterLogin)
			.flatMap(input -> Optional.ofNullable(PasswordEncryptor
				.encrypt(enterPass).equals(input)))
			.orElse(false);
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to check user", e);
	}
	return false;
    }

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

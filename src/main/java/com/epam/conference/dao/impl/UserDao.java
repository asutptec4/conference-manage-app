package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.User;
import com.epam.conference.entity.UserRole;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.util.PasswordEncryptor;

/**
 * DAO class used for working with {@code User} objects and modifying data in
 * corresponding tables of database.
 * 
 * @author Alexander Shishonok
 *
 */
public class UserDao extends AbstractDao<User> {

    private static final String FIND_ALL = "SELECT u.id, u.login, u.firstname, u.lastname, r.name, u.email, u.phone, u.create_time, u.is_blocked"
	    + " FROM user AS u JOIN role AS r ON u.role_id = r.id";
    private static final String FIND_BY_ID = "SELECT u.id, u.login, u.firstname, u.lastname, r.name, u.email, u.phone, u.create_time, u.is_blocked"
	    + " FROM user AS u JOIN role AS r ON u.role_id = r.id WHERE u.id = ?";
    private static final String FIND_BY_LOGIN = "SELECT u.id, u.login, u.firstname, u.lastname, r.name, u.email, u.phone, u.create_time, u.is_blocked"
	    + " FROM user AS u JOIN role AS r ON u.role_id = r.id WHERE u.login = ?";
    private static final String FIND_PASS_BY_LOGIN = "SELECT password FROM user WHERE login = ?";
    private static final String INSERT = "INSERT INTO user (id, login, password, firstname, lastname, role_id, email, phone, create_time, is_blocked)"
	    + " VALUES (null, ?, ?, ?, ?, (SELECT id FROM role WHERE name = ?), ?, ?, UNIX_TIMESTAMP(), default)";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE = "UPDATE user AS u SET u.login = ?, u.firstname = ?, u.lastname = ?,"
	    + " u.role_id = (SELECT id FROM role WHERE name = ?), u.email = ?, u.phone = ?, u.is_blocked = ? WHERE u.id = ?";
    private static final String UPDATE_PASS_BY_LOGIN = "UPDATE user SET password = ? WHERE login = ?";

    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String PASS = "password";
    private static final String FNAME = "firstname";
    private static final String LNAME = "lastname";
    private static final String ROLE_NAME = "name";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String CREATE_TIME = "create_time";
    private static final String IS_BLOCKED = "is_blocked";

    @Override
    public boolean add(User entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getLogin());
	    statement.setString(2,
		    PasswordEncryptor.encrypt(entity.getLogin()));
	    statement.setString(3, entity.getFirstName());
	    statement.setString(4, entity.getLastName());
	    statement.setString(5, entity.getUserRole().toString());
	    statement.setString(6, entity.getEmail());
	    statement.setString(7, entity.getPhone());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add user to db.", e);
	}
    }

    /**
     * Overloaded method which add user entity with password field.
     * 
     * @param entity
     *            an user object
     * @param password
     *            string contain user password
     * @return if add database table entry.
     * @throws ConferenceAppDaoException
     *             if error is occurred execute command.
     */
    public boolean add(User entity, String password)
	    throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getLogin());
	    statement.setString(2, PasswordEncryptor.encrypt(password));
	    statement.setString(3, entity.getFirstName());
	    statement.setString(4, entity.getLastName());
	    statement.setString(5, entity.getUserRole().toString());
	    statement.setString(6, entity.getEmail());
	    statement.setString(7, entity.getPhone());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add user to db.", e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(DELETE)) {
	    statement.setLong(1, id);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't delete user.", e);
	}
    }

    @Override
    public Optional<User> findById(long id) throws ConferenceAppDaoException {
	User user = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		user = createUser(resultSet);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find user by id.", e);
	}
	return Optional.ofNullable(user);
    }

    public Optional<User> findByLogin(String name)
	    throws ConferenceAppDaoException {
	User user = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_LOGIN)) {
	    statement.setString(1, name);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		user = createUser(resultSet);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find user by name.", e);
	}
	return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() throws ConferenceAppDaoException {
	List<User> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createUser(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find users in db.", e);
	}
	return list;
    }

    public Optional<String> findPasswordByLogin(String login)
	    throws ConferenceAppDaoException {
	String pass = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_PASS_BY_LOGIN)) {
	    statement.setString(1, login);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		pass = resultSet.getString(PASS);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Fail to query find password by login", e);
	}
	return Optional.ofNullable(pass);
    }

    @Override
    public boolean update(User entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setString(1, entity.getLogin());
	    statement.setString(2, entity.getFirstName());
	    statement.setString(3, entity.getLastName());
	    statement.setString(4, entity.getUserRole().toString());
	    statement.setString(5, entity.getEmail());
	    statement.setString(6, entity.getPhone());
	    statement.setBoolean(7, entity.isBlocked());
	    statement.setLong(8, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update user.", e);
	}
    }

    public boolean updatePasswordByLogin(String login, String password)
	    throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE_PASS_BY_LOGIN)) {
	    statement.setString(1, PasswordEncryptor.encrypt(password));
	    statement.setString(2, login);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update user password.",
		    e);
	}
    }

    private User createUser(ResultSet resultSet) throws SQLException {
	User user = new User();
	user.setId(resultSet.getLong(ID));
	user.setLogin(resultSet.getString(LOGIN));
	user.setFirstName(resultSet.getString(FNAME));
	user.setLastName(resultSet.getString(LNAME));
	user.setUserRole(
		UserRole.valueOf(resultSet.getString(ROLE_NAME).toUpperCase()));
	user.setEmail(resultSet.getString(EMAIL));
	user.setPhone(resultSet.getString(PHONE));
	user.setCreateTime(resultSet.getLong(CREATE_TIME));
	user.setBlocked(resultSet.getBoolean(IS_BLOCKED));
	return user;
    }
}
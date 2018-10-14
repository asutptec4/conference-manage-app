package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Message;
import com.epam.conference.exception.ConferenceAppDaoException;

/**
 * {@code MessageDao} class used for working with {@code Message} objects and
 * modifying data in corresponding table of database.
 * 
 * @author Alexander Shishonok
 *
 */
public class MessageDao extends AbstractDao<Message> {

    private static final String FIND_ALL = "SELECT m.time, u1.login AS user_from, u2.login AS user_to, m.text, m.is_read FROM message AS m"
	    + " JOIN user AS u1 ON u1.id = m.user_from JOIN user AS u2 ON u2.id = m.user_to";
    private static final String FIND_ALL_UNREAD = "SELECT m.time, u1.login AS user_from, u2.login AS user_to, m.text, m.is_read FROM message AS m"
	    + " JOIN user AS u1 ON u1.id = m.user_from JOIN user AS u2 ON u2.id = m.user_to WHERE m.is_read = 0";
    private static final String FIND_FROM = "SELECT m.time, u1.login AS user_from, u2.login AS user_to, m.text, m.is_read FROM message AS m"
	    + " JOIN user AS u1 ON u1.id = m.user_from JOIN user AS u2 ON u2.id = m.user_to WHERE u1.login = ?";
    private static final String FIND_TO = "SELECT m.time, u1.login AS user_from, u2.login AS user_to, m.text, m.is_read FROM message AS m"
	    + " JOIN user AS u1 ON u1.id = m.user_from JOIN user AS u2 ON u2.id = m.user_to WHERE u2.login = ?";
    private static final String INSERT = "INSERT INTO message VALUES (UNIX_TIMESTAMP(), (SELECT id FROM user WHERE login = ?),"
	    + " (SELECT id FROM user WHERE login = ?), ?, default)";
    private static final String UPDATE = "UPDATE message SET user_to = (SELECT id FROM user WHERE login = ?), text = ?, is_read = ?"
	    + " WHERE time = ? AND user_from = (SELECT id FROM user WHERE login = ?)";

    private static final String TIME = "time";
    private static final String SENDER = "user_from";
    private static final String RECEIVER = "user_to";
    private static final String TEXT = "text";
    private static final String IS_READ = "is_read";

    @Override
    public boolean add(Message entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getFrom());
	    statement.setString(2, entity.getTo());
	    statement.setString(3, entity.getText());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add message to db.", e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	throw new ConferenceAppDaoException("Unsupported operation");
    }

    @Override
    public Optional<Message> findById(long id)
	    throws ConferenceAppDaoException {
	throw new ConferenceAppDaoException("Unsupported operation");
    }

    @Override
    public List<Message> findAll() throws ConferenceAppDaoException {
	List<Message> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Message message = createMessage(resultSet);
		list.add(message);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any messages in db.", e);
	}
	return list;
    }

    public List<Message> findAllUnread() throws ConferenceAppDaoException {
	List<Message> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL_UNREAD)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Message message = createMessage(resultSet);
		list.add(message);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find unread messages in db.", e);
	}
	return list;
    }

    public List<Message> findFromUser(String username)
	    throws ConferenceAppDaoException {
	List<Message> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_FROM)) {
	    statement.setString(1, username);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Message message = createMessage(resultSet);
		list.add(message);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any messages from user=" + username, e);
	}
	return list;
    }

    public List<Message> findToUser(String username)
	    throws ConferenceAppDaoException {
	List<Message> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_TO)) {
	    statement.setString(1, username);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Message message = createMessage(resultSet);
		list.add(message);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any messages to user=" + username, e);
	}
	return list;
    }

    @Override
    public boolean update(Message entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setString(1, entity.getTo());
	    statement.setString(2, entity.getText());
	    statement.setBoolean(3, entity.isRead());
	    statement.setLong(4, entity.getTime());
	    statement.setString(5, entity.getFrom());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update message.", e);
	}
    }

    private Message createMessage(ResultSet resultSet) throws SQLException {
	Message message = new Message();
	message.setTime(resultSet.getLong(TIME));
	message.setFrom(resultSet.getString(SENDER));
	message.setTo(resultSet.getString(RECEIVER));
	message.setText(resultSet.getString(TEXT));
	message.setRead(resultSet.getBoolean(IS_READ));
	return message;
    }
}
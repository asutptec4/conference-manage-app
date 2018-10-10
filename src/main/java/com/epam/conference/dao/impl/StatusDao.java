package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Status;
import com.epam.conference.exception.ConferenceAppDaoException;

public class StatusDao extends AbstractDao<Status> {

    private static final String FIND_ALL = "SELECT id, name FROM status";
    private static final String FIND_BY_ID = "SELECT id, name FROM status WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT id, name FROM status WHERE name LIKE ?";
    private static final String INSERT = "INSERT INTO status VALUES (null, ?)";
    private static final String UPDATE = "UPDATE status SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM status WHERE id = ?";

    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public boolean add(Status entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getName());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add new status to db.",
		    e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(DELETE)) {
	    statement.setLong(1, id);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't delete status from db.",
		    e);
	}
    }

    @Override
    public Optional<Status> findById(long id) throws ConferenceAppDaoException {
	Status status = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		status = new Status();
		status.setId(id);
		status.setName(resultSet.getString(NAME));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find status by id.", e);
	}
	return Optional.ofNullable(status);
    }

    public Optional<Status> findByName(String str)
	    throws ConferenceAppDaoException {
	Status status = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_NAME)) {
	    statement.setString(1, str);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		status = new Status();
		status.setId(resultSet.getLong(ID));
		status.setName(resultSet.getString(NAME));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find status by id.", e);
	}
	return Optional.ofNullable(status);
    }

    @Override
    public List<Status> findAll() throws ConferenceAppDaoException {
	List<Status> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Status status = new Status();
		status.setId(resultSet.getLong(ID));
		status.setName(resultSet.getString(NAME));
		list.add(status);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find any status in db.",
		    e);
	}
	return list;
    }

    @Override
    public boolean update(Status entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setString(1, entity.getName());
	    statement.setLong(2, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update status.", e);
	}
    }
}
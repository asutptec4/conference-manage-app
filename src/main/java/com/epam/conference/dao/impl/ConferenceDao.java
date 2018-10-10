package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Conference;
import com.epam.conference.exception.ConferenceAppDaoException;

public class ConferenceDao extends AbstractDao<Conference> {

    private static final String FIND_ALL = "SELECT id, name, startdate, enddate, location, description FROM conference";
    private static final String FIND_BY_ID = "SELECT name, startdate, enddate, location, description FROM conference WHERE id = ?";
    private static final String INSERT = "INSERT INTO conference (id, name, startdate, enddate, location, description)"
	    + " VALUES (null, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE conference SET name = ?, startdate = ?, enddate = ?, location = ?, description = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM conference WHERE id = ?";
    
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String START = "startdate";
    private static final String END = "enddate";
    private static final String DESC = "description";
    private static final String LOCATION = "location";

    @Override
    public boolean add(Conference entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getName());
	    statement.setLong(2, entity.getStartDate());
	    statement.setLong(3, entity.getEndDate());
	    statement.setString(4, entity.getLocation());
	    statement.setString(5, entity.getDescription());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add conference to db.", e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(DELETE)) {
	    statement.setLong(1, id);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't delete conference from db.", e);
	}
    }

    @Override
    public Optional<Conference> findById(long id) throws ConferenceAppDaoException {
	Conference conference = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		conference = new Conference();
		conference.setId(id);
		conference.setName(resultSet.getString(NAME));
		conference.setStartDate(resultSet.getLong(START));
		conference.setEndDate(resultSet.getLong(END));
		conference.setLocation(resultSet.getString(LOCATION));
		conference.setDescription(resultSet.getString(DESC));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find conference by id.", e);
	}
	return Optional.ofNullable(conference);
    }

    @Override
    public List<Conference> findAll() throws ConferenceAppDaoException {
	List<Conference> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Conference conference = new Conference();
		conference.setId(resultSet.getLong(ID));
		conference.setName(resultSet.getString(NAME));
		conference.setStartDate(resultSet.getLong(START));
		conference.setEndDate(resultSet.getLong(END));
		conference.setLocation(resultSet.getString(LOCATION));
		conference.setDescription(resultSet.getString(DESC));
		list.add(conference);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find any conferences in db.", e);
	}
	return list;
    }

    @Override
    public boolean update(Conference entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setString(1, entity.getName());
	    statement.setLong(2, entity.getStartDate());
	    statement.setLong(3, entity.getEndDate());
	    statement.setString(4, entity.getLocation());
	    statement.setString(5, entity.getDescription());
	    statement.setLong(6, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update conference record in db.", e);
	}
    }
}

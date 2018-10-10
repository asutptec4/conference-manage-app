package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Section;
import com.epam.conference.exception.ConferenceAppDaoException;

public class SectionDao extends AbstractDao<Section> {

    private static final String FIND_ALL = "SELECT id, name, conference_id, description FROM section";
    private static final String FIND_BY_ID = "SELECT id, name, conference_id, description FROM section WHERE id = ?";
    private static final String FIND_BY_CONFERID = "SELECT id, name, conference_id, description FROM section WHERE conference_id = ?";
    private static final String INSERT = "INSERT INTO section (id, name, conference_id, description)"
	    + " VALUES (null, ?, ?, ?)";
    private static final String UPDATE = "UPDATE section SET name = ?, conference_id = ?, description = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM section WHERE id = ?";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESC = "description";
    private static final String CONF_ID = "conference_id";

    @Override
    public boolean add(Section entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setString(1, entity.getName());
	    statement.setLong(2, entity.getConferenceId());
	    statement.setString(3, entity.getDescription());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add section to db.", e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(DELETE)) {
	    statement.setLong(1, id);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't delete section from db.",
		    e);
	}
    }

    @Override
    public Optional<Section> findById(long id)
	    throws ConferenceAppDaoException {
	Section section = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		section = new Section();
		section.setId(id);
		section.setName(resultSet.getString(NAME));
		section.setConferenceId(resultSet.getLong(CONF_ID));
		section.setDescription(resultSet.getString(DESC));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find section by id in db.", e);
	}
	return Optional.ofNullable(section);
    }

    public List<Section> findByConferenceId(long conferenceId)
	    throws ConferenceAppDaoException {
	List<Section> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_CONFERID)) {
	    statement.setLong(1, conferenceId);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Section section = new Section();
		section.setId(resultSet.getLong(ID));
		section.setName(resultSet.getString(NAME));
		section.setConferenceId(resultSet.getLong(CONF_ID));
		section.setDescription(resultSet.getString(DESC));
		list.add(section);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any sections in db.", e);
	}
	return list;
    }

    @Override
    public List<Section> findAll() throws ConferenceAppDaoException {
	List<Section> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Section section = new Section();
		section.setId(resultSet.getLong(ID));
		section.setName(resultSet.getString(NAME));
		section.setConferenceId(resultSet.getLong(CONF_ID));
		section.setDescription(resultSet.getString(DESC));
		list.add(section);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any sections in db.", e);
	}
	return list;
    }

    @Override
    public boolean update(Section entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setString(1, entity.getName());
	    statement.setLong(2, entity.getConferenceId());
	    statement.setString(3, entity.getDescription());
	    statement.setLong(4, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't update section record in db.", e);
	}
    }
}

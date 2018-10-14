package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Application;
import com.epam.conference.entity.Status;
import com.epam.conference.exception.ConferenceAppDaoException;

/**
 * DAO class used for working with {@code Application} objects and modifying
 * data in corresponding table of database.
 * 
 * @author Alexander Shishonok
 *
 */
public class ApplicationDao extends AbstractDao<Application> {

    private static final String FIND_ALL = "SELECT a.id, a.section_id, a.report_id, a.report_date, a.status_id, s.name"
	    + " FROM application AS a JOIN status AS s ON a.status_id = s.id";
    private static final String FIND_BY_ID = "SELECT a.id, a.section_id, a.report_id, a.report_date, a.status_id, s.name"
	    + " FROM application AS a JOIN status AS s ON a.status_id = s.id WHERE a.id = ?";
    private static final String FIND_BY_USERID = "SELECT a.id, a.section_id, a.report_id, a.report_date, a.status_id, s.name"
	    + " FROM application AS a JOIN status AS s ON a.status_id = s.id"
	    + " JOIN report as r ON a.report_id = r.id WHERE r.user_id = ?";
    private static final String FIND_BY_REPORTID = "SELECT a.id, a.section_id, a.report_id, a.report_date, a.status_id, s.name"
	    + " FROM application AS a JOIN status AS s ON a.status_id = s.id WHERE a.report_id = ?";
    private static final String FIND_BY_SECTIONID = "SELECT a.id, a.section_id, a.report_id, a.report_date, a.status_id, s.name"
	    + " FROM application AS a JOIN status AS s ON a.status_id = s.id WHERE a.section_id = ?";
    private static final String INSERT = "INSERT INTO application (id, section_id, report_id, report_date, status_id)"
	    + " VALUES (null, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM application WHERE id = ?";
    private static final String UPDATE = "UPDATE application SET section_id = ?, report_id = ?, report_date = ?, status_id = ? WHERE id = ?";

    private static final String ID = "id";
    private static final String SECTION_ID = "section_id";
    private static final String REPORT_ID = "report_id";
    private static final String DATE = "report_date";
    private static final String STATUS_ID = "status_id";
    private static final String STATUS_NAME = "name";

    @Override
    public boolean add(Application entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setLong(1, entity.getSectionId());
	    statement.setLong(2, entity.getReportId());
	    statement.setLong(3, entity.getReportDate());
	    statement.setLong(4, entity.getStatus().getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add application to db.",
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
	    throw new ConferenceAppDaoException(
		    "Can't delete application from db.", e);
	}
    }

    @Override
    public Optional<Application> findById(long id)
	    throws ConferenceAppDaoException {
	Application application = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		application = createApplication(resultSet);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find application by id.",
		    e);
	}
	return Optional.ofNullable(application);
    }

    public List<Application> findByReportId(long reportId)
	    throws ConferenceAppDaoException {
	List<Application> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_REPORTID)) {
	    statement.setLong(1, reportId);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createApplication(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find application by report id.", e);
	}
	return list;
    }

    public List<Application> findBySectionId(long sectionId)
	    throws ConferenceAppDaoException {
	List<Application> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_SECTIONID)) {
	    statement.setLong(1, sectionId);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createApplication(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find application by section id.", e);
	}
	return list;
    }

    public List<Application> findByUserId(long userId)
	    throws ConferenceAppDaoException {
	List<Application> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_USERID)) {
	    statement.setLong(1, userId);
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createApplication(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find application from user in db.", e);
	}
	return list;
    }

    @Override
    public List<Application> findAll() throws ConferenceAppDaoException {
	List<Application> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createApplication(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't find any application in db.", e);
	}
	return list;
    }

    @Override
    public boolean update(Application entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setLong(1, entity.getSectionId());
	    statement.setLong(2, entity.getReportId());
	    statement.setLong(3, entity.getReportDate());
	    statement.setLong(4, entity.getStatus().getId());
	    statement.setLong(5, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException(
		    "Can't update application record in db.", e);
	}
    }

    private Application createApplication(ResultSet resultSet)
	    throws SQLException {
	Application application = new Application();
	application.setId(resultSet.getLong(ID));
	application.setSectionId(resultSet.getLong(SECTION_ID));
	application.setReportId(resultSet.getLong(REPORT_ID));
	application.setReportDate(resultSet.getLong(DATE));
	Status status = new Status();
	status.setId(resultSet.getLong(STATUS_ID));
	status.setName(resultSet.getString(STATUS_NAME));
	application.setStatus(status);
	return application;
    }
}

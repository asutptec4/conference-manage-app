package com.epam.conference.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.conference.dao.AbstractDao;
import com.epam.conference.entity.Report;
import com.epam.conference.exception.ConferenceAppDaoException;

/**
 * {@code ReportDAO} class used for working with {@code Report} objects and
 * modifying data in corresponding table of database.
 * 
 * @author Alexander Shishonok
 *
 */
public class ReportDao extends AbstractDao<Report> {

    private static final String FIND_ALL = "SELECT id, user_id, report_name, description FROM report";
    private static final String FIND_BY_ID = "SELECT id, user_id, report_name, description"
	    + " FROM report WHERE id = ?";
    private static final String FIND_BY_NAME = "SELECT id, user_id, report_name, description"
	    + " FROM report WHERE report_name = ?";
    private static final String INSERT = "INSERT INTO report (id, user_id, report_name, description)"
	    + " VALUES (null, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM report WHERE id = ?";
    private static final String UPDATE = "UPDATE report SET user_id = ?, report_name = ?, description = ? WHERE id = ?";

    private static final String ID = "id";
    private static final String USERID = "user_id";
    private static final String NAME = "report_name";
    private static final String DESC = "description";

    @Override
    public boolean add(Report entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(INSERT)) {
	    statement.setLong(1, entity.getUserId());
	    statement.setString(2, entity.getName());
	    statement.setString(3, entity.getDescription());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't add report to db.", e);
	}
    }

    @Override
    public boolean delete(long id) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(DELETE)) {
	    statement.setLong(1, id);
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't delete report from db.",
		    e);
	}
    }

    @Override
    public Optional<Report> findById(long id) throws ConferenceAppDaoException {
	Report report = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_ID)) {
	    statement.setLong(1, id);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		report = createReport(resultSet);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find report by id.", e);
	}
	return Optional.ofNullable(report);
    }

    public Optional<Report> findByName(String name)
	    throws ConferenceAppDaoException {
	Report report = null;
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_BY_NAME)) {
	    statement.setString(1, name);
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
		report = createReport(resultSet);
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't find report by name.",
		    e);
	}
	return Optional.ofNullable(report);
    }

    @Override
    public List<Report> findAll() throws ConferenceAppDaoException {
	List<Report> list = new ArrayList<>();
	try (PreparedStatement statement = connection
		.getPrepareStatement(FIND_ALL)) {
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		list.add(createReport(resultSet));
	    }
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't any report in db.", e);
	}
	return list;
    }

    @Override
    public boolean update(Report entity) throws ConferenceAppDaoException {
	try (PreparedStatement statement = connection
		.getPrepareStatement(UPDATE)) {
	    statement.setLong(1, entity.getUserId());
	    statement.setString(2, entity.getName());
	    statement.setString(3, entity.getDescription());
	    statement.setLong(4, entity.getId());
	    return statement.executeUpdate() > 0;
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't update report.", e);
	}
    }

    private Report createReport(ResultSet resultSet) throws SQLException {
	Report report = new Report();
	report.setId(resultSet.getLong(ID));
	report.setUserId(resultSet.getLong(USERID));
	report.setName(resultSet.getString(NAME));
	report.setDescription(resultSet.getString(DESC));
	return report;
    }
}

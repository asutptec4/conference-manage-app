package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.ReportDao;
import com.epam.conference.entity.Report;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;

/**
 * Service class is used for working with {@link Report} objects via DAO layer
 * classes.
 * 
 * @author Alexander Shishonok
 *
 */
public class ReportService {

    private static final ReportService INSTANCE = new ReportService();

    private ReportService() {
    }

    public static ReportService getInstance() {
	return INSTANCE;
    }

    /**
     * Add new report.
     * 
     * @param userId
     *            user identifier.
     * @param name
     *            name of report.
     * @param description
     *            report description.
     * @return true if report add in database.
     * @throws ConferenceAppServiceException
     *             if error is occurred added new report.
     */
    public boolean addReport(long userId, String name, String description)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	Report report = new Report();
	report.setName(name);
	report.setDescription(description);
	report.setUserId(userId);
	try (ReportDao dao = new ReportDao()) {
	    flag = dao.add(report);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Can't add new report", e);
	}
	return flag;
    }

    /**
     * Method find report by id.
     * 
     * @param id
     *            report identifier.
     * @return an {@link Report} object wrapped in {@link Optional}.
     * @throws ConferenceAppServiceException
     *             if fail to find report in database.
     */
    public Optional<Report> findReportByid(long id)
	    throws ConferenceAppServiceException {
	Optional<Report> report = Optional.empty();
	try (ReportDao dao = new ReportDao()) {
	    report = dao.findById(id);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find report by id=" + id, e);
	}
	return report;
    }

    /**
     * Find all user's reports.
     * 
     * @param userId
     *            user identifier.
     * @return an {@link List} object contains all user reports.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public List<Report> findUserReports(long userId)
	    throws ConferenceAppServiceException {
	List<Report> reports = new ArrayList<>();
	try (ReportDao dao = new ReportDao()) {
	    reports = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to get report list.",
		    e);
	}
	reports = reports.stream().filter(r -> {
	    return r.getUserId() == userId;
	}).collect(Collectors.toList());
	return reports;
    }

    /**
     * Update report information.
     * 
     * @param id
     *            report identifier.
     * @param userId
     *            user who create the report.
     * @param name
     *            report name.
     * @param description
     *            report description.
     * @return true if report info updated.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public boolean updateReport(long id, long userId, String name,
	    String description) throws ConferenceAppServiceException {
	boolean flag = false;
	Report report = new Report();
	report.setId(id);
	report.setName(name);
	report.setDescription(description);
	report.setUserId(userId);
	try (ReportDao dao = new ReportDao()) {
	    flag = dao.update(report);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Can't update report with id=" + id, e);
	}
	return flag;
    }
}
package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.ReportDao;
import com.epam.conference.entity.Report;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;

public class ReportService {

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
	    throw new ConferenceAppServiceException(
		    "Can't add new report", e);
	}
	return flag;
    }

    public Optional<Report> findReportByid(long reportId)
	    throws ConferenceAppServiceException {
	Optional<Report> report = Optional.empty();
	try (ReportDao dao = new ReportDao()) {
	    report = dao.findById(reportId);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find report by id=" + reportId, e);
	}
	return report;
    }

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
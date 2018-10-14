package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.ApplicationDao;
import com.epam.conference.dao.impl.ConferenceDao;
import com.epam.conference.dao.impl.ReportDao;
import com.epam.conference.dao.impl.SectionDao;
import com.epam.conference.dao.impl.StatusDao;
import com.epam.conference.dao.impl.UserDao;
import com.epam.conference.entity.Application;
import com.epam.conference.entity.ApplicationInfo;
import com.epam.conference.entity.Conference;
import com.epam.conference.entity.Report;
import com.epam.conference.entity.Section;
import com.epam.conference.entity.Status;
import com.epam.conference.entity.User;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.DateTimeConverter;

public class ApplicationService {

    private static final ApplicationService INSTANCE = new ApplicationService();

    private ApplicationService() {
    }

    public static ApplicationService getInstance() {
	return INSTANCE;
    }

    public boolean addApplication(long sectionId, long reportId, String date)
	    throws ConferenceAppServiceException {
	Application application = new Application();
	application.setReportId(reportId);
	application.setSectionId(sectionId);
	if (date != null && date.length() > 0) {
	    application.setReportDate(DateTimeConverter.convertToLong(date));
	}
	Status status = new Status();
	try (StatusDao dao = new StatusDao()) {
	    Optional<Status> optional = dao.findByName("%new%");
	    if (optional.isPresent()) {
		status.setId(optional.get().getId());
		status.setName(optional.get().getName());
	    } else {
		throw new ConferenceAppDaoException(
			"Not found 'new' status in db");
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to add new application", e);
	}
	application.setStatus(status);
	boolean flag = false;
	try (ApplicationDao dao = new ApplicationDao()) {
	    flag = dao.add(application);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to add new application", e);
	}
	return flag;
    }

    public List<ApplicationInfo> getApplicationInfoList()
	    throws ConferenceAppServiceException {
	List<ApplicationInfo> applicList = new ArrayList<>();
	try (ApplicationDao applicDao = new ApplicationDao()) {
	    applicDao.findAll().forEach(
		    applic -> applicList.add(new ApplicationInfo(applic)));
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	try (ReportDao reportDao = new ReportDao()) {
	    List<Report> reportList = reportDao.findAll();
	    applicList.forEach(applicInf -> {
		for (Report report : reportList) {
		    if (report.getId() == applicInf.getReportId()) {
			applicInf.setReportName(report.getName());
			applicInf.setUserId(report.getUserId());
		    }
		}
	    });
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	try (SectionDao sectionDao = new SectionDao()) {
	    List<Section> sectionList = sectionDao.findAll();
	    applicList.forEach(applicInf -> {
		for (Section section : sectionList) {
		    if (section.getId() == applicInf.getSectionId()) {
			applicInf.setSectionName(section.getName());
			applicInf.setConferenceId(section.getConferenceId());
		    }
		}
	    });
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	try (ConferenceDao conferenceDao = new ConferenceDao()) {
	    List<Conference> conferenceList = conferenceDao.findAll();
	    applicList.forEach(applicInf -> {
		for (Conference conference : conferenceList) {
		    if (conference.getId() == applicInf.getConferenceId()) {
			applicInf.setConferenceName(conference.getName());
		    }
		}
	    });
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	try (UserDao userDao = new UserDao()) {
	    List<User> userList = userDao.findAll();
	    applicList.forEach(applicInf -> {
		for (User user : userList) {
		    if (user.getId() == applicInf.getUserId()) {
			applicInf.setUserName(
				user.getFirstName() + " " + user.getLastName());
			applicInf.setLogin(user.getLogin());
		    }
		}
	    });
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	return applicList;
    }

    public List<Application> getApplicationList()
	    throws ConferenceAppServiceException {
	List<Application> applications = new ArrayList<>();
	try (ApplicationDao dao = new ApplicationDao()) {
	    applications = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	return applications;
    }

    public Optional<Application> findApplicationById(long id)
	    throws ConferenceAppServiceException {
	Optional<Application> application = Optional.empty();
	try (ApplicationDao dao = new ApplicationDao()) {
	    application = dao.findById(id);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any application", e);
	}
	return application;
    }

    public List<ApplicationInfo> findApplicationInfoByUser(long userId)
	    throws ConferenceAppServiceException {
	List<ApplicationInfo> applicList = getApplicationInfoList();
	applicList = applicList.stream()
		.filter(applic -> applic.getUserId() == userId)
		.collect(Collectors.toList());
	return applicList;
    }

    public List<Application> findUserApplication(long userId)
	    throws ConferenceAppServiceException {
	List<Application> applications = new ArrayList<>();
	try (ApplicationDao dao = new ApplicationDao()) {
	    applications = dao.findByUserId(userId);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find user's application", e);
	}
	return applications;
    }

    public boolean removeApplication(long id)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try (ApplicationDao dao = new ApplicationDao()) {
	    flag = dao.delete(id);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to delete application", e);
	}
	return flag;
    }

    public List<Status> getStatusList() throws ConferenceAppServiceException {
	List<Status> statuses = new ArrayList<>();
	try (StatusDao dao = new StatusDao()) {
	    statuses = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to find any status",
		    e);
	}
	return statuses;
    }

    public boolean changeApplicStatus(long applicId, long statusId)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try (ApplicationDao dao = new ApplicationDao()) {
	    Application application = dao.findById(applicId).get();
	    application.getStatus().setId(statusId);
	    flag = dao.update(application);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to change status of application with id=" + applicId,
		    e);
	}
	return flag;
    }

    public boolean changeApplicDate(long applicId, String reportDate)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try (ApplicationDao dao = new ApplicationDao()) {
	    Application application = dao.findById(applicId).get();
	    application
		    .setReportDate(DateTimeConverter.convertToLong(reportDate));
	    flag = dao.update(application);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to change report date of application with id="
			    + applicId,
		    e);
	}
	return flag;
    }
}

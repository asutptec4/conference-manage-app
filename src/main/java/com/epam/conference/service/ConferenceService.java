package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.ConferenceDao;
import com.epam.conference.dao.impl.SectionDao;
import com.epam.conference.entity.Conference;
import com.epam.conference.entity.Section;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.DateTimeConverter;

public class ConferenceService {

    public boolean addConference(String name, String startDate, String endDate,
	    String location, String descripiton)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	Conference conference = new Conference();
	conference.setName(name);
	conference.setStartDate(DateTimeConverter.convertToLong(startDate));
	conference.setEndDate(DateTimeConverter.convertToLong(endDate));
	conference.setLocation(location);
	conference.setDescription(descripiton);
	try (ConferenceDao dao = new ConferenceDao()) {
	    flag = dao.add(conference);
	} catch (Exception e) {
	    throw new ConferenceAppServiceException(
		    "Fail to add new conference", e);
	}
	return flag;
    }

    public boolean addSection(String name, String description,
	    long conferenceId) throws ConferenceAppServiceException {
	boolean flag = false;
	Section section = new Section();
	section.setName(name);
	section.setDescription(description);
	section.setConferenceId(conferenceId);
	try (SectionDao dao = new SectionDao()) {
	    flag = dao.add(section);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to add new section",
		    e);
	}
	return flag;
    }

    public Optional<Conference> findConferenceById(long id)
	    throws ConferenceAppServiceException {
	Optional<Conference> conference = Optional.empty();
	try (ConferenceDao dao = new ConferenceDao()) {
	    conference = dao.findById(id);
	} catch (Exception e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find conference with id=" + id, e);
	}
	return conference;
    }

    public List<Conference> findConferenceByName(String name)
	    throws ConferenceAppServiceException {
	List<Conference> conferences = new ArrayList<>();
	try (ConferenceDao dao = new ConferenceDao()) {
	    conferences = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find conferences in db", e);
	}
	return conferences.stream().filter(conf -> {
	    return conf.getName().toLowerCase().contains(name.toLowerCase());
	}).collect(Collectors.toList());
    }

    public Optional<Section> findSectionById(long id)
	    throws ConferenceAppServiceException {
	Optional<Section> section = Optional.empty();
	try (SectionDao dao = new SectionDao()) {
	    section = dao.findById(id);
	} catch (Exception e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find section with id=" + id, e);
	}
	return section;
    }

    public List<Section> findSectionsOfConference(long conferenceId)
	    throws ConferenceAppServiceException {
	List<Section> sections = new ArrayList<>();
	try (SectionDao dao = new SectionDao()) {
	    sections = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find sections of conference with id="
			    + conferenceId,
		    e);
	}
	return sections.stream().filter(s -> {
	    return s.getConferenceId() == conferenceId;
	}).collect(Collectors.toList());
    }

    public List<Conference> getConferenceList()
	    throws ConferenceAppServiceException {
	try (ConferenceDao dao = new ConferenceDao()) {
	    return dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to get conference list from db", e);
	}
    }

    public List<Section> getSectionList() throws ConferenceAppServiceException {
	List<Section> sections = new ArrayList<>();
	try (SectionDao dao = new SectionDao()) {
	    sections = dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to find any sections of conference", e);
	}
	return sections;
    }

    public boolean updateConference(long id, String name, String startDate,
	    String endDate, String location, String descripiton)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	Conference conference = new Conference();
	conference.setId(id);
	conference.setName(name);
	conference.setStartDate(DateTimeConverter.convertToLong(startDate));
	conference.setEndDate(DateTimeConverter.convertToLong(endDate));
	conference.setLocation(location);
	conference.setDescription(descripiton);
	try (ConferenceDao dao = new ConferenceDao()) {
	    flag = dao.update(conference);
	} catch (Exception e) {
	    throw new ConferenceAppServiceException(
		    "Fail to update conference", e);
	}
	return flag;
    }

    public boolean updateSection(long id, String name, String description)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try(SectionDao dao = new SectionDao()){
	    Section section = dao.findById(id).get();
	    section.setName(name);
	    section.setDescription(description);
	    flag = dao.update(section);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to update section", e);
	}
	return flag;
    }
}

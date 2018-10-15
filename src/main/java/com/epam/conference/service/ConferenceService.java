package com.epam.conference.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.epam.conference.dao.impl.ApplicationDao;
import com.epam.conference.dao.impl.ConferenceDao;
import com.epam.conference.dao.impl.SectionDao;
import com.epam.conference.entity.Conference;
import com.epam.conference.entity.Section;
import com.epam.conference.exception.ConferenceAppDaoException;
import com.epam.conference.exception.ConferenceAppServiceException;
import com.epam.conference.util.DateTimeConverter;

/**
 * Service class is used for working with {@link Conference} and {@link Section}
 * objects via DAO layer classes. {@code ConferenceService} is singleton.
 * 
 * @author Alexander Shishonok
 *
 */
public class ConferenceService {

    private static final ConferenceService INSTANCE = new ConferenceService();

    private ConferenceService() {
    }

    public static ConferenceService getInstance() {
	return INSTANCE;
    }

    /**
     * Create new conference and add to database.
     * 
     * @param name
     *            name of conference.
     * @param startDate
     *            conference start date.
     * @param endDate
     *            conference end date.
     * @param location
     *            conference location.
     * @param descripiton
     *            conference description.
     * @return true if conference created.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
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

    /**
     * Create new section of conference.
     * 
     * @param name
     *            name of conference's section.
     * @param description
     *            section description.
     * @param conferenceId
     *            conference identifier that contains this section.
     * @return true if section of conference added.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
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

    /**
     * Method find conference by id.
     * 
     * @param id
     *            conference identifier.
     * @return an {@link Conference} object wrapped in {@link Optional}.
     * @throws ConferenceAppServiceException
     *             if fail to find conference in database.
     */
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

    /**
     * Method find conference by name.
     * 
     * @param name
     *            conference name or part of name.
     * @return an {@link List} of conferences contain string {@code name} in
     *         conference name.
     * @throws ConferenceAppServiceException
     *             if fail to find conference in database.
     */
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

    /**
     * Method find section by id.
     * 
     * @param id
     *            section identifier.
     * @return an {@link Section} object wrapped in {@link Optional}.
     * @throws ConferenceAppServiceException
     *             if fail to find section in database.
     */
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

    /**
     * Method find all section of conference.
     * 
     * @param conferenceId
     *            conference identifier.
     * @return an {@link List} of sections.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
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

    /**
     * Method find all conferences created in app.
     * 
     * @return an {@link List} of conferences.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
    public List<Conference> getConferenceList()
	    throws ConferenceAppServiceException {
	try (ConferenceDao dao = new ConferenceDao()) {
	    return dao.findAll();
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException(
		    "Fail to get conference list from db", e);
	}
    }

    /**
     * Method find all sections created in app.
     * 
     * @return an {@link List} of sections.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
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

    /**
     * Method update conference info.
     * 
     * @param id
     *            conference identifier.
     * @param name
     *            conference name.
     * @param startDate
     *            conference start date.
     * @param endDate
     *            conference end date.
     * @param location
     *            conference location.
     * @param descripiton
     *            conference description.
     * @return true if conference updated.
     * @throws ConferenceAppServiceException
     *             if error is occurred working with db.
     */
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
	    throw new ConferenceAppServiceException("Fail to update conference",
		    e);
	}
	return flag;
    }

    /**
     * Update section information.
     * 
     * @param id
     *            section identifier.
     * @param name
     *            name of conference's section.
     * @param description
     *            section description.
     * @return true if section info changed.
     * @throws ConferenceAppServiceException
     *             if fail update section in db.
     */
    public boolean updateSection(long id, String name, String description)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try (SectionDao dao = new SectionDao()) {
	    Section section = dao.findById(id).get();
	    section.setName(name);
	    section.setDescription(description);
	    flag = dao.update(section);
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to update section",
		    e);
	}
	return flag;
    }

    /**
     * Delete conference from db. Delete only if conference hasn't any section.
     * 
     * @param id
     *            conference identifier.
     * @return true if successful delete.
     * @throws ConferenceAppServiceException
     *             if fail to delete from db.
     */
    public boolean deleteConference(long id)
	    throws ConferenceAppServiceException {
	boolean flag = false;
	try (ConferenceDao dao = new ConferenceDao()) {
	    if (findSectionsOfConference(id).size() == 0) {
		flag = dao.delete(id);
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to delete conference",
		    e);
	}
	return flag;
    }

    /**
     * Delete section only if section hasn't any application from user.
     * 
     * @param id
     *            section identifier.
     * @return true if delete.
     * @throws ConferenceAppServiceException
     */
    public boolean deleteSection(long id) throws ConferenceAppServiceException {
	boolean flag = false;
	try (SectionDao sectDao = new SectionDao();
		ApplicationDao appDao = new ApplicationDao()) {
	    if (appDao.findBySectionId(id).size() == 0) {
		flag = sectDao.delete(id);
	    }
	} catch (ConferenceAppDaoException e) {
	    throw new ConferenceAppServiceException("Fail to delete section",
		    e);
	}
	return flag;
    }
}

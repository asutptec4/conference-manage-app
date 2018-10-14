package com.epam.conference.dao;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.db.ConnectionPool;
import com.epam.conference.db.ProxyConnection;
import com.epam.conference.entity.Entity;
import com.epam.conference.exception.ConferenceAppDaoException;

/**
 * Base interface for DAO classes.
 * 
 * @author Alexander Shishonok
 */
public abstract class AbstractDao<T extends Entity> implements AutoCloseable {

    private static final Logger LOGGER = LogManager
	    .getLogger(AbstractDao.class);

    /**
     * An {@code ProxyConnection} object that is a database connection.
     */
    protected ProxyConnection connection;

    /**
     * Default constructor, which initialize the field {@code connection} with a
     * connection from the pool.
     */
    public AbstractDao() {
	this.connection = (ProxyConnection) ConnectionPool.getInstance()
		.getConnection();
    }

    /**
     * Method add entity to database.
     * 
     * @param entity
     *            an entity of business model.
     * @return true if add database table entry.
     * @throws ConferenceAppDaoException
     *             if fail to retrieve data from database.
     */
    public abstract boolean add(T entity) throws ConferenceAppDaoException;

    /**
     * Method delete database table entry by id.
     * 
     * @param id
     *            id of database table entry.
     * @return true if success deleted.
     * @throws ConferenceAppDaoException
     *             if error is occurred execute command.
     */
    public abstract boolean delete(long id) throws ConferenceAppDaoException;

    /**
     * Method return entry by id.
     * 
     * @param id
     *            id of database table entry.
     * @return an {@code Optional} object wraps an entity of business model.
     * @throws ConferenceAppDaoException
     *             if fail to retrieve data from database.
     */
    public abstract Optional<T> findById(long id)
	    throws ConferenceAppDaoException;

    /**
     * Method return all entries from database table.
     * 
     * @return the {@code List} of entities.
     * @throws ConferenceAppDaoException
     *             if fail to retrieve data from database.
     */
    public abstract List<T> findAll() throws ConferenceAppDaoException;

    /**
     * Method update entry in database table which matching parameter entity.
     * Search entry for update by entity id.
     * 
     * @param entity
     *            an entity of business model.
     * @return true if entry updated.
     * @throws ConferenceAppDaoException
     *             if error is occurred execute command.
     */
    public abstract boolean update(T entity) throws ConferenceAppDaoException;

    /**
     * Start a transaction when working with a table
     * 
     * @throws ConferenceAppDaoException
     *             if fail to start transaction
     */
    public void startTransaction() throws ConferenceAppDaoException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't start transaction.", e);
	}
    }

    /**
     * Roll back all uncommitted changes.
     */
    public void rollbackTransaction() {
	try {
	    connection.rollback();
	} catch (SQLException e) {
	    LOGGER.error("Fail to end transaction.", e);
	}
    }

    /**
     * Close connection received when the instance of DAO created.
     */
    @Override
    public void close() {
	try {
	    if (connection != null) {
		if (connection.getAutoCommit() == false) {
		    connection.commit();
		}
		connection.close();
	    }
	} catch (SQLException e) {
	    LOGGER.error("Fail to close connection.", e);
	}
    }

}

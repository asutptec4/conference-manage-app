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

    protected ProxyConnection connection;

    public AbstractDao() {
	this.connection = (ProxyConnection) ConnectionPool.getInstance()
		.getConnection();
    }

    public abstract boolean add(T entity) throws ConferenceAppDaoException;

    public abstract boolean delete(long id) throws ConferenceAppDaoException;

    public abstract Optional<T> findById(long id)
	    throws ConferenceAppDaoException;

    public abstract List<T> findAll() throws ConferenceAppDaoException;

    public abstract boolean update(T entity) throws ConferenceAppDaoException;

    public void startTransaction() throws ConferenceAppDaoException {
	try {
	    connection.setAutoCommit(false);
	} catch (SQLException e) {
	    throw new ConferenceAppDaoException("Can't start transaction.", e);
	}
    }

    public void rollbackTransaction() {
	try {
	    connection.rollback();
	} catch (SQLException e) {
	    LOGGER.error("Fail to end transaction.", e);
	}
    }

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

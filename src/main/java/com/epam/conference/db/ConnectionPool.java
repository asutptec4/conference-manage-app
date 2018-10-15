package com.epam.conference.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.conference.exception.ConnectionPoolException;

/**
 * Class {@code ConnectionPool} used for creation and manage database
 * connection. Pool realize singleton pattern.
 * 
 * @author Alexander Shishonok
 */
public class ConnectionPool {

    private static final Logger LOGGER = LogManager
	    .getLogger(ConnectionPool.class);

    /**
     * Instance of {@code ConnectionPool}.
     */
    private static ConnectionPool instance;

    /**
     * Numbers of connection in pool.
     */
    private int size;

    /**
     * Collection for database connection.
     */
    private BlockingQueue<ProxyConnection> freeConnections;

    /**
     * Flag indicates that the pool is created.
     */
    private static AtomicBoolean isExist = new AtomicBoolean(false);
    private static Lock lock = new ReentrantLock();

    private ConnectionPool() {
	initConnectionPool();
    }

    /**
     * Method return instance of {@code ConnectionPool} class.
     * 
     * @return instance of {@code ConnectionPool} class.
     */
    public static ConnectionPool getInstance() {
	if (!isExist.get()) {
	    try {
		lock.lock();
		if (instance == null) {
		    instance = new ConnectionPool();
		    isExist.set(true);
		    LOGGER.info("Instance of ConnectionPool created.");
		}
	    } finally {
		lock.unlock();
	    }
	}
	return instance;
    }

    /**
     * Method get {@code Connection} instance from pool.
     * 
     * @return instance of {@code Connection}.
     * @throws ConnectionPoolException
     *             throw if fail to get connection from pool.
     */
    public Connection getConnection() {
	ProxyConnection connection = null;
	try {
	    connection = freeConnections.take();
	} catch (InterruptedException e) {
	    LOGGER.error(Thread.currentThread() + " didn't get connection", e);
	    Thread.currentThread().interrupt();
	    System.out.println("Thread down");
	}
	return connection;
    }

    /**
     * Method return {@code Connection} instance back to pool.
     * 
     * @param connection
     *            returned connection.
     * @throws ConnectionPoolException
     *             throw if connection not return to pool.
     */
    public void returnConnection(Connection connection)
	    throws ConnectionPoolException {
	if (!freeConnections.offer((ProxyConnection) connection)) {
	    LOGGER.error(Thread.currentThread() + " didn't return connection");
	    throw new ConnectionPoolException("Fail to return connection.");
	}
    }

    /**
     * Method close all connections in pool. After closing of connections,
     * deregister drivers.
     */
    public void clearConnectionPool() {
	try {
	    closeConnections(freeConnections);
	} catch (SQLException e) {
	    LOGGER.error("Cannot clear Connection Pool.", e);
	}
	try {
	    Enumeration<Driver> drivers = DriverManager.getDrivers();
	    while (drivers.hasMoreElements()) {
		Driver driver = (Driver) drivers.nextElement();
		DriverManager.deregisterDriver(driver);
	    }
	} catch (SQLException e) {
	    LOGGER.error("Fail to deregister drivers.", e);
	}
    }

    /*
     * Method initialize ConnectionPool instance, register driver for JDBC,
     * create {@code Connection} instances.
     */
    private void initConnectionPool() {
	DbConfig config = new DbConfig();
	size = config.getPoolSize();
	freeConnections = new LinkedBlockingQueue<ProxyConnection>(size);
	try {
	    DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
	    for (int i = 0; i < size; i++) {
		freeConnections.add(new ProxyConnection(
			DriverManager.getConnection(config.getUrl(),
				config.getUser(), config.getPassword())));
	    }
	} catch (SQLException e) {
	    LOGGER.error("Cannot initialize Connection Pool.", e);
	    throw new RuntimeException("Fail to init pool.", e);
	}
    }

    /*
     * Close connection in connection pool.
     * 
     * @param connections
     *            connection queue.
     * @throws SQLException
     *             if a database access error occurs.
     */
    private void closeConnections(BlockingQueue<ProxyConnection> connections)
	    throws SQLException {
	ProxyConnection connection = null;
	while ((connection = connections.poll()) != null) {
	    if (!connection.getAutoCommit()) {
		connection.commit();
	    }
	    connection.reallyClose();
	}
    }
}

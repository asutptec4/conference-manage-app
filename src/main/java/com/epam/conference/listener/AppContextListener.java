package com.epam.conference.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.epam.conference.db.ConnectionPool;

/**
 * {@code AppContextListener} used for initializing {@code ConnectionPool}
 * object when the application starts.
 * 
 * @author Alexander Shishonok
 *
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce) {
	ConnectionPool.getInstance().clearConnectionPool();
    }

    public void contextInitialized(ServletContextEvent sce) {
	ConnectionPool.getInstance();
    }
}

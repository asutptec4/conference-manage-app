package test.com.epam.conference.db;

import java.sql.Connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.db.ConnectionPool;
import com.epam.conference.exception.ConnectionPoolException;

public class ConnectionPoolTest {

    @Test
    public void clearConnectionPool() {
	ConnectionPool.getInstance();
    }

    @Test
    public void getConnection() {
	Connection connection = ConnectionPool.getInstance().getConnection();
	Assert.assertTrue(connection != null);
    }

    @Test
    public void getInstance() {
	Assert.assertTrue(ConnectionPool.getInstance() != null);
    }

    @Test
    public void returnConnection() {
	Connection connection = ConnectionPool.getInstance().getConnection();
	try {
	    ConnectionPool.getInstance().returnConnection(connection);
	} catch (ConnectionPoolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}

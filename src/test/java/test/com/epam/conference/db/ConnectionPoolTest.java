package test.com.epam.conference.db;

import java.sql.Connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.db.ConnectionPool;

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

    @Test(enabled = false)
    public void returnConnection() {
	throw new RuntimeException("Test not implemented");
    }
}

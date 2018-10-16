package test.com.epam.conference.db;

import java.sql.Connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.db.ConnectionPool;
import com.epam.conference.db.ProxyConnection;

public class ConnectionPoolTest {

    @Test
    public void getConnection() {
	Connection connection = ConnectionPool.getInstance().getConnection();
	Assert.assertTrue(connection instanceof ProxyConnection);
    }

    @Test
    public void getInstance() {
	Assert.assertTrue(ConnectionPool.getInstance() != null);
    }
}

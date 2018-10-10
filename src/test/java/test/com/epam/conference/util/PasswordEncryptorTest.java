package test.com.epam.conference.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.util.PasswordEncryptor;

public class PasswordEncryptorTest {

    @Test
    public void encrypt() {
	Assert.assertEquals(PasswordEncryptor.encrypt("Hello"),
		"8b1a9953c4611296a827abf8c47804d7");
    }
}

package test.com.epam.conference.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.util.PasswordEncryptor;

public class PasswordEncryptorTest {

    @Test
    public void encryptTest() {
	Assert.assertEquals(PasswordEncryptor.encrypt("Hello"),
		"91db2d4279a42766759cfa87e9d633b4");
    }
}

package test.com.epam.conference.util.validator;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.conference.util.validator.InputValidator;

public class InputValidatorTest {

    @Test(enabled = false)
    public void validateConference() {
	throw new RuntimeException("Test not implemented");
    }

    @Test(enabled = false)
    public void validateReport() {
	throw new RuntimeException("Test not implemented");
    }

    @Test(enabled = false)
    public void validateSection() {
	throw new RuntimeException("Test not implemented");
    }

    @Test(dataProvider = "password")
    public void validatePassword(String password, boolean expected) {
	Assert.assertEquals(InputValidator.validatePassword(password),
		expected);
    }

    @Test(dataProvider = "user")
    public void validateUser(String login, String firstName, String lastName,
	    String email, String phone, boolean expected) {
	Assert.assertEquals(InputValidator.validateUser(login, firstName,
		lastName, email, phone), expected);
    }

    @Test(dataProvider = "script")
    public void removeScript(String str, String result) {
	Assert.assertEquals(InputValidator.removeScript(str), result);
    }

    @DataProvider(name = "script")
    public Object[][] dataForScriptTest() {
	return new Object[][] {
		{ "<script> alert(username) </script>", " alert(username) " },
		{ "simple script string", "simple script string" },
		{ "<script> open script", " open script" },
		{ "close <\\script>and</script>", "close <\\script>and" } };
    }

    @DataProvider(name = "password")
    public Object[][] dataForPasswordTest() {
	return new Object[][] { { "ghfdkjlag", true }, { "", false },
		{ " ", true }, { null, false } };
    }

    @DataProvider(name = "user")
    public Object[][] dataForUserTest() {
	return new Object[][] {
		{ "miron", "Dmitry", "Mironovich", "dmitrymironov@gmail.com",
			"+375297353879", true },
		{ "miron", "Dmitry", "Mironovich", "dmitrymironov@gmail.com",
			"375297353879", false },
		{ "user1", "Petr", "Petrov", "qqqqqqq@qqq.qq", "+43896296345",
			true },
		{ "Admin", "Admin", "Admin", "admin@admin.com", "+375555555555",
			true } };
    }
}

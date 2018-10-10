package test.com.epam.conference.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.epam.conference.util.DateTimeConverter;

public class DateTimeConverterTest {

  @Test
  public void convertToLongString() {
    Assert.assertEquals(DateTimeConverter.convertToLong("2018-10-10T10:10"), 1539155400000L);
  }

}

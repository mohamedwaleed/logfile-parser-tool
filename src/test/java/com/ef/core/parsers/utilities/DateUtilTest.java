package com.ef.core.parsers.utilities;

import com.ef.utilities.DateUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;

/**
 * Created by mohamed on 16/10/17.
 */
@RunWith(JUnit4.class)
public class DateUtilTest {

    @Test
    public void addDailyTest() throws ParseException {
        String result = DateUtil.addTo("2017-01-01.00:00:00", 24, "yyyy-MM-dd.HH:mm:ss");
        Assert.assertEquals(result, "2017-01-02.00:00:00");
    }

    @Test
    public void addHourlyErrorTest() throws Exception {
        String result = DateUtil.addTo("2017-01-01.00:00:00", 1, "yyyy-MM-dd.HH:mm:ss");
        Assert.assertEquals(result, "2017-01-01.01:00:00");
    }

    @Test(expected = ParseException.class)
    public void addToErrorTest() throws ParseException {
        String result = DateUtil.addTo("2017-01-01.00:00:00", 24, "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals(result, "2017-01-02.00:00:00");
    }

}

package com.ef.core.parsers;

import com.ef.entities.CmdArgs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;

/**
 * Created by mohamed on 14/10/17.
 */
@RunWith(JUnit4.class)
public class CommandLineParserTest {
    @Test
    public void testParseHappy() throws ParseException {
        Parser parser = new CommandLineParser();
        CmdArgs cmdArgs = (CmdArgs) parser.parse("--startDate=2017-01-01.13:00:00","--duration=hourly","--threshold=100");
        Assert.assertEquals(cmdArgs.getStartDate(), "2017-01-01.13:00:00");
        Assert.assertEquals(cmdArgs.getDuration(), CmdArgs.Duration.HOURLY);
        Assert.assertEquals(cmdArgs.getThreshold(), new Integer(100));
    }
}

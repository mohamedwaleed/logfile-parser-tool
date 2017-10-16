package com.ef.core.parsers;

import com.ef.core.parsers.LogLineParser;
import com.ef.core.parsers.Parser;
import com.ef.entities.LogRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.ParseException;

/**
 * Created by mohamed on 13/10/17.
 */
@RunWith(JUnit4.class)
public class LogLineParserTest {

    @Test
    public void testParseHappy() throws Exception {
        Parser parser = new LogLineParser("\\|");
        LogRecord logRecord = (LogRecord) parser.parse("2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n");
        Assert.assertEquals(logRecord.getIp(), "192.168.234.82");
//        Assert.assertEquals(logRecord.getRequest(), "\"GET / HTTP/1.1\"");
//        Assert.assertEquals(logRecord.getStatus(), "200");
//        Assert.assertEquals(logRecord.getUserAgent(), "\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n");
        Assert.assertEquals(logRecord.getDate().toString(), "Sun Jan 01 00:00:11 EET 2017");
    }

}

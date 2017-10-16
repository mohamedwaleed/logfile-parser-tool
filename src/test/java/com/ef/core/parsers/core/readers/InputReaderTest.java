package com.ef.core.parsers.core.readers;

import com.ef.core.readers.InputReader;
import com.ef.core.readers.Reader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by mohamed_waleed on 16/10/17.
 */
@RunWith(JUnit4.class)
public class InputReaderTest {

    @Test
    public void readTest() throws Exception {
        String lines = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n" +
                "2017-01-01 00:00:21.164|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n";
        InputStream inputStream = new ByteArrayInputStream(lines.getBytes());
        Reader reader = new InputReader(inputStream);
        String result = reader.read(1);
        Assert.assertEquals(result, "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n");
    }

}

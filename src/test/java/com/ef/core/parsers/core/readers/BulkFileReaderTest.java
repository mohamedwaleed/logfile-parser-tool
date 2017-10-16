package com.ef.core.parsers.core.readers;

import com.ef.core.readers.BulkFileReader;
import com.ef.core.readers.InputReader;
import com.ef.entities.LogRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mohamed on 16/10/17.
 */
@RunWith(JUnit4.class)
public class BulkFileReaderTest {

    @Test
    public void iteratorTest() throws FileNotFoundException {
        String lines = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n" +
                "2017-01-01 00:00:21.164|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"\n";
        InputStream inputStream = new ByteArrayInputStream(lines.getBytes());
        BulkFileReader<LogRecord> bulkFileReader = new BulkFileReader<>(new InputReader(inputStream), 1);
        Iterator<List<LogRecord>> iterator = bulkFileReader.iterator();
        boolean hasNext = iterator.hasNext();
        Assert.assertEquals(hasNext, true);
        List<LogRecord> logRecordList = iterator.next();
        Assert.assertEquals(logRecordList.size(), 1);
        Assert.assertEquals(logRecordList.get(0).getIp(), "192.168.234.82");
    }

}

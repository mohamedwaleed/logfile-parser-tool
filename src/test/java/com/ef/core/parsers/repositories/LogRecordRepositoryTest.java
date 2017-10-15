package com.ef.core.parsers.repositories;

import com.ef.config.HibernateConfig;
import com.ef.core.parsers.config.MainTestConfig;
import com.ef.entities.LogRecord;
import com.ef.repositories.LogRecordRepository;
import com.ef.repositories.LogRecordRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mohamed on 16/10/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainTestConfig.class)
@ActiveProfiles("test")
public class LogRecordRepositoryTest {

    private Session session;

    @Before
    public void init() {
        session = HibernateConfig.sessionFactory.openSession();
        session.createNativeQuery("delete from log_record");
    }

    @Test
    public void testAddLogArchives() throws ParseException {
        List<LogRecord> logRecordList = new ArrayList<>();
        LogRecord logRecord = new LogRecord();
        logRecord.setIp("192.168.99.217");
        logRecord.setRequest("\"GET / HTTP/1.1\"");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = df.parse("2017-01-01 00:00:11.763");

        logRecord.setDate(date);
        logRecordList.add(logRecord);
        LogRecordRepository logRecordRepository = new LogRecordRepositoryImpl();
        logRecordRepository.addLogRecords(logRecordList);

        Query query = session.createNativeQuery("select * from log_record where ip='192.168.99.217'", LogRecord.class);

        Assert.assertEquals(query.list().size(), 1);

        LogRecord resultLogRecord = (LogRecord) query.list().get(0);
        Assert.assertEquals(resultLogRecord.getIp(), "192.168.99.217");
        Assert.assertEquals(resultLogRecord.getDate().toString(), "2017-01-01 00:00:11.763");
        Assert.assertEquals(resultLogRecord.getRequest(), "\"GET / HTTP/1.1\"");

    }


    @Test
    public void testFindIpsBetween() throws ParseException {
        session.createNativeQuery("insert into log_record(id, ip, date, request) values(1, '192.168.99.217', { ts '2017-01-01 15:00:01.0'}, '')");
        session.createNativeQuery("insert into log_record(id, ip, date, request) values(2, '192.168.99.218', { ts '2017-01-01 15:00:02.0'}, '')");
        session.createNativeQuery("insert into log_record(id, ip, date, request) values(3, '192.168.99.219', { ts '2017-01-01 15:00:03.0'}, '')");
        session.createNativeQuery("insert into log_record(id, ip, date, request) values(4, '192.168.99.217', { ts '2017-01-01 15:00:04.0'}, '')");
        session.createNativeQuery("insert into log_record(id, ip, date, request) values(5, '192.168.99.217', { ts '2017-01-01 15:00:05.0'}, '')");

        LogRecordRepository logRecordRepository = new LogRecordRepositoryImpl();
        List<String> ips = logRecordRepository.findIpsBetween("2017-01-01 15:00:00.0", "2017-01-01 15:59:59.0", 0);

        Assert.assertNotNull(ips);
        Assert.assertEquals(ips.size(), 1);
    }

    @After
    public void finish() {
        session.close();
    }
}

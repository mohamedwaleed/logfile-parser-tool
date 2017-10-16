package com.ef.core.parsers.repositories;

import com.ef.config.HibernateConfig;
import com.ef.core.parsers.config.MainTestConfig;
import com.ef.entities.LogArchive;
import com.ef.entities.LogRecord;
import com.ef.repositories.LogArchiveRepository;
import com.ef.repositories.LogArchiveRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed_waleed on 16/10/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainTestConfig.class)
@ActiveProfiles("test")
public class LogArchiveRepositoryTest {

    private Session session;

    @Before
    public void init() {
        session = HibernateConfig.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("delete from log_record").executeUpdate();
        tx.commit();
    }

    @Test
    public void addLogArchivesTest() {
        List<LogArchive> logArchiveList = new ArrayList<>();
        LogArchive logArchive = new LogArchive();
        logArchive.setIp("192.168.99.217");
        logArchiveList.add(logArchive);

        LogArchiveRepository logArchiveRepository = new LogArchiveRepositoryImpl();
        logArchiveRepository.addLogArchives(logArchiveList);

        Query query = session.createNativeQuery("select * from log_archive where ip='192.168.99.217'", LogArchive.class);

        Assert.assertEquals(query.list().size(), 1);

        LogArchive resultLogArchive = (LogArchive) query.list().get(0);
        Assert.assertEquals(resultLogArchive.getIp(), "192.168.99.217");
    }


    @After
    public void finish() {
        session.close();
    }
}

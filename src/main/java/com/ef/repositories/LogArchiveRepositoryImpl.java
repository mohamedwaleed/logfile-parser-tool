package com.ef.repositories;

import com.ef.config.HibernateConfig;
import com.ef.entities.LogArchive;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by mohamed on 15/10/17.
 */
public class LogArchiveRepositoryImpl implements LogArchiveRepository {
    @Override
    public void addLogArchives(List<LogArchive> logArchives) {
        int bulkSize = 0;
        Session session = HibernateConfig.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (LogArchive logArchive: logArchives ) {
            session.save(logArchive);
            bulkSize++;
            if(bulkSize % 100 == 0) {
                session.flush();
                session.clear();
            }
        }
        tx.commit();
        session.close();
    }
}

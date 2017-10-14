package com.ef.repositories;

import com.ef.config.HibernateConfig;
import com.ef.entities.LogRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

/**
 * Created by mohamed on 14/10/17.
 */
public class LogRecordRepositoryImpl implements LogRecordRepository {

    @Override
    public void addLogRecords(List<LogRecord> logRecords) {
        Session session = HibernateConfig.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (LogRecord logRecord: logRecords ) {
            session.save(logRecord);
        }
        tx.commit();
        session.close();
    }

}

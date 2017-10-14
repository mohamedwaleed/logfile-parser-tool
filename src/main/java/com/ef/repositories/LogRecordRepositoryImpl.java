package com.ef.repositories;

import com.ef.config.HibernateConfig;
import com.ef.entities.LogRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    @Override
    public List<String> findIpsBetween(String startDate, String endDate, Integer threshold) {
        Session session = HibernateConfig.sessionFactory.openSession();
        Query query = session.createNativeQuery("SELECT ip FROM parser.log_record\n" +
                "where (`date` between '"+startDate+"' and '"+endDate+"')\n" +
                "group by ip\n" +
                "having count(ip) >= "+threshold+";");
        return query.list();
    }


}

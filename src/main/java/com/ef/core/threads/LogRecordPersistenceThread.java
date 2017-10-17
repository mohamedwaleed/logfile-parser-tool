package com.ef.core.threads;

import com.ef.entities.LogRecord;
import com.ef.repositories.LogRecordRepository;
import com.ef.repositories.LogRecordRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * Created by mohamed on 14/10/17.
 */

public class LogRecordPersistenceThread extends Thread {

    private volatile Queue<List<LogRecord>> queue = new ArrayDeque<>();

    private volatile boolean finished = false;

    private LogRecordRepository logRecordRepository = new LogRecordRepositoryImpl();

    public final Logger logger = LoggerFactory.getLogger("static_log");

    @Override
    public void run() {
        while(!(queue.isEmpty() && finished)) {

            if(queue.isEmpty()) {
                continue;
            }

            List<LogRecord> logRecords = queue.poll();
            logger.info(">");
            logRecordRepository.addLogRecords(logRecords);
        }
    }

    public void add(List<LogRecord> logRecords) {
        queue.add(logRecords);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}

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

    private Logger logger = LoggerFactory.getLogger("feedback");

    @Override
    public void run() {
        while(true) {
            if(queue.isEmpty() && finished){
                break;
            }
            if(queue.isEmpty()) {
                continue;
            }

            List<LogRecord> logRecords = queue.poll();
            System.out.print(".");
            logRecordRepository.addLogRecords(logRecords);
        }
//        System.out.println("thread off");
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

package com.ef.core.schedulers;

import com.ef.core.threads.LogRecordPersistenceThread;
import com.ef.entities.LogRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mohamed on 14/10/17.
 */
public class RoundrobinPersistenceScheduler extends AbstractPersistentScheduler {

    private Short currentThread = 0;
    private HashMap<Integer, LogRecordPersistenceThread> threadHashMap = new HashMap<>() ;

    public RoundrobinPersistenceScheduler(Integer numberOfThreads) throws InterruptedException {
        super(numberOfThreads);
        init();
    }

    private void init() throws InterruptedException {
        for(int i = 0 ; i < this.numberOfThreads ; i ++ ) {
            LogRecordPersistenceThread logRecordPersistenceThread = new LogRecordPersistenceThread();
            logRecordPersistenceThread.join();
            threadHashMap.put(i, logRecordPersistenceThread);
            logRecordPersistenceThread.start();
        }
    }

    public void add(List<LogRecord> logRecords) {
        int key = currentThread % this.numberOfThreads;
        threadHashMap.get(key).add(logRecords);
        currentThread ++;
    }

    public void setFinishSignal() throws InterruptedException {
        for(int i = 0 ; i < this.numberOfThreads ; i ++ ) {
            threadHashMap.get(i).setFinished(true);
            threadHashMap.get(i).join();
        }
    }

    public Map<Integer, LogRecordPersistenceThread> getThreadHashMap(){
        return this.threadHashMap;
    }
}

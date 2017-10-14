package com.ef.repositories;

import com.ef.entities.LogRecord;

import java.util.List;

/**
 * Created by mohamed on 14/10/17.
 */
public interface LogRecordRepository {
    void addLogRecords(List<LogRecord> logRecords);
}

package com.ef.repositories;

import com.ef.entities.LogArchive;

import java.util.List;

/**
 * Created by mohamed on 15/10/17.
 */
public interface LogArchiveRepository {
    void addLogArchives(List<LogArchive> logArchives);
}

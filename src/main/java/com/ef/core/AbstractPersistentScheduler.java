package com.ef.core;

/**
 * Created by mohamed on 14/10/17.
 */
public abstract class AbstractPersistentScheduler {

    protected Integer numberOfThreads;

    public AbstractPersistentScheduler(Integer numberOfThreads) {
        if(numberOfThreads <= 0) {
            throw new IllegalArgumentException("Number of threads must be greater than 0");
        }
        this.numberOfThreads = numberOfThreads;
    }


}

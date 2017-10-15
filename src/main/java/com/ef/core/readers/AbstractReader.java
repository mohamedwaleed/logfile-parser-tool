package com.ef.core.readers;

/**
 * Created by mohamed on 13/10/17.
 */
public class AbstractReader {

    protected Reader reader;

    AbstractReader(Reader reader) {
        this.reader = reader;
    }

}

package com.ef.core.readers;


import java.io.IOException;

/**
 * Created by mohamed on 13/10/17.
 */
public interface Reader {
    String read(Integer numberOfLines) throws IOException;
}

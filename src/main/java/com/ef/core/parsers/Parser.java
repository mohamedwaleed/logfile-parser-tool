package com.ef.core.parsers;

/**
 * Created by mohamed on 13/10/17.
 */
public interface Parser {
    Object parse(String ...input) throws Exception;
}

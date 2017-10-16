package com.ef.core.parsers;

import java.text.ParseException;

/**
 * Created by mohamed on 13/10/17.
 */
public interface Parser {
    Object parse(String ...input) throws ParseException;
}

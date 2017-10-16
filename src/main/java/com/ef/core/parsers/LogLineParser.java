package com.ef.core.parsers;

import com.ef.entities.LogRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mohamed on 13/10/17.
 */
public class LogLineParser implements Parser {

    private String regex;

    public LogLineParser(String regex) {
        this.regex = regex;
    }

    @Override
    public Object parse(String ...input) throws ParseException {
        String [] tokens = input[0].split(regex);
        String dateString = tokens[0];
        String ip = tokens[1];
        String request = tokens[2];

        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateString); // This throws a ParseException

        LogRecord logRecord = new LogRecord();
        logRecord.setDate(d);
        logRecord.setIp(ip);
        logRecord.setRequest(request);

        return logRecord;
    }
}

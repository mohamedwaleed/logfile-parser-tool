package com.ef.core.parsers;

import com.ef.entities.CmdArgs;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 14/10/17.
 */
public class CommandLineParser implements Parser {

    private String []requiredKeys = {"startDate", "duration", "threshold", "accesslog"};
    @Override
    public Object parse(String ...input) throws Exception {
        if(input.length != requiredKeys.length){
            throw new ParseException("Expected "+requiredKeys.length+" arguments only", 0);
        }
        Map<String, String> args = new HashMap<>();
        for(String arg: input) {
            String []keyValueArray = arg.split("=");
            String key = keyValueArray[0].trim().substring(2);
            String value = keyValueArray[1].trim();
            if(Arrays.asList(requiredKeys).indexOf(key) == -1) {
                throw new IllegalArgumentException("Unknown key " + key);
            }
            args.put(key, value);
        }

        CmdArgs cmdArgs = new CmdArgs();
        for(String key:requiredKeys) {
            if(!args.containsKey(key)) {
                throw new IllegalArgumentException("Key " + key + " is not exist");
            }
            switch (key) {
                case "startDate":
                    String startDate = args.get(key);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
                    df.parse(startDate);
                    cmdArgs.setStartDate(startDate);
                    break;
                case "duration":
                    String value = args.get(key);
                    CmdArgs.Duration duration;
                    if(value.equals("daily")) {
                        duration = CmdArgs.Duration.DAILY;
                    }else if(value.equals("hourly")) {
                        duration = CmdArgs.Duration.HOURLY;
                    }else {
                        throw new IllegalArgumentException("Key " + key + " accept hourly and daily only as values");
                    }
                    cmdArgs.setDuration(duration);
                    break;
                case "threshold":
                    cmdArgs.setThreshold(Integer.valueOf(args.get(key)));
                    break;
                case "accesslog":
                    String filePath = args.get(key);
                    File file = new File(filePath);
                    if(!file.exists()) {
                        throw new FileNotFoundException("File " + filePath + " is not exist");
                    }
                    cmdArgs.setAccessLogPath(args.get(key));
                    break;
            }
        }
        return cmdArgs;
    }
}

package com.ef.core.parsers;

import com.ef.entities.CmdArgs;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 14/10/17.
 */
public class CommandLineParser implements Parser {

    private String []requiredKeys = {"startDate", "duration", "threshold"};
    @Override
    public Object parse(String ...input) throws ParseException {
        if(input.length != 3){
            throw new ParseException("Expected 3 arguments only", 0);
        }
        Map<String, String> args = new HashMap<>();
        for(String arg: input) {
            String []keyValueArray = arg.split("=");
            String key = keyValueArray[0].trim().substring(2);
            String value = keyValueArray[1].trim();
            if(Arrays.binarySearch(requiredKeys,key) == -1) {
                throw new IllegalArgumentException("Unknown key" + key);
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
                    cmdArgs.setStartDate(args.get(key));
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
            }
        }
        return cmdArgs;
    }
}

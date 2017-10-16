package com.ef.core.parsers;

import com.ef.entities.CmdArgs;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed on 14/10/17.
 */
public class CommandLineParser implements Parser {

    private String []requiredKeys = {"startDate", "duration", "threshold", "accesslog"};
    @Override
    public Object parse(String ...input) throws ParseException {
        if(input.length != requiredKeys.length){
            throw new ParseException("Expected "+requiredKeys.length+" arguments only", 0);
        }
        Map<String, String> argsMap = buildKeyMapFromArgs(input);

        CmdArgs cmdArgs = new CmdArgs();
        for(String key:requiredKeys) {
            if(!argsMap.containsKey(key)) {
                throw new IllegalArgumentException("Key " + key + " is not exist");
            }
            switch (key) {
                case "startDate":
                    String startDate = argsMap.get(key);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
                    df.parse(startDate);
                    cmdArgs.setStartDate(startDate);
                    break;
                case "duration":
                    String value = argsMap.get(key);
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
                    cmdArgs.setThreshold(Integer.valueOf(argsMap.get(key)));
                    break;
                case "accesslog":
                    String filePath = argsMap.get(key);
                    File file = new File(filePath);
                    if(!file.exists()) {
                        throw new ParseException("File " + filePath + " is not exist", 1);
                    }
                    cmdArgs.setAccessLogPath(argsMap.get(key));
                    break;
                default:
                    break;
            }
        }
        return cmdArgs;
    }

    private Map<String, String> buildKeyMapFromArgs(String[] input) {
        Map<String, String> argsMap = new HashMap<>();
        for(String arg: input) {
            String []keyValueArray = arg.split("=");
            if(keyValueArray.length != 2) {
                throw new IllegalArgumentException("Arguments  must be in a form --argument=value");
            }
            String key = keyValueArray[0].trim().substring(2);
            String value = keyValueArray[1].trim();
            if(Arrays.asList(requiredKeys).indexOf(key) == -1) {
                throw new IllegalArgumentException("Unknown key " + key);
            }
            argsMap.put(key, value);
        }
        return argsMap;
    }
}

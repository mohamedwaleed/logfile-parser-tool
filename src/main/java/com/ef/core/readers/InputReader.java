package com.ef.core.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by mohamed on 13/10/17.
 */
public class InputReader implements Reader {

    private final Logger logger = LoggerFactory.getLogger(InputReader.class);

    private BufferedReader bufferedReader = null;

    public InputReader(File file) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(file));
    }
    public InputReader(InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }
    public String read(Integer numberOfLines) throws IOException {

        StringBuilder readBuffer = new StringBuilder();
        try {
            String line = "";
            Integer currentLine = 0 ;
            while(currentLine < numberOfLines &&  bufferedReader.ready() && (line = bufferedReader.readLine()) != null) {
                readBuffer.append(line);
                readBuffer.append('\n');
                currentLine ++;
            }
            if(line != null && line.isEmpty()) {
                bufferedReader.close();
                return null;
            }
            if(line == null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return readBuffer.toString();
    }

}

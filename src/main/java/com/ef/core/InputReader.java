package com.ef.core;

import java.io.*;

/**
 * Created by mohamed on 13/10/17.
 */
public class InputReader implements Reader {

    private BufferedReader bufferedReader = null;

    public InputReader(File file) throws FileNotFoundException {
        bufferedReader = new BufferedReader(new FileReader(file));
    }
    public InputReader(InputStream inputStream) throws FileNotFoundException {
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
            if(line.isEmpty()) {
                return null;
            }
            if(line == null && bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readBuffer.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        if(bufferedReader != null) {
            bufferedReader.close();
        }
    }
}

package com.ef.core.readers;

import com.ef.core.parsers.LogLineParser;
import com.ef.core.parsers.Parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mohamed on 13/10/17.
 */
public class BulkFileReader<T> extends AbstractReader implements Iterable<List<T>>{

    private Integer numberOfLines = 200;

    private static final Parser parser = new LogLineParser("\\|");

    public BulkFileReader(Reader reader) {
        super(reader);
    }

    public BulkFileReader(Reader reader, Integer numberOfLines) {
        super(reader);
        this.numberOfLines = numberOfLines;
    }

    public Iterator<List<T>> iterator() {
        return new FileIterator(this.reader, this.numberOfLines);
    }

    class FileIterator implements Iterator<List<T>> {

        private Reader reader;

        private String lines;

        private Integer numberOfLines;

        public FileIterator(Reader reader, Integer numberOfLines) {
            this.reader = reader;
            this.numberOfLines = numberOfLines;
        }

        @Override
        public boolean hasNext() {
            try {
                lines = this.reader.read(numberOfLines);

                if(lines == null) {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        public List<T> next() {
            List<T> logRecords = null;
            try {
                String[] linesArray = lines.split("\n");
                logRecords = new ArrayList<>();

                for(String line: linesArray) {
                    logRecords.add((T) parser.parse(line));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return logRecords;
        }
    }
}

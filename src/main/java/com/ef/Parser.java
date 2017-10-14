package com.ef;

import com.ef.config.HibernateConfig;
import com.ef.config.MainConfig;
import com.ef.core.BulkFileReader;
import com.ef.core.InputReader;
import com.ef.core.RoundrobinPersistenceScheduler;
import com.ef.core.parsers.CommandLineParser;
import com.ef.entities.CmdArgs;
import com.ef.entities.LogRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * Created by mohamed on 12/10/17.
 */
public class Parser {

    public static final Logger logger = LoggerFactory.getLogger("feedback");

    public static void main(String[] args) {

        args = new String[3];
        args[0] = "--startDate=2017-01-01.13:00:00 ";
        args[1] = "--duration=daily";
        args[2] = "--threshold=250";
        com.ef.core.parsers.Parser parser = new CommandLineParser();
        try {
            CmdArgs cmdArgs = (CmdArgs) parser.parse(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        RoundrobinPersistenceScheduler scheduler = new RoundrobinPersistenceScheduler(4);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        InputStream in = classLoader.getResourceAsStream("access.log");

        try {
            logger.info("Start reading file ...");
            logger.info("Start storing date in mysql ...");
            BulkFileReader<LogRecord> fileReader = new BulkFileReader<>(new InputReader(in));
            for(List<LogRecord> records: fileReader) {
                scheduler.add(records);
            }

            System.out.println();
            logger.info("Read file completed !!!");
            scheduler.setFinishSignal(); // blocks until all threads finished
            logger.info("Done storing date in mysql !!!");

        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        HibernateConfig.sessionFactory.close();
        logger.info("Done !!!");
    }
}

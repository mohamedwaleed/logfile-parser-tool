package com.ef;

import com.ef.config.HibernateConfig;
import com.ef.config.MainConfig;
import com.ef.core.BulkFileReader;
import com.ef.core.InputReader;
import com.ef.core.RoundrobinPersistenceScheduler;
import com.ef.core.parsers.CommandLineParser;
import com.ef.entities.CmdArgs;
import com.ef.entities.LogArchive;
import com.ef.entities.LogRecord;
import com.ef.repositories.LogArchiveRepository;
import com.ef.repositories.LogArchiveRepositoryImpl;
import com.ef.repositories.LogRecordRepository;
import com.ef.repositories.LogRecordRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mohamed on 12/10/17.
 */
public class Parser {

    public static final Logger logger = LoggerFactory.getLogger("feedback");

    public static void main(String[] args) throws ParseException {

        com.ef.core.parsers.Parser parser = new CommandLineParser();

        CmdArgs cmdArgs = (CmdArgs) parser.parse(args);


        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);

        RoundrobinPersistenceScheduler scheduler = new RoundrobinPersistenceScheduler(4);
        File file = new File(cmdArgs.getAccessLogPath());
        try {
            logger.info("Start reading file ...");
            logger.info("Start storing date in mysql ...");
            BulkFileReader<LogRecord> fileReader = new BulkFileReader<>(new InputReader(file));
            for(List<LogRecord> records: fileReader) {
                scheduler.add(records);
            }

            System.out.println();
            logger.info("Read file completed !!!");
            scheduler.setFinishSignal(); // blocks until all threads finished
            System.out.println();
            logger.info("Done storing date in mysql !!!");

            System.out.println();

            Integer addedHours = 0 ;
            if(cmdArgs.getDuration() == CmdArgs.Duration.DAILY) {
                addedHours = 24;
            }else if(cmdArgs.getDuration() == CmdArgs.Duration.HOURLY) {
                addedHours = 1;
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
            Date d = df.parse(cmdArgs.getStartDate());
            Calendar gc = new GregorianCalendar();
            gc.setTime(d);
            gc.add(Calendar.HOUR, addedHours);
            Date date = gc.getTime();
            String endDate = df.format(date);

            LogRecordRepository logRecordRepository = new LogRecordRepositoryImpl();
            List<String> ips = logRecordRepository.findIpsBetween(cmdArgs.getStartDate(),endDate,cmdArgs.getThreshold());
            logger.info("Result:");
            ips.forEach(System.out::println);

            logger.info("Storing result in database...");
            LogArchiveRepository logArchiveRepository = new LogArchiveRepositoryImpl();
            List<LogArchive> logArchives = ips.stream().map(ip -> {
                LogArchive logArchive = new LogArchive();
                logArchive.setIp(ip);
                return logArchive;
            }).collect(Collectors.toList());
            logArchiveRepository.addLogArchives(logArchives);
            logger.info("Done storing results !!!");
        } catch (FileNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }

        HibernateConfig.sessionFactory.close();

        System.out.println();
        logger.info("Done !!!");
    }
}

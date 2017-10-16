package com.ef;

import com.ef.config.HibernateConfig;
import com.ef.config.MainConfig;
import com.ef.core.parsers.CommandLineParser;
import com.ef.core.readers.BulkFileReader;
import com.ef.core.readers.InputReader;
import com.ef.core.schedulers.RoundrobinPersistenceScheduler;
import com.ef.entities.CmdArgs;
import com.ef.entities.LogArchive;
import com.ef.entities.LogRecord;
import com.ef.repositories.LogArchiveRepository;
import com.ef.repositories.LogArchiveRepositoryImpl;
import com.ef.repositories.LogRecordRepository;
import com.ef.repositories.LogRecordRepositoryImpl;
import com.ef.utilities.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by mohamed on 16/10/17.
 */
public class ParserApplication {

    public final Logger logger = LoggerFactory.getLogger("feedback");

    public ParserApplication(String ...commandLineArgs) {
        init(commandLineArgs);
    }

    public void init(String ...commandLineArgs) {


        try {

            CmdArgs cmdArgs = parseCommandLineArgs(commandLineArgs);

            buildIocContainer();

            loadInputFileIntoDatabase(cmdArgs.getAccessLogPath());

            List<String> ips = calculateResult(cmdArgs);

            printResult(ips);

            storeResultInDatabase(ips);

            HibernateConfig.sessionFactory.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }


    }

    private void storeResultInDatabase(List<String> ips) {
        logger.info("Storing result in database...");
        LogArchiveRepository logArchiveRepository = new LogArchiveRepositoryImpl();
        List<LogArchive> logArchives = ips.stream().map(ip -> {
            LogArchive logArchive = new LogArchive();
            logArchive.setIp(ip);
            return logArchive;
        }).collect(Collectors.toList());
        logArchiveRepository.addLogArchives(logArchives);
        logger.info("Done storing results !!!");
    }

    private void printResult(List<String> ips) {
        logger.info("Result:");
        ips.forEach(System.out::println);
    }

    private List<String> calculateResult(CmdArgs cmdArgs) throws ParseException {
        Integer addedHours = getAddedHours(cmdArgs);

        String endDate = DateUtil.addTo(cmdArgs.getStartDate(), addedHours, "yyyy-MM-dd.HH:mm:ss");

        LogRecordRepository logRecordRepository = new LogRecordRepositoryImpl();
        return logRecordRepository.findIpsBetween(cmdArgs.getStartDate(),endDate,cmdArgs.getThreshold());
    }

    private Integer getAddedHours(CmdArgs cmdArgs) {
        Integer addedHours = 0 ;
        if(cmdArgs.getDuration() == CmdArgs.Duration.DAILY) {
            addedHours = 24;
        }else if(cmdArgs.getDuration() == CmdArgs.Duration.HOURLY) {
            addedHours = 1;
        }
        return addedHours;
    }

    private void loadInputFileIntoDatabase(String filePath) throws FileNotFoundException, InterruptedException {

        logger.info("Start reading file ...");
        logger.info("Start storing date in mysql ...");

        RoundrobinPersistenceScheduler scheduler = new RoundrobinPersistenceScheduler(4);
        File file = new File(filePath);

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
    }

    private void buildIocContainer() {
        System.setProperty("spring.profiles.active", "development");
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
    }

    private CmdArgs parseCommandLineArgs(String[] args) throws Exception {
        com.ef.core.parsers.Parser parser = new CommandLineParser();

        return (CmdArgs) parser.parse(args);
    }
}

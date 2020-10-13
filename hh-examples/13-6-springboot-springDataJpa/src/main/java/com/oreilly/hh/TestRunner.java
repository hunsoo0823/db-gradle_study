package com.oreilly.hh;

// yck
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oreilly.hh.service.AlbumTest;
import com.oreilly.hh.service.CreateTest;
import com.oreilly.hh.service.QueryTest;

@SpringBootApplication
public class TestRunner implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(TestRunner.class);

    @Autowired
    CreateTest createService;

    @Autowired
    QueryTest queryService;

    @Autowired
    AlbumTest albumService;

    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class, args).close();
    }

    @Override
    public void run(String... args) throws Exception {

        String testName = "queryTest";
        if (args.length != 0)
            testName = args[0];

        log.info("Running test: {}", testName);

        switch (testName) {
        case "createTest":
            createService.run();
            break;
        case "queryTest":
            queryService.run();
            break;
        case "albumTest":
            albumService.run();
            break;
        default:
            System.out.println("no match");
        }
    }
}


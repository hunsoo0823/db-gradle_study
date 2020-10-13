package com.oreilly.hh;

// yck
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.oreilly.hh.service.Test;

/**
 * A simple harness to run our tests.  Configures Log4J,
 * creates an ApplicationContext, retrieves a bean from Spring
 *
 * @author Timothy O'Brien
 */
public class RunCreateTest {

    private static Logger log = LoggerFactory.getLogger(TestRunner.class);

    public static void main(String[] args) throws Exception {
        //PropertyConfigurator.configure(TestRunner.class.getResource("/log4j.properties"));
        //log = Logger.getLogger(TestRunner.class);

        log.info("Initializing TestRunner...");
        log.info("Loading Spring Configuration...");
        var context = new ClassPathXmlApplicationContext("applicationContext.xml");

        log.info("Running test: createTest");
        Test test = context.getBean("createTest", Test.class);

        test.run();

        context.close();
    }
}

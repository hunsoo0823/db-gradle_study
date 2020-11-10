package exercise;

import java.util.Arrays;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import exercise.config.*;
import exercise.service.*;

@lombok.extern.slf4j.Slf4j
public class App {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(PersistenceConfig.class);

        log.debug("List of beans:");
        log.debug("{}", Arrays.asList(context.getBeanDefinitionNames()));
        //SpringUtil.displayAllBeans(context, false);

        var service = context.getBean(PersistenceService.class);

        service.initializeDatabase();
        service.printAll();

        context.close();
    }
}


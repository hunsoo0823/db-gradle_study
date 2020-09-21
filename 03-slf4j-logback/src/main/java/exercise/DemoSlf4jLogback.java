package exercise;

// http://www.slf4j.org/api/org/slf4j/Logger.html
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoSlf4jLogback {

    private static final Logger log = LoggerFactory.getLogger(DemoSlf4jLogback.class);

    public static void main(String[] args) {
        String name = "Kent";
        Integer age = 37;

        log.error("Error   Message : ({}, {})", name, age);
        log.warn ("Warning Message : ({}, {})", name, age);
        log.info ("Info    Message : ({}, {})", name, age);
        log.debug("Debug   Message : ({}, {})", name, age);
        log.trace("Trace   Message : ({}, {})", name, age);

        System.out.println("Well Done!");
    }

}


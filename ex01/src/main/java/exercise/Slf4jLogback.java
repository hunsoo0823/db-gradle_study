package exercise;

// http://www.slf4j.org/api/org/slf4j/Logger.html
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogback {

    private static final Logger log = LoggerFactory.getLogger(Slf4jLogback.class);

    public static void main(String[] args) {
        String name = "Alex";

        log.error("Error Message.");
        log.warn("Warning Message.");
        log.info("Info Message.");
        log.debug("Debug Message : {}.", name);
        log.trace("Trace Message : {}.", name);

        System.out.println("Well Done!");
    }
}


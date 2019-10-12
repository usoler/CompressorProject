import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class MyLoggerExample {
    Logger LOGGER = LoggerFactory.getLogger(MyLoggerExample.class);

    public void showLogs() {
        LOGGER.info("Info log");
        LOGGER.debug("Debug log");
    }
}

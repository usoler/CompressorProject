package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public class DomainController {
    Logger LOGGER = LoggerFactory.getLogger(DomainController.class);
//    java.util.logging.Logger LOGGER2 = java.util.logging.Logger.getLogger(DomainController.class.toString());

    public void showLogger() {
        LOGGER.info("Info logger");
        LOGGER.debug("Debug logger");
        LOGGER.warn("Warn logger");
        LOGGER.error("Error logger");
//
//        LOGGER2.info("Info logger");
//        LOGGER2.fine("Debug logger");
//        LOGGER2.warning("Warn logger");
//        LOGGER2.severe("Error logger");
    }
}

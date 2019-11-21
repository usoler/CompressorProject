package domain;

import data.DataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainController.class);

    private DataController dataController;

    public void DomainController() {
        LOGGER.debug("Constructing Domain Controller");
        init();
        LOGGER.debug("Domain Controller constructed");
    }

    public void init() {
        LOGGER.debug("Initiating Domain Controller");
        dataController = DataController.getInstance();
        LOGGER.debug("Domain Controller initiated");
    }
}

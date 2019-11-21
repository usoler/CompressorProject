package data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);

    private static DataController singletonDataController;

    private DataController(){
        // Intentionally empty
    }

    public static DataController getInstance() {
        LOGGER.debug("Getting Data Controller instance");
        if (Objects.isNull(singletonDataController)) {
            LOGGER.debug("Data Controller not instanced. Instantiating");
            singletonDataController = new DataController();
        }

        return singletonDataController;
    }
}

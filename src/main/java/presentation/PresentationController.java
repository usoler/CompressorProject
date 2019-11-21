package presentation;

import domain.DomainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PresentationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationController.class);

    private DomainController domainController;
    private MainView mainView;

    public PresentationController() {
        LOGGER.debug("Constructing Presentation Controller");
        domainController = new DomainController();
        mainView = new MainView(this);
        LOGGER.debug("Presentation Controller constructed");
    }

    public void init() {
        LOGGER.debug("Initiating Presentation Controller");
        domainController.init();
        mainView.show();
        LOGGER.debug("Presentation Controller initiated");
    }
}

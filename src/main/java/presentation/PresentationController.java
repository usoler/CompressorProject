package presentation;

import domain.DomainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PresentationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationController.class);

    private DomainController domainController;
    //private MainView mainView;
    private MainViewSwing mainView;

    public PresentationController() {
        LOGGER.debug("Constructing Presentation Controller");
        domainController = new DomainController();
        mainView = new MainViewSwing(this);
        LOGGER.debug("Presentation Controller constructed");
    }

    public void init() {
        LOGGER.debug("Initiating Presentation Controller");
        domainController.init();
        mainView.show();
        LOGGER.debug("Presentation Controller initiated");
    }

    public void addFile(String pathname) {
        LOGGER.debug("Calling Add File from Domain Controller");
        domainController.addFile(pathname);
        LOGGER.debug("Add file from Domain Controller called");
    }
}

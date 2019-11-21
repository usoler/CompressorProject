package presentation;

import domain.DomainController;

public class PresentationController {

    private DomainController domainController;

    private MainView mainView;

    public PresentationController() {
        domainController =new DomainController();
        mainView = new MainView(this);
    }

    public void initializePresentation() {
        // domainController.initializeDomainController();
        mainView.setVisible();
    }
}

package presentation;

import domain.DomainController;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PresentationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PresentationController.class);

    private DomainController domainController;
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

    public void addFile(String pathname) throws CompressorException {
        LOGGER.debug("Calling Add File from Domain Controller with param pathname '{}'", pathname);
        domainController.addFile(pathname);
        LOGGER.debug("Add file from Domain Controller called");
    }

    public String compressFile(String algorithm, String pathname, String filename) throws CompressorException {
        LOGGER.debug("Calling Compress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.compressFile(algorithm, pathname, filename);
    }

    public String uncompressFile(String algorithm, String pathname, String filename) throws CompressorException {
        LOGGER.debug("Calling Uncompress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.uncompressFile(algorithm, pathname, filename);
    }

}

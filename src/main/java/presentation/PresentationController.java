package presentation;

import domain.DomainController;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
        loadHistory();
        LOGGER.debug("Presentation Controller initiated");
    }

    private void loadHistory() {
        LOGGER.debug("Calling load history to Domain Layer");
        CompressorException exception = null;
        ArrayList<String> arrayOfFileData = new ArrayList<>();
        try {
            arrayOfFileData = domainController.loadHistory();
        } catch (CompressorException e) {
            exception = e;
        } finally {
            mainView.loadHistoryTable(arrayOfFileData, exception);
        }
    }

    public void addFile(String pathname, String date) throws CompressorException {
        LOGGER.debug("Calling Add File from Domain Controller with param pathname '{}'", pathname);
        domainController.addFile(pathname, date, true);
        LOGGER.debug("Add file from Domain Controller called");
    }

    public void rewriteHistoryFile(ArrayList<Integer> linesToRemove) throws CompressorException {
        LOGGER.debug("Calling Rewrite History File from Domain Controller");
        domainController.rewriteHistoryFile(linesToRemove);
        LOGGER.debug("Rewrite from Domain Controller called");
    }

    public String compressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Compress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.compressFile(algorithm, pathname, filename, extension);
    }

    public String uncompressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Uncompress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.uncompressFile(algorithm, pathname, filename, extension);
    }

}

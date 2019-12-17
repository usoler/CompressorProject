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
    private ComparisonViewSwing comparisonView;

    public PresentationController() {
        LOGGER.debug("Constructing Presentation Controller");
        domainController = new DomainController();
        mainView = new MainViewSwing(this);
        comparisonView = new ComparisonViewSwing(this);
        LOGGER.debug("Presentation Controller constructed");
    }

    public void init() {
        LOGGER.debug("Initiating Presentation Controller");
        domainController.init();
        mainView.show();
        loadHistory();
        loadStats();
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

    private void loadStats() {
        LOGGER.debug("Calling load stats to Domain Layer");
        CompressorException exception = null;
        ArrayList<String> arrayOfStats = new ArrayList<>();
        try {
            arrayOfStats = domainController.loadStats();
        } catch (CompressorException e) {
            exception = e;
        } finally {
            mainView.loadStatsTable(arrayOfStats, exception);
        }
    }

    public void addFile(String pathname, String date) throws CompressorException {
        LOGGER.debug("Calling Add File from Domain Controller with param pathname '{}'", pathname);
        domainController.addFile(pathname, date, true);
        LOGGER.debug("Added file from Domain Controller called");
    }

    public void rewriteHistoryFile(ArrayList<Integer> linesToRemove) throws CompressorException {
        LOGGER.debug("Calling Rewrite History File from Domain Controller");
        domainController.rewriteHistoryFile(linesToRemove);
        LOGGER.debug("Rewrite from Domain Controller called");
    }

    public String[] compressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Compress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.compressFile(algorithm, pathname, filename, extension);
    }

    public String[] uncompressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Uncompress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.uncompressFile(algorithm, pathname, filename, extension);
    }

    public String getFilenameFromPath(String pathname) {
        LOGGER.debug("Calling Get Filename from path from Domain Controller with pathname param '{}'", pathname);
        return domainController.getFilenameFromPath(pathname);
    }

    public String getFileSizeFromPath(String pathname) {
        LOGGER.debug("Calling Get FileSize from path from Domain Controller with pathname param '{}'", pathname);
        return domainController.getFileSizeFromPath(pathname);
    }

    public void changeMainViewToComparisonView() {
        mainView.disable();
        comparisonView.show();
    }

    public void changeComparisonViewToMainView() {
        comparisonView.hide();
        mainView.enable();
    }

}

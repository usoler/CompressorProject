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

    /**
     * Constructs a new {@link PresentationController} with their {@link DomainController}, {@link MainViewSwing} and
     * {@link ComparisonViewSwing}
     */
    public PresentationController() {
        LOGGER.debug("Constructing Presentation Controller");
        domainController = new DomainController();
        mainView = new MainViewSwing(this);
        comparisonView = new ComparisonViewSwing(this);
        LOGGER.debug("Presentation Controller constructed");
    }

    /**
     * Initializes the {@link PresentationController} initiating the {@link DomainController}, showing the view
     * and loading the history and the stats
     */
    public void init() {
        LOGGER.debug("Initiating Presentation Controller");
        domainController.init();
        mainView.show();
        loadHistory();
        loadStats();
        LOGGER.debug("Presentation Controller initiated");
    }

    /**
     * Adds a file to the {@link DomainController}
     *
     * @param pathname the file pathname
     * @param date     the file date
     * @throws CompressorException If any error occurs
     */
    public void addFile(String pathname, String date) throws CompressorException {
        LOGGER.debug("Calling Add File from Domain Controller with param pathname '{}'", pathname);
        domainController.addFile(pathname, date, true);
        LOGGER.debug("Added file from Domain Controller called");
    }

    /**
     * Rewrite a history file without a {@link ArrayList<Integer>} of lines
     *
     * @param linesToRemove the lines to remove
     * @throws CompressorException If any error occurs
     */
    public void rewriteHistoryFile(ArrayList<Integer> linesToRemove) throws CompressorException {
        LOGGER.debug("Calling Rewrite History File from Domain Controller");
        domainController.rewriteHistoryFile(linesToRemove);
        LOGGER.debug("Rewrite from Domain Controller called");
    }

    /**
     * Compress a file with a given pathname, filename, extension and algorithm
     *
     * @param algorithm the algorithm to encode
     * @param pathname  the file pathname
     * @param filename  the filename
     * @param extension the file extension
     * @return the compressed file data
     * @throws CompressorException If any error occurs
     */
    public String[] compressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Compress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.compressFile(algorithm, pathname, filename, extension);
    }

    /**
     * Uncompress a file with a given pathname, filename, extension and algorithm
     *
     * @param algorithm the algorithm to decode
     * @param pathname  the file pathname
     * @param filename  the filename
     * @param extension the file extension
     * @return the uncompressed file data
     * @throws CompressorException If any error occurs
     */
    public String[] uncompressFile(String algorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Calling Uncompress File from Domain Controller with params algorithm '{}' and pathname '{}'",
                algorithm, pathname);
        return domainController.uncompressFile(algorithm, pathname, filename, extension);
    }

    /**
     * Gets the filename from a given pathname
     *
     * @param pathname the file pathname
     * @return the filename
     */
    public String getFilenameFromPath(String pathname) {
        LOGGER.debug("Calling Get Filename from path from Domain Controller with pathname param '{}'", pathname);
        return domainController.getFilenameFromPath(pathname);
    }

    /**
     * Gets the file size from a pathname
     *
     * @param pathname the file pathname
     * @return the file size
     */
    public String getFileSizeFromPath(String pathname) {
        LOGGER.debug("Calling Get FileSize from path from Domain Controller with pathname param '{}'", pathname);
        return domainController.getFileSizeFromPath(pathname);
    }

    /**
     * Changes the main view to the comparison view
     */
    public void changeMainViewToComparisonView(String originalContent, String decompressedContent) {
        mainView.disable();
        comparisonView.show();
        comparisonView.setOriginalContent(originalContent);
        comparisonView.setDecompressionResult(decompressedContent);
    }

    /**
     * Changes the comparison view to the main view
     */
    public void changeComparisonViewToMainView() {
        comparisonView.hide();
        mainView.enable();
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

    /**
     * Gets the filename from a given pathname
     *
     * @param pathname the file pathname
     * @return the filename
     */
    public String getContentFromPath(String pathname) {
        LOGGER.debug("Calling Get Content from path from Domain Controller with pathname param '{}'", pathname);
        return domainController.getContentFromPath(pathname);
    }
}

package data;

import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class DataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
    private static final String HISTORY_FILENAME = "history.txt";
    private static final String STATS_FILENAME = "stats.txt";
    private static final String HISTORY_PATH = String.format("DATA/%s", HISTORY_FILENAME);
    private static final String STATS_PATH = String.format("DATA/%s", STATS_FILENAME);

    private static DataController singletonDataController;

    private DataController() {
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

    public static ArrayList<String> getAllFilesFromHistory() throws CompressorException {
        LOGGER.debug("Reading file '{}' with path '{}'", HISTORY_FILENAME, HISTORY_PATH);
        FileReader fileReader = getFileReader();
        Scanner scanner = new Scanner(fileReader);

        ArrayList<String> arrayOfFilePaths = readAllPathsFromFile(scanner);
        return arrayOfFilePaths;
    }

    public static void removePathAt(int index) {

    }

    private static ArrayList<String> readAllPathsFromFile(Scanner scanner) {
        ArrayList<String> arrayOfFilePaths = new ArrayList<>();
        while (scanner.hasNextLine()) {
            arrayOfFilePaths.add(scanner.nextLine());
        }
        return arrayOfFilePaths;
    }

    private static FileReader getFileReader() throws CompressorException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(HISTORY_PATH);
        } catch (FileNotFoundException e) {
            String message = "Failure to read all paths from history file in database";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.READ_HISTORY_PATHS);
        }
        return fileReader;
    }
}

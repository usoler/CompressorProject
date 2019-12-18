package data;

import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class DataController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataController.class);
    private static final String HISTORY_FILENAME = "history.txt";
    private static final String STATS_FILENAME = "stats.txt";
    private static final String DATA_FOLDER_PATH = "DATA/";
    private static final String HISTORY_PATH = DATA_FOLDER_PATH + HISTORY_FILENAME;
    private static final String STATS_PATH = DATA_FOLDER_PATH + STATS_FILENAME;

    private static DataController singletonDataController;

    private DataController() {
        // Intentionally empty
    }

    /**
     * Gets the single instance of Data Controller
     *
     * @return a {@link DataController} instance
     */
    public static DataController getInstance() {
        LOGGER.debug("Getting Data Controller instance");
        if (Objects.isNull(singletonDataController)) {
            LOGGER.debug("Data Controller not instanced. Instantiating");
            singletonDataController = new DataController();
        }

        return singletonDataController;
    }

    /**
     * Gets all the persisted stats from the database
     *
     * @return a {@link ArrayList<String>} with all the data of the stats
     * @throws CompressorException If any error occurs
     */
    public static ArrayList<String> getAllStatsFromStats() throws CompressorException {
        LOGGER.debug("Reading file '{}' with path '{}'", STATS_FILENAME, STATS_PATH);
        FileReader fileReader = getFileReaderFromStats();
        Scanner scanner = new Scanner(fileReader);

        ArrayList<String> arrayOfStats = readAllStatsFromFile(scanner);
        return arrayOfStats;
    }

    /**
     * Gets all the persisted data of files from the database
     *
     * @return a {@link ArrayList<String>} with all the data of the files
     * @throws CompressorException If any error occurs
     */
    public static ArrayList<String> getAllFilesFromHistory() throws CompressorException {
        LOGGER.debug("Reading file '{}' with path '{}'", HISTORY_FILENAME, HISTORY_PATH);
        FileReader fileReader = getFileReaderFromHistory();
        Scanner scanner = new Scanner(fileReader);

        ArrayList<String> arrayOfFileData = readAllPathsFromFile(scanner);
        return arrayOfFileData;
    }

    /**
     * Persists the history file data in the database
     *
     * @param pathname the file pathname to persist
     * @param date     the file date to persist
     * @throws CompressorException In any error occurs
     */
    public static void addFileToHistoryFile(String pathname, String date) throws CompressorException {
        LOGGER.debug("Adding pathname to the history file");
        try {
            FileWriter fileWriter = new FileWriter(new File(HISTORY_PATH), true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(String.format("%s %s", date, pathname));
            bufferWriter.write('\n');
            bufferWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            String message = String.format("Failure to write the pathname '%s' into '%s'", pathname, HISTORY_FILENAME);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.WRITE_HISTORY_PATHS_FAILURE);
        }
    }

    /**
     * Persists the stats data in the database
     *
     * @param stats the stats data to persist
     * @throws CompressorException If any error occurs
     */
    public static void addStatsToStatsFile(String[] stats) throws CompressorException {
        LOGGER.debug("Adding stats to the stats file");
        try {
            FileWriter fileWriter = new FileWriter(new File(STATS_PATH), true);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < stats.length; ++i) {
                bufferWriter.write(String.valueOf(stats[i]));
                if (i != stats.length - 1) {
                    bufferWriter.write(" ");
                }
            }
            bufferWriter.write('\n');
            bufferWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            String message = String.format("Failure to write the stats '%s'", STATS_FILENAME);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.WRITE_STATS_FAILURE);
        }
    }

    /**
     * Rewrite the history file data in the database
     *
     * @param linesToRemove the lines to remove
     * @throws CompressorException If any error occurs
     */
    public static void rewriteHistoryFile(ArrayList<Integer> linesToRemove) throws CompressorException {
        LOGGER.debug("Rewriting data to the history file");
        try {
            File fileToRead = new File(HISTORY_PATH);
            File temporalFileToWrite = new File(DATA_FOLDER_PATH + "tempFile.txt");
            temporalFileToWrite.createNewFile();

            BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
            BufferedWriter writer = new BufferedWriter(new FileWriter(temporalFileToWrite));

            String currentLine;
            int currentIndex = 0;
            int k = 0;
            while ((currentLine = reader.readLine()) != null) {
                if ((k < linesToRemove.size()) && (currentIndex == linesToRemove.get(k))) {
                    ++k;
                    continue;
                } else {
                    writer.write(currentLine + System.getProperty("line.separator"));
                    ++currentIndex;
                }
            }
            writer.close();
            reader.close();
            fileToRead.delete();
            temporalFileToWrite.renameTo(fileToRead);

        } catch (IOException e) {
            String message = String.format("Failure to rewrite the data into '%s'", HISTORY_FILENAME);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.REWRITE_HISTORY_PATHS_FAILURE);
        }
    }

    private static ArrayList<String> readAllPathsFromFile(Scanner scanner) {
        ArrayList<String> arrayOfFilePaths = new ArrayList<>();
        while (scanner.hasNextLine()) {
            arrayOfFilePaths.add(scanner.nextLine());
        }
        return arrayOfFilePaths;
    }

    private static ArrayList<String> readAllStatsFromFile(Scanner scanner) {
        ArrayList<String> arrayOfStats = new ArrayList<>();
        while (scanner.hasNextLine()) {
            arrayOfStats.add(scanner.nextLine());
        }
        return arrayOfStats;
    }

    private static FileReader getFileReaderFromHistory() throws CompressorException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(HISTORY_PATH);
        } catch (FileNotFoundException e) {
            String message = "Failure to read all paths from history file in database";
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_HISTORY_PATHS_FAILURE);
        }
        return fileReader;
    }

    private static FileReader getFileReaderFromStats() throws CompressorException {
        FileReader fileReader;
        try {
            fileReader = new FileReader(STATS_PATH);
        } catch (FileNotFoundException e) {
            String message = "Failure to read all stats from stats file in database";
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_STATS_FAILURE);
        }
        return fileReader;
    }
}

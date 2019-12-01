package domain.fileManager;

import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);
    private static Scanner scanner;
    private static FileCreator fileCreator;

    public FileReader(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
    }

    public static void readSpecificFile(String pathname) throws CompressorException {
        File file = new File(pathname);
        if (file.isDirectory()) {
            readAllFilesFromFolder(pathname);
        } else {
            InputStream inputStream = readFile(file, pathname);
            closeInputStream(inputStream);
        }
    }

    public static void readAllFilesFromFolder(String folderPathName) throws CompressorException {
        File folder = new File(folderPathName);
        for (File file : folder.listFiles()) {
            String pathname = folderPathName + "/" + file.getName();
            if (file.isFile()) {
                InputStream inputStream = readFile(file, pathname);
                closeInputStream(inputStream);
            } else if (file.isDirectory()) {
                readAllFilesFromFolder(pathname);
            }
        }
    }

    private static void closeInputStream(InputStream inputStream) throws CompressorException {
        try {
            inputStream.close();
        } catch (IOException e) {
            String message = "Failure to close the input stream";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.CLOSE_INPUT_STREAM_FAILURE);
        }
    }

    private static FileInputStream readFile(File file, String pathname) throws CompressorException {
        FileInputStream fileInputStream = startInputStream(file);
        fileCreator.createFileImpl(readDataFromFile(fileInputStream).getBytes(), pathname);
        return fileInputStream;
    }

    private static FileInputStream startInputStream(File file) throws CompressorException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            String message = "Failure to read the file";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.READ_INPUT_STREAM_FAILURE);
        }
    }

    private static String readDataFromFile(FileInputStream fileInputStream) {
        scanner = new Scanner(fileInputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}

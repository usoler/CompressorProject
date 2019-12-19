package domain.fileManager;

import domain.IFile;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);
    private static Scanner scanner;
    private static FileCreator fileCreator;

    /**
     * Constructs a new {@link FileReader} with a given {@link FileCreator}
     *
     * @param fileCreator the file creator of this file reader
     */
    public FileReader(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
    }

    /**
     * Reads a file from a given pathname and folder
     *
     * @param pathname the file pathname
     * @param folder   the folder's file
     * @throws CompressorException If any error occurs
     */
    public static void readFile(String pathname, IFile folder) throws CompressorException {
        File file = new File(pathname);
        if (file.isDirectory()) {
            readAllFilesFromFolder(pathname, folder);
        } else {
            InputStream inputStream = readFile(file, pathname, folder);
            closeInputStream(inputStream);
        }
    }

    /**
     * Reads all the files from a given folder pathname
     *
     * @param folderPathName the folder pathname
     * @param i_folder       // TODO ??
     * @throws CompressorException If any error occurs
     */
    public static void readAllFilesFromFolder(String folderPathName, IFile i_folder) throws CompressorException {
        File folderFile = new File(folderPathName);
        IFile folder = fileCreator.createFolder(folderFile, folderPathName);
        if (!Objects.isNull(i_folder)) {
            i_folder.addFile(folder);
        }
        for (File file : folderFile.listFiles()) {
            String pathname = folderPathName + "/" + file.getName();
            if (file.isFile()) {
                InputStream inputStream = readFile(file, pathname, folder);
                closeInputStream(inputStream);
            } else if (file.isDirectory()) {
                readAllFilesFromFolder(pathname, folder);
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

    private static FileInputStream readFile(File file, String pathname, IFile folder) throws CompressorException {
        FileInputStream fileInputStream = startInputStream(file);
        String format = obtainFormatOfFile(file.getName());
        byte[] binaryData;
        try {
            binaryData = Files.readAllBytes(new File(pathname).toPath());
        } catch (IOException e) {
            String message = String.format("Failure to read all bytes in file from path '%s'", pathname);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
        }
        if (isCompressed(format)) {
            fileCreator.createCompressedFile(binaryData, pathname, folder, file.getName(), binaryData.length, format);
        } else {
            fileCreator.createDecompressedFile(binaryData, pathname, folder, file.getName(), binaryData.length, format);
        }
        return fileInputStream;
    }

    private static FileInputStream startInputStream(File file) throws CompressorException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            String message = String.format("Failure to read the file '%s'", file.getName());
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.READ_INPUT_STREAM_FAILURE);
        }
    }

    private static String readDataFromFile(FileInputStream fileInputStream) {
        scanner = new Scanner(fileInputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }


    private static String obtainFormatOfFile(String name) {
        String[] values = name.split("\\.");
        return values[values.length - 1];
    }

    private static boolean isCompressed(String format) {
        if (format == "lz78" || format == "lzw" || format == "jpeg") {
            return true;
        } else {
            return false;
        }
    }
}
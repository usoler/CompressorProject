package domain.fileManager;

import domain.IFile;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class FileWriterImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWriterImpl.class);

    public static void writeToFile(IFile file, boolean append_value) throws CompressorException {
        //Removes "input/" from pathname and set its to "output/"
        String newPathname = file.getPathname();

        File fileWritten = new File(newPathname);
        FileOutputStream fileOutputStream = getFileOutputStream(fileWritten);

        writeIntoFileOutputStream(file, fileOutputStream);
        closeFileOutputStream(fileOutputStream);
    }

    private static FileOutputStream getFileOutputStream(File fileWritten) throws CompressorException {
        try {
            return new FileOutputStream(fileWritten);
        } catch (FileNotFoundException ex) {
            String message = "Error initializing file output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.INIT_FILE_OUTPUT_STREAM_FAILURE);
        }
    }

    private static void writeIntoFileOutputStream(IFile file, FileOutputStream fileOutputStream) throws CompressorException {
        try {
            fileOutputStream.write(file.getData());
        } catch (IOException ex) {
            String message = "Error writting into file output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_FILE_OUTPUT_STREAM_FAILURE);
        }
    }

    private static void closeFileOutputStream(FileOutputStream fileOutputStream) throws CompressorException {
        try {
            fileOutputStream.close();
        } catch (IOException ex) {
            String message = "Error closing file output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.CLOSE_FILE_OUTPUT_STREAM_FAILURE);
        }
    }

    private static FileWriter getFileWriter(boolean append_value, String newPathname) throws CompressorException {
        try {
            return new FileWriter(newPathname, append_value);
        } catch (IOException ex) {
            String message = "Error initializing FileWriter";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.INIT_FILE_WRITER_FAILURE);
        }
    }
}

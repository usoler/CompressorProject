package domain;

import data.DataController;
import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import domain.fileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class DomainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainController.class);

    private DataController dataController;
    private FileManager fileManager;

    public void DomainController() {
        LOGGER.debug("Constructing Domain Controller");
        init();
        LOGGER.debug("Domain Controller constructed");
    }

    public void init() {
        LOGGER.debug("Initiating Domain Controller");
        dataController = DataController.getInstance();
        fileManager = new FileManager();
        LOGGER.debug("Domain Controller initiated");
    }

    public ArrayList<String> loadHistory() throws CompressorException {
        LOGGER.debug("Loading history from Persistence Layer");
        ArrayList<String> arrayOfFilePaths = dataController.getAllFilesFromHistory();
        int index = 0;
        for (String pathname : arrayOfFilePaths) {
            if (checkFile(new File(pathname), index)) {
                addFile(pathname);
            } else {
                arrayOfFilePaths.remove(pathname);
            }
            ++index;
        }
        return arrayOfFilePaths;
    }

    private boolean checkFile(File file, int index) {
        if (!file.exists()) {
            LOGGER.warn("File '{}' with pathname '{}' does not exist", file.getName(), file.getAbsolutePath());
            // TODO
            dataController.removePathAt(index);
            return false;
        }
        return true;
    }

    public void addFile(String pathname) throws CompressorException {
        LOGGER.debug("Adding file to the domain");
        fileManager.readFile(pathname);
        LOGGER.debug("File added to the domain");
    }

    public String compressFile(String typeOfAlgorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Compressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        validateCompressFile(typeOfAlgorithm, extension);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);

        byte[] encodingResult;
        try {
            encodingResult = algorithm.encodeFile(Files.readAllBytes(new File(pathname).toPath()));
        } catch (IOException e) {
            String message = String.format("Failure to read all bytes in file from path '%s'", pathname);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
        }
        String compressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectCompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, compressedPath);
        fileManager.writeFile(compressedPath, false);
        return compressedPath;
    }

    public String uncompressFile(String typeOfAlgorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Uncompressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        validateUncompressFile(typeOfAlgorithm, extension);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
        byte[] encodingResult;
        try {
            encodingResult = algorithm.decodeFile(Files.readAllBytes(new File(pathname).toPath()));
        } catch (IOException e) {
            String message = String.format("Failure to read all bytes in file from path '%s'", pathname);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
        }
        String uncompressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectUncompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, uncompressedPath);
        fileManager.writeFile(uncompressedPath, false);
        return uncompressedPath;
    }

    private void validateCompressFile(String typeOfAlgorithm, String extension) throws CompressorException {
        if (typeOfAlgorithm.equals("JPEG") && !extension.equals("ppm")) {
            String message = String.format("Extension '%s' not supported to encode with Jpeg algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.JPEG_EXTENSION_COMPATIBILITY_FAILURE);
        } else if (typeOfAlgorithm.equals("LZ78") && (!extension.equals("txt"))) {
            String message = String.format("Extension '%s' not supported to encode with Lz78 algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.LZ78_EXTENSION_COMPATIBILITY_FAILURE);
        } else if (typeOfAlgorithm.equals("LZW") && (!extension.equals("txt"))) {
            String message = String.format("Extension '%s' not supported to encode with Lzw algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.LZW_EXTENSION_COMPATIBILITY_FAILURE);
        }
    }

    private void validateUncompressFile(String typeOfAlgorithm, String extension) throws CompressorException {
        if (typeOfAlgorithm.equals("JPEG") && !extension.equals("jpeg")) {
            String message = String.format("Extension '%s' not supported to decode with Jpeg algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.JPEG_EXTENSION_COMPATIBILITY_FAILURE);
        } else if (typeOfAlgorithm.equals("LZ78") && (!extension.equals("lz78"))) {
            String message = String.format("Extension '%s' not supported to decode with Lz78 algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.LZ78_EXTENSION_COMPATIBILITY_FAILURE);
        } else if (typeOfAlgorithm.equals("LZW") && (!extension.equals("lzw"))) {
            String message = String.format("Extension '%s' not supported to decode with Lzw algorithm", extension);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.LZW_EXTENSION_COMPATIBILITY_FAILURE);
        }
    }

    private Algorithm selectAlgorithm(String typeOfAlgorithm) {
        Algorithm algorithm = new Algorithm();
        if (typeOfAlgorithm.equals("JPEG")) {
            LOGGER.debug("Initiating JPEG compression");
            algorithm.setAlgorithmInterface(new Jpeg());
        } else if (typeOfAlgorithm.equals("LZ78")) {
            LOGGER.debug("Initiating LZ78 compression");
            algorithm.setAlgorithmInterface(new Lz78());
        } else {
            LOGGER.debug("Initiating LZW compression");
            algorithm.setAlgorithmInterface(new Lzw());
        }
        return algorithm;
    }

    private String selectUncompressedExtension(String typeOfAlgorithm) {
        if (typeOfAlgorithm.equals("JPEG")) {
            return ".ppm";
        } else {
            return ".txt";
        }
    }

    private String selectCompressedExtension(String typeOfAlgorithm) {
        if (typeOfAlgorithm.equals("JPEG")) {
            return ".jpeg";
        } else if (typeOfAlgorithm.equals("LZ78")) {
            return ".lz78";
        } else {
            return ".lzw";
        }
    }
}

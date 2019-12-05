package domain;

import data.DataController;
import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.exception.CompressorException;
import domain.fileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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

    public void addFile(String pathname) throws CompressorException {
        LOGGER.debug("Adding file to the domain");
        fileManager.readFile(pathname);
        LOGGER.debug("File added to the domain");
    }

    public String compressFile(String typeOfAlgorithm, String pathname, String filename) throws CompressorException {
        LOGGER.debug("Compressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);

        byte[] encodingResult = null;
        try {
            encodingResult = algorithm.encodeFile(Files.readAllBytes(new File(pathname).toPath()));
        } catch (IOException e) {
            // TODO: implement exception
//            String message = "";
//            LOGGER.error(message);
//            throw new CompressorException(message, e, CompressorErrorCode.);
        }
        String compressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectCompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, compressedPath);
        fileManager.writeFile(compressedPath, false);
        return compressedPath;
    }

    public String uncompressFile(String typeOfAlgorithm, String pathname, String filename) throws CompressorException {
        LOGGER.debug("Uncompressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
        byte[] encodingResult = null;
        try {
            encodingResult = algorithm.decodeFile(Files.readAllBytes(new File(pathname).toPath()));
        } catch (IOException e) {
            // TODO: implement exception
//            String message = "";
//            LOGGER.error(message);
//            throw new CompressorException(message, e, CompressorErrorCode.);
        }
        String uncompressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectUncompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, uncompressedPath);
        fileManager.writeFile(uncompressedPath, false);
        return uncompressedPath;
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

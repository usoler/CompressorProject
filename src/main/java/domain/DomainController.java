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

    public void addFile(String pathname) {
        LOGGER.debug("Adding file to the domain");
        try {
            fileManager.readFile(pathname);
        } catch (CompressorException e) {
            // throw exception
            // TODO: implement exception
            e.printStackTrace();
        }
        LOGGER.debug("File added to the domain");
    }

    public String compressFile(String typeOfAlgorithm, String pathname, String filename) {
        LOGGER.debug("Compressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);

        try {
            Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
            byte[] encodingResult = algorithm.encodeFile(Files.readAllBytes(new File(pathname).toPath()));
            String compressedPath = System.getProperty("user.dir") + "/output/" + filename + ".lz78";
            fileManager.createFile(encodingResult, compressedPath);
            fileManager.writeFile(compressedPath, false);
            return compressedPath;
        } catch (IOException | CompressorException e) {
            // TODO: implement exception
        }
        LOGGER.debug("File compressed");
        return null;
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

    public String uncompressFile(String typeOfAlgorithm, String pathname, String filename) {
        LOGGER.debug("Uncompressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);

        try {
            Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
            byte[] encodingResult = algorithm.decodeFile(Files.readAllBytes(new File(pathname).toPath()));
            String extension = selectExtension(typeOfAlgorithm);
            String uncompressedPath = System.getProperty("user.dir") + "/output/" + filename + extension;
            fileManager.createFile(encodingResult, uncompressedPath);
            fileManager.writeFile(uncompressedPath, false);
            return uncompressedPath;
        } catch (IOException | CompressorException e) {
            // TODO: implement exception
        }
        LOGGER.debug("File uncompressed");
        return null;
    }

    private String selectExtension(String typeOfAlgorithm) {
        if (typeOfAlgorithm.equals("JPEG")) {
            return ".ppm";
        } else {
            return ".txt";
        }
    }
}

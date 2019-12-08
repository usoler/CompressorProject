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
        ArrayList<String> arrayOfFileData = dataController.getAllFilesFromHistory();
        ArrayList<String> goodArrayOfFileData = new ArrayList<>();
        ArrayList<Integer> linesToRemove = new ArrayList<>();
        int index = 0;
        for (String data : arrayOfFileData) {
            String pathname = data.split(" ")[2];
            if (checkFile(new File(pathname))) {
                addFile(pathname, null, false);
                goodArrayOfFileData.add(data);
            } else {
                linesToRemove.add(index);
            }
            ++index;
        }
        rewriteHistoryFile(linesToRemove);
        return goodArrayOfFileData;
    }

    public ArrayList<String> loadStats() throws CompressorException {
        LOGGER.debug("Loading stats from Persistence Layer");
        return dataController.getAllStatsFromStats();
    }

    private boolean checkFile(File file) {
        if (!file.exists()) {
            LOGGER.warn("File '{}' with pathname '{}' does not exist", file.getName(), file.getAbsolutePath());
            return false;
        }
        return true;
    }

    public void addFile(String pathname, String date, boolean writeInFile) throws CompressorException {
        LOGGER.debug("Adding file to the domain");
        fileManager.readFile(pathname);
        if (writeInFile) {
            dataController.addFileToHistoryFile(pathname, date);
        }
        LOGGER.debug("File added to the domain");
    }

    private void addStats(String filename, String algorithm, String type, String[] statsValues) throws CompressorException {
        LOGGER.debug("Adding stats to the Persistence Layer");
        String[] stats = new String[]{filename, algorithm, type, statsValues[1], statsValues[2],
                statsValues[3], statsValues[4]};
        dataController.addStatsToStatsFile(stats);
        LOGGER.debug("Stats added to the Persistence Layer");
    }

    public void rewriteHistoryFile(ArrayList<Integer> linesToRemove) throws CompressorException {
        LOGGER.debug("Rewriting history file");
        dataController.rewriteHistoryFile(linesToRemove);
        LOGGER.debug("History file rewrote");
    }

    public String[] compressFile(String typeOfAlgorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Compressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        validateCompressFile(typeOfAlgorithm, extension);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
        String[] response = new String[5];

        byte[] encodingResult;
        try {
            byte[] data = Files.readAllBytes(new File(pathname).toPath());
            int uncompressedSize = data.length;
            long start = System.currentTimeMillis();
            encodingResult = algorithm.encodeFile(data);
            long end = System.currentTimeMillis();
            int compressedSize = encodingResult.length;
            response = printEncodeStatistics(start, end, uncompressedSize, compressedSize, response);
        } catch (IOException e) {
            String message = String.format("Failure to read all bytes in file from path '%s'", pathname);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
        }
        String compressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectCompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, compressedPath);
        fileManager.writeFile(compressedPath, false);
        response[0] = compressedPath;
        addStats(filename, typeOfAlgorithm, "Encode", response);
        return response;
    }

    private String[] printEncodeStatistics(long start, long end, float uncompressedSize, float compressedSize, String[] response) {
        LOGGER.debug("Showing the encode statistics:");
        response[2] = Long.toString(end - start);
        LOGGER.debug("Elapsed Time: '{}'", response[2]);
        response[3] = showCompressionSpeed(start, end, uncompressedSize, compressedSize);
        LOGGER.debug("Compression Speed: '{}'", response[3]);
        response[1] = showCompressionRatio(uncompressedSize, compressedSize);
        LOGGER.debug("Compression Ratio: '{}'", response[1]);
        response[4] = Float.toString((1.0f - compressedSize / uncompressedSize) * 100.0f);
        LOGGER.debug("Elapsed Time: '{}'", response[4]);

        return response;
    }

    private String showCompressionSpeed(long start, long end, float uncompressedSize, float compressedSize) {
        if ((end - start) == 0 && (uncompressedSize - compressedSize) == 0) {
            return "0";
        } else {
            return Float.toString((uncompressedSize - compressedSize) / (end - start));
        }
    }

    private String showCompressionRatio(float uncompressedSize, float compressedSize) {
        if (uncompressedSize == 0 && compressedSize == 0) {
            return "0";
        } else {
            return Float.toString(uncompressedSize / compressedSize);
        }
    }

    public String[] uncompressFile(String typeOfAlgorithm, String pathname, String filename, String extension) throws CompressorException {
        LOGGER.debug("Uncompressing file with algorithm '{}', pathanme '{}' and filename '{}'",
                typeOfAlgorithm, pathname, filename);
        validateUncompressFile(typeOfAlgorithm, extension);
        Algorithm algorithm = selectAlgorithm(typeOfAlgorithm);
        String[] response = new String[5];
        byte[] encodingResult;
        try {
            byte[] data = Files.readAllBytes(new File(pathname).toPath());
            int compressedSize = data.length;
            long start = System.currentTimeMillis();
            encodingResult = algorithm.decodeFile(data);
            long end = System.currentTimeMillis();
            int uncompressedSize = encodingResult.length;
            response = printEncodeStatistics(start, end, uncompressedSize, compressedSize, response);
        } catch (IOException e) {
            String message = String.format("Failure to read all bytes in file from path '%s'", pathname);
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
        }
        String uncompressedPath = System.getProperty("user.dir") + "/output/" + filename
                + selectUncompressedExtension(typeOfAlgorithm);
        fileManager.createFile(encodingResult, uncompressedPath);
        fileManager.writeFile(uncompressedPath, false);
        response[0] = uncompressedPath;
        addStats(filename, typeOfAlgorithm, "Decode", response);
        return response;
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

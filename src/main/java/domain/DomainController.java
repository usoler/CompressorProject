package domain;

import data.DataController;
import domain.algorithms.Algorithm;
import domain.algorithms.AlgorithmInterface;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.components.PpmComponent;
import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import domain.fileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

public class DomainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainController.class);
    private DataController dataController;
    private FileManager fileManager;

    /**
     * Constructs an empty new {@link DomainController}
     */
    public void DomainController() {
        LOGGER.debug("Constructing Domain Controller");
        init();
        LOGGER.debug("Domain Controller constructed");
    }

    /**
     * Initializes the {@link DomainController} with their {@link DataController} and {@link FileManager}
     */
    public void init() {
        LOGGER.debug("Initiating Domain Controller");
        dataController = DataController.getInstance();
        fileManager = new FileManager();
        LOGGER.debug("Domain Controller initiated");
    }

    /**
     * Loads the history from the {@link DataController}
     *
     * @return the {@link ArrayList<String>} of history file data
     * @throws CompressorException If any error occurs
     */
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
        rewriteHistoryFile(linesToRemove, null);
        return goodArrayOfFileData;
    }

    /**
     * Loads the stats from the {@link DataController}
     *
     * @return the {@link ArrayList<String>} of stats file data
     * @throws CompressorException If any error occurs
     */
    public ArrayList<String> loadStats() throws CompressorException {
        LOGGER.debug("Loading stats from Persistence Layer");
        return dataController.getAllStatsFromStats();
    }

    /**
     * Reads a file from a given pathname and adds it to the history file
     *
     * @param pathname    the file pathname
     * @param date        the file date
     * @param writeInFile if the file should be write in the history file
     * @throws CompressorException If any error occurs
     */
    public void addFile(String pathname, String date, boolean writeInFile) throws CompressorException {
        LOGGER.debug("Adding file to the domain");
        fileManager.readFile(pathname);
        if (writeInFile) {
            dataController.addFileToHistoryFile(pathname, date);
        }
        LOGGER.debug("File added to the domain");
    }

    /**
     * Rewrites the history file without an {@link ArrayList<Integer>} of lines
     *
     * @param linesToRemove the lines to remove
     * @throws CompressorException If any error occurs
     */
    public void rewriteHistoryFile(ArrayList<Integer> linesToRemove, String pathname) throws CompressorException {
        LOGGER.debug("Rewriting history file");
        if (!Objects.isNull(pathname)) {
            fileManager.removeFile(pathname);
        }

        dataController.rewriteHistoryFile(linesToRemove);
        LOGGER.debug("History file rewrote");
    }

    /**
     * Compress a file with a given pathname, filename, extension and type of algorithm
     *
     * @param typeOfAlgorithm the type of algorithm to encode
     * @param pathname        the file pathname
     * @param filename        the filename
     * @param extension       the file extension
     * @return the compressed file data
     * @throws CompressorException If any error occurs
     */
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
        fileManager.createCompressedFile(encodingResult, compressedPath, filename, encodingResult.length, typeOfAlgorithm);
        fileManager.writeFile(compressedPath);
        response[0] = compressedPath;
        addStats(filename, typeOfAlgorithm, "Encode", response);
        return response;
    }

    public String[] compressFolder(String typeOfAlgorithm, String pathname, String filename) throws CompressorException {
        LOGGER.debug("Compressing folder with algorithm '{}', pathanme '{}' and filename '{}'",
                pathname, filename);
        String[] response = new String[5];
        Algorithm algorithm = new Algorithm();
        Folder folder = (Folder) fileManager.getFile(pathname);
        byte[] encodingResult = algorithm.encodeFolder(folder, getTextAlgorithm(typeOfAlgorithm));

        String compressedPath = System.getProperty("user.dir") + "/output/" + filename
                + ".folderzip";
        fileManager.createCompressedFile(encodingResult, compressedPath, filename, encodingResult.length, "folderzip");
        fileManager.writeFile(compressedPath);
        response[0] = compressedPath;
        LOGGER.debug("Folder compressed");

        return response;
    }

    public String[] uncompressFolder(String pathname, String filename) throws CompressorException {
        LOGGER.debug("Uncompressing folder with pathname '{}' and filename '{}'", pathname, filename);
        String[] response = new String[5];
        Algorithm algorithm = new Algorithm();
        fileManager.readFile(pathname);
        IFile folder = fileManager.getFile(pathname);
        Folder decodedFolder = algorithm.decodeFolder(folder.getData(), "output/");

        fileManager.createFolderFromIFile(folder);

        String uncompressedPath = System.getProperty("user.dir") + "/output/" + filename
                + ".folder";

        response[0] = uncompressedPath;
        LOGGER.debug("Folder uncompressed");
        return response;
    }

    private AlgorithmInterface getTextAlgorithm(String typeOfAlgorithm) {
        if (typeOfAlgorithm.equals("LZW")) {
            return new Lzw();
        } else {
            return new Lz78();
        }
    }

    /**
     * Uncompress a file with a given pathname, filename, extension and  type of algorithm to decode
     *
     * @param typeOfAlgorithm the type of algorithm with to decode
     * @param pathname        the file pathname
     * @param filename        the filename
     * @param extension       the file extension
     * @return the uncompressed file data
     * @throws CompressorException If any error occurs
     */
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
        fileManager.createDecompressedFile(encodingResult, uncompressedPath, filename, encodingResult.length, getFormatByTypeOfAlgorithm(typeOfAlgorithm));
        fileManager.writeFile(uncompressedPath);
        response[0] = uncompressedPath;
        addStats(filename, typeOfAlgorithm, "Decode", response);
        return response;
    }

    /**
     * Gets a filename from a given pathname
     *
     * @param pathname the file pathname
     * @return the filename
     */
    public String getFilenameFromPath(String pathname) {
        LOGGER.debug("Calling Get Filename from path from Domain Controller with pathname param '{}'", pathname);
        return fileManager.getFile(pathname).getName() + '.' + fileManager.getFile(pathname).getFormat().toLowerCase();
    }

    /**
     * Gets a file size from a given pathname
     *
     * @param pathname the file pathname
     * @return the file size
     */
    public String getFileSizeFromPath(String pathname) {
        LOGGER.debug("Calling Get FileSize from path from Domain Controller with pathname param '{}'", pathname);
        return formatSize(fileManager.getFile(pathname).getSize());
    }

    private boolean checkFile(File file) {
        if (!file.exists()) {
            LOGGER.warn("File '{}' with pathname '{}' does not exist", file.getName(), file.getPath());
            return false;
        }
        return true;
    }

    private void addStats(String filename, String algorithm, String type, String[] statsValues) throws CompressorException {
        LOGGER.debug("Adding stats to the Persistence Layer");
        String[] stats = new String[]{filename, algorithm, type, statsValues[1], statsValues[2],
                statsValues[3], statsValues[4]};
        dataController.addStatsToStatsFile(stats);
        LOGGER.debug("Stats added to the Persistence Layer");
    }

    private String formatSize(int size) {
        double bytesSize = (size / (1024 * 1024));
        double roundedSize = (Math.round(bytesSize * 100.0) / 100.0);
        if (roundedSize <= 0.1) {
            return Double.toString((double) size / 1000) + " B";
        } else if (roundedSize < 0.5 && roundedSize > 0.1) {
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " KB";
        } else {
            bytesSize = (double) size / (1024000);
            return Double.toString((Math.round(bytesSize * 100.0) / 100.0)) + " MB";
        }
    }

    private String[] printEncodeStatistics(long start, long end, float uncompressedSize, float compressedSize, String[] response) {
        LOGGER.debug("Showing the encode statistics:");
        response[2] = Long.toString(end - start);
        LOGGER.debug("Elapsed Time: '{}'", response[2]);
        response[3] = showCompressionSpeed(start, end, uncompressedSize, compressedSize);
        LOGGER.debug("Compression Speed: '{}'", response[3]);
        response[1] = showCompressionRatio(uncompressedSize, compressedSize);
        LOGGER.debug("Compression Ratio: '{}'", response[1]);
        response[4] = Float.toString(avoidNegativeValues((1.0f - compressedSize / uncompressedSize) * 100.0f));
        LOGGER.debug("Elapsed Time: '{}'", response[4]);

        return response;
    }

    private String showCompressionSpeed(long start, long end, float uncompressedSize, float compressedSize) {
        if ((end - start) == 0 && (uncompressedSize - compressedSize) == 0) {
            return "0";
        } else {
            return Float.toString(avoidNegativeValues((uncompressedSize - compressedSize) / (end - start)));
        }
    }

    private String showCompressionRatio(float uncompressedSize, float compressedSize) {
        if (uncompressedSize == 0 && compressedSize == 0) {
            return "0";
        } else {
            return Float.toString(avoidNegativeValues(uncompressedSize / compressedSize));
        }
    }

    private float avoidNegativeValues(float value) {
        if (value < 0.0f) {
            value = 0.0f;
        }
        return value;
    }

    private String getFormatByTypeOfAlgorithm(String typeOfAlgorithm) throws CompressorException {
        if (typeOfAlgorithm.equals("JPEG")) {
            return "ppm";
        } else {
            return "txt";
        }
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

    /**
     * Gets the content from a given pathname
     *
     * @param pathname the file pathname
     * @return the filename
     */
    public String getContentFromPath(String pathname) {
        LOGGER.debug("Calling Get Content from path from Domain Controller with pathname param '{}'", pathname);
        return new String(fileManager.getFile(pathname).getData());
    }

    public int[][][] readPpmImage(String pathname) throws CompressorException {
        LOGGER.debug("Calling Read Content from path from Domain Controller with pathname param '{}'", pathname);
        Matrix<Pixel> pixelMatrix = new PpmComponent().readPpmFile(fileManager.getFile(pathname).getData()).getMatrix();
        int[][][] matrix = new int[pixelMatrix.getNumberOfRows()][pixelMatrix.getNumberOfColumns()][3];
        for (int i = 0; i < pixelMatrix.getNumberOfRows(); i++) {
            for (int j = 0; j < pixelMatrix.getNumberOfColumns(); j++) {
                Pixel pixel = pixelMatrix.getElementAt(i, j);
                matrix[i][j][0] = (int) pixel.getX();
                matrix[i][j][1] = (int) pixel.getY();
                matrix[i][j][2] = (int) pixel.getZ();
            }
        }
        return matrix;
    }
}

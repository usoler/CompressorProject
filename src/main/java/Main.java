import domain.algorithms.lossy.Jpeg;
import domain.fileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    //private static final String pathTest = "input/blackbuck.ascii.ppm";
    private static final String pathTest = "input/16x16.ppm";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.readFile(pathTest);

        Jpeg algorithm = new Jpeg();

        //String pathnameEncoded = "output/JPEGTest/blackbuck.ascii.jpeg";
        String pathnameEncoded = "output/JPEGTest/16x16.jpeg";

        try {
            // Encoding
            byte[] response = algorithm.encode(fileManager.getFile(pathTest).getData());
            fileManager.createFile(response, pathnameEncoded);
            fileManager.writeFile(pathnameEncoded, false);

            // Decoding
            byte[] responseDecode = algorithm.decode(fileManager.getFile(pathnameEncoded).getData());

        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            throw new IOException();
        }

    }
}
import domain.algorithms.lossy.Jpeg;
import domain.fileManager.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MainJPEG {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String pathTest = "input/blackbuck.ascii.ppm";
    //private static final String pathTest = "input/16x16.ppm";
    //private static final String pathTest = "input/black16x16.ppm";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.readFile(pathTest);

        Jpeg algorithm = new Jpeg();

        String pathnameEncoded = "output/JPEGTest/blackbuck.ascii.jpeg";
        //String pathnameEncoded = "output/JPEGTest/16x16.jpeg";
        //String pathnameEncoded = "output/JPEGTest/black16x16.jpeg";

        //String pathnameDecoded = "output/JPEGTest/16x16.ppm";
        //String pathnameDecoded = "output/JPEGTest/black16x16.ppm";
        String pathnameDecoded = "output/JPEGTest/black.ppm";

        try {
            // Encoding
            byte[] responseEncode = algorithm.encode(fileManager.getFile(pathTest).getData());
            fileManager.createFile(responseEncode, pathnameEncoded);
            fileManager.writeFile(pathnameEncoded, false);

            // Decoding
            byte[] responseDecode = algorithm.decode(fileManager.getFile(pathnameEncoded).getData());
            fileManager.createFile(responseDecode, pathnameDecoded);
            fileManager.writeFile(pathnameDecoded, false);

        } catch (Exception ex) {
            LOGGER.debug(ex.getMessage());
            ex.printStackTrace();
            throw new IOException();
        }

    }
}
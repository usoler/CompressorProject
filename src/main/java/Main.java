import domain.algorithms.lossy.Jpeg;
import domain.fileManager.FileManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String pathTest = "input/blackbuck.ascii.ppm";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.readFile(pathTest);

        Jpeg algorithm = new Jpeg();

        String pathnameEncoded = "output/JPEGTest/blackbuck.ascii.jpeg";

        try {
            byte[] response = algorithm.encode(fileManager.getFile(pathTest).getData());
            fileManager.createFile(response, pathnameEncoded);
            fileManager.writeFile(pathnameEncoded, false);

            String val = new String(response, StandardCharsets.US_ASCII);

        } catch (Exception ex) {
            throw new IOException();
        }

    }
}
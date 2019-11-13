import domain.algorithms.lossy.Jpeg;
import domain.fileManager.FileManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String pathTest = "input/16x16.ppm";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.readFile(pathTest);

        Jpeg algorithm = new Jpeg();

        String pathnameEncoded = "output/JPEGTest/16x16.jpeg";

        try {
            byte[] response = algorithm.encode(fileManager.getFile(pathTest).GetData());
            fileManager.createFile(response, pathnameEncoded);
            fileManager.writeFile(pathnameEncoded, false);

            String val = new String(response, StandardCharsets.US_ASCII);

        } catch (Exception ex) {
            throw new IOException();
        }

    }
}
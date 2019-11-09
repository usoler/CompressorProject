import domain.algorithms.lossy.Jpeg;
import domain.fileManager.FileManager;

import java.io.IOException;

public class Main {
    private static final String pathTest = "input/16x16.ppm";

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        fileManager.readFile(pathTest);

        Jpeg algorithm = new Jpeg();

        try {
            algorithm.encode(fileManager.getFile(pathTest).GetData());

        } catch (Exception ex) {
            throw new IOException();
        }

    }
}
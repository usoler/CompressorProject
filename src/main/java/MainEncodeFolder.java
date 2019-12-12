import domain.algorithms.Algorithm;
import domain.algorithms.lossy.Jpeg;
import domain.exception.CompressorException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainEncodeFolder {
    public static void main(String[] args) throws IOException, CompressorException {
        testEncodeFolder();
        //testEncodeJPEG();
        //System.out.println(File.separator);
    }

    private static void testEncodeFolder() throws IOException, CompressorException {
        String inputPath = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\inputTxt";
        File folder = new File(inputPath);
        Algorithm compressor = new Algorithm();
        byte[] encodedFolder = compressor.encodeFolder(folder);

        String outputPath = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\outputEncodeFolder";
        File compressedFolder1 = new File(outputPath + '\\' + folder.getName() + ".fl1");
        File compressedFolder2 = new File(outputPath + '\\' + folder.getName() + ".fl2");
        File compressedFolder3 = new File(outputPath + '\\' + folder.getName() + ".fl3");

        FileWriter writer = new FileWriter(compressedFolder1);
        writer.write(new String(encodedFolder));
        writer.close();

        FileOutputStream outputStream = new FileOutputStream(compressedFolder2);
        outputStream.write(encodedFolder);
        outputStream.close();

        Files.write(Paths.get(compressedFolder3.getPath()), encodedFolder);

        /*if (!compressedFolder1.createNewFile()) {
            System.out.println("Cannot create file 1");
        }
        if (!compressedFolder2.createNewFile()) {
            System.out.println("Cannot create file 2");
        }
        if (!compressedFolder2.createNewFile()) {
            System.out.println("Cannot create file 3");
        }*/

        outputPath = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\outputDecodeFolder";
        compressor.decodeFolder(Files.readAllBytes(compressedFolder2.toPath()), outputPath);
    }

    private static void testEncodeJPEG() throws IOException, CompressorException {
        String inputPath = "C:\\Users\\mique\\OneDrive\\Desktop\\input\\pruebas_jpeg\\pbmlib.ppm";
        File file = new File(inputPath);
        Algorithm compressor = new Algorithm();
        compressor.setAlgorithmInterface(new Jpeg());
        //byte[] encodeFile = compressor.encodeFile(Files.readAllBytes(file.toPath()));
        String path = System.getProperty("user.dir") + "/input/testImages/comentsTest.ppm";
        byte[] encodeFile = compressor.encodeFile(Files.readAllBytes(new File(path).toPath()));
    }
}

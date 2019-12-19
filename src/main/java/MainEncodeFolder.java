import domain.Folder;
import domain.IFile;
import domain.algorithms.Algorithm;
import domain.algorithms.AlgorithmInterface;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.exception.CompressorException;
import domain.fileManager.FileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class MainEncodeFolder {
    public static void main(String[] args) throws IOException, CompressorException {
        //testEncodeFolder();
        //testEncodeJPEG();
        //System.out.println(File.separator);
        //testEncodeFolderWithFolderImpl();
        //testEncodeJPEGWithFileManager();
//        testFindFolder();
        testFinal();
//        pruebas();
    }


    private static AlgorithmInterface askAlgorithm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Que algoritmo de texto quieres? (1)Lz78 o (2)Lzw");
        int algorithmCode = scanner.nextInt();
        AlgorithmInterface textAlgorithm = new Lz78();
        if (algorithmCode == 2) {
            textAlgorithm = new Lzw();
        }
        return textAlgorithm;
    }

    // hecho para comprobar si funcion
    private static void testFinal() throws CompressorException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quieres realizar el test de la caperta ComprimirCarpeta/input? y/n");
        String response = scanner.next();
        AlgorithmInterface textAlgorithm = askAlgorithm();
        if (response.equals("y")) {
            System.out.println("Procedo a realizar la prueba");
            testEncodeDemo(textAlgorithm);
        } else if (response.equals("n")) {
            System.out.println("Introduce la direccion de la carpeta que quieres comprimir");
            String path = scanner.nextLine();
            testEncodeFolder(path, textAlgorithm);
        } else {
            System.out.println("Codigo invalido");
        }

    }

    private static void testEncodeFolder(String path, AlgorithmInterface textAlgorithm) {
        System.out.println("NO IMPLEMENTADO, NO ES NECESARIO PARA LA ENTREGA");
    }

    private static void testEncodeDemo(AlgorithmInterface textAlgorithm) throws CompressorException, IOException {
        FileManager fileManager = new FileManager();
        String path = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\input";
        fileManager.readFolder(path);
        IFile folder = fileManager.getFile(path);
        Algorithm algorithm = new Algorithm();
        byte[] compressionResult = algorithm.encodeFolder((Folder)folder, textAlgorithm);

        String outputEncode = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\outputEncodeFolder" + '\\' + folder.getName() + ".fdr";
        File compressedFolder = new File(outputEncode); // TODO: ??
        fileManager.createDecompressedFile(compressionResult, outputEncode, folder.getName(), compressionResult.length, ".jdr");
        fileManager.writeFile(outputEncode);

        fileManager.readFile(outputEncode);
        IFile file = fileManager.getFile(outputEncode);
        String outputDecode = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\outputDecodeFolder";
        Folder decodedFolder = algorithm.decodeFolder(file.getData(), outputDecode);
        createFileUsingIFile(decodedFolder);
    }

    private static void createFileUsingIFile(IFile iFile) throws CompressorException {
        if (iFile instanceof Folder) {
            String pathname = ((Folder) iFile).getPathname();
            File folder = new File(pathname);
            folder.mkdir();
            for (IFile f : ((Folder) iFile).getFiles()) {
                if (f instanceof domain.File) {
                    FileManager fileManager = new FileManager();
                    String newPathname = pathname + File.separator + ((domain.File) f).getName();
                    fileManager.createDecompressedFile(((domain.File) f).getData(), newPathname,
                            ((domain.File) f).getName(),
                            ((domain.File) f).getData().length, "." + ((domain.File) f).getFormat());
                    fileManager.writeFile(newPathname);
                } else if (f instanceof Folder) {
                    createFileUsingIFile(f);
                }
            }
        }
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

    private static void testEncodeJPEGWithFileManager() throws IOException, CompressorException {
        FileManager fileManager = new FileManager();
        fileManager.readFile("C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\input\\pruebas_jpeg\\comentsTest.ppm");
        IFile file = fileManager.getFile("C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\input\\pruebas_jpeg\\comentsTest.ppm");
        System.out.println(Arrays.toString(file.getData()));

        Algorithm compressor = new Algorithm();
        compressor.setAlgorithmInterface(new Jpeg());
        byte[] encodeFile = compressor.encodeFile(file.getData());
        byte[] decodeResult = compressor.decodeFile(encodeFile);

        String outputPath2 = "C:\\Users\\mique\\OneDrive\\Desktop\\ComprimirCarpeta\\outputDecodeJPEG2";
        File compressedFolder = new File(outputPath2 + '\\' + file.getName() + ".ppm");
        FileOutputStream outputStream = new FileOutputStream(compressedFolder);
        outputStream.write(decodeResult);
        outputStream.close();
    }

    private static void printTree(IFile iFile, int depth) {
        for (IFile f : iFile.getFiles()) {
            for (int i = 0; i < depth; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
            if (f.getFiles() != null) {
                printTree(f, depth + 1);
            }
        }
    }

    private static void printTreeWithFileContent(IFile iFile, int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("\t");
        }
        if (iFile instanceof domain.File) {
            System.out.println(((domain.File) iFile).getName() + ", data: " + new String(((domain.File) iFile).getData()));
        } else if (iFile instanceof Folder) {
            System.out.println(((Folder) iFile).getName());
            for (IFile f : ((Folder) iFile).getFiles()) {
                printTreeWithFileContent(f, depth + 1);
            }
        }
    }

    private static void printTree(File iFile, int depth) {
        for (File f : Objects.requireNonNull(iFile.listFiles())) {
            for (int i = 0; i < depth; i++) {
                System.out.print("\t");
            }
            System.out.println(f.getName());
            if (f.isDirectory()) {
                printTree(f, depth + 1);
            }
        }
    }
}

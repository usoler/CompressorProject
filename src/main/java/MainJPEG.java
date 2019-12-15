import domain.algorithms.Algorithm;
import domain.algorithms.AlgorithmInterface;
import domain.algorithms.lossy.Jpeg;
import data.fileManager.FileManager;

import java.io.File;
import java.nio.file.Files;
import java.util.Scanner;

public class MainJPEG {

    public static void main(String[] args) throws Exception {
        System.out.println("COMPRESSOR 3000");
        System.out.println("THIS PROGRAM IS DEVELOPED BY THE GROUP 10-3");
        System.out.println("DEVELOPERS ARE:");
        System.out.println("GONZALEZ JURADO, ANGEL");
        System.out.println("MALQUI CRUZ, MIGUEL ANGEL");
        System.out.println("SOLER CRUZ, LUIS ORIOL");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("THIS VERSION OF THE PROGRAM COMPILES IN THE ALGORITHM JPEG");
        System.out.println("PRESS 1 IF YOU WANT TO TEST OUR FILES");
        System.out.println("PRESS 2 IF YOU WANT TO INSERT YOUR LOCAL FILE");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        //Initialize program
        String projectPath = System.getProperty("user.dir");
        FileManager fileManager = new FileManager();
        String path = null;

        switch (command) {
            case 1:
                System.out.println("OUR FILE IS GOING TO BE USED");
                path = projectPath + "/TestImage.ppm";
                break;
            case 2:
                System.out.println("PLEASE INSERT THE LOCAL PATH OF YOUR FILE");
                path = scanner.next();
                System.out.println("YOUR FILE IS GOING TO BE USED");

                break;

            default:
                System.out.println("ERROR. 1 OR 2 NOT RECEIVED");
                System.out.println("CLOSING PROGRAM");
                System.exit(0);
                break;
        }

        fileManager.readFile(path);

        System.out.println("FILE READ SUCCESSFULLY");
        System.out.println("PRESS 1 IF YOU WANT TO SEE THE CONTENTS OF THE FILE");
        System.out.println("PRESS 2 OR ELSE TO CONTINUE");
        command = scanner.nextInt();
        if (command == 1) {
            System.out.println("THIS ARE THE CONTENTS OF THE FILE:");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            String text = new String(fileManager.getFile(path).getData());
            System.out.println(text);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        }

        AlgorithmInterface jpeg = new Jpeg();
        Algorithm algorithm = new Algorithm();
        algorithm.setAlgorithmInterface(jpeg);

        System.out.println("COMPRESSION IS GOING TO START");
        System.out.println("PRESS 1 TO CONTINUE");
        command = scanner.nextInt();
        if (command != 1) {
            System.out.println("CLOSING PROGRAM");
            System.exit(0);
        }
        //byte[] encodingResult = algorithm.encodeFile(fileManager.getFile(path).getData());
        byte[] encodingResult = algorithm.encodeFile(Files.readAllBytes(new File(path).toPath()));
        System.out.println("WRITE THE NEW NAME OF THE COMPRESSED FILE");
        String compressedName = scanner.next();
        String format = "jpeg";
        String compressedPath= projectPath + "/output/" + compressedName + "." + format;

        fileManager.createCompressedFile(encodingResult, compressedPath, compressedName, encodingResult.length, format);

        fileManager.writeFile(compressedPath, false);

        System.out.println("PRESS 1 TO SEE THE CONTENTS OF THE FILE COMPRESSED");
        System.out.println("PRESS 2 OR ELSE TO CONTINUE");
        command = scanner.nextInt();
        if (command == 1) {
            System.out.println("THIS ARE THE CONTENTS OF THE FILE COMPRESSED:");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            fileManager.readFile(compressedPath);
            String text = new String(fileManager.getFile(compressedPath).getData());
            System.out.println(text);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        }


        System.out.println("DECOMPRESSION IS GOING TO START");
        System.out.println("PRESS 1 TO CONTINUE");
        command = scanner.nextInt();
        if (command != 1) {
            System.out.println("CLOSING PROGRAM");
            System.exit(0);
        }
        byte[] decodingResult = algorithm.decodeFile(fileManager.getFile(compressedPath).getData());
        System.out.println("WRITE THE NEW NAME OF THE DECOMPRESSED FILE");
        String decompressedName = scanner.next();
        String decompressedPath = projectPath + "/output/" + decompressedName;

        fileManager.createDecompressedFile(decodingResult, decompressedPath,decompressedName,decodingResult.length,"ppm");

        fileManager.writeFile(decompressedPath, false);

        System.out.println("PRESS 1 TO SEE THE CONTENTS OF THE FILE DECOMPRESSED");
        System.out.println("PRESS 2 OR ELSE TO CONTINUE");
        command = scanner.nextInt();
        if (command == 1) {
            System.out.println("THIS ARE THE CONTENTS OF THE FILE DECOMPRESSED:");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
            fileManager.readFile(decompressedPath);
            String text = new String(fileManager.getFile(decompressedPath).getData());
            System.out.println(text);
            System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        }
        System.out.println("THANK YOU FOR USING OUR PROGRAM");
        System.out.println("CLOSING PROGRAM");
    }
}
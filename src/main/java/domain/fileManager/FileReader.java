package domain.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {

    static private Scanner scanner;
    static private FileCreator fileCreator;

    public FileReader(FileCreator _fileCreator) {
        fileCreator = _fileCreator;
    }

    public static void readSpecificFile(String filePathname) {
        File file = new File(filePathname);
        if (file.isDirectory()) {
            System.out.println("YOU ARE READING A FOLDER. READING A FOLDER INSTEAD");
            readAllFilesFromFolder(filePathname);
        } else {
            InputStream inputStream = null;
            String data;
            try {
                inputStream = new FileInputStream(file);
                long timeStart = System.currentTimeMillis();
                scanner = new Scanner(inputStream).useDelimiter("\\A");
                data = scanner.hasNext() ? scanner.next() : "";
                fileCreator.createFileImpl(data.getBytes(), filePathname);
                long timeEnd = System.currentTimeMillis();
            } catch (IOException e) {
                System.out.println("FILE NOT FOUND");
                System.out.println("DID YOU FORGET THE EXTENSION OF THE FILE?");
                System.exit(0);
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }

    public static void readAllFilesFromFolder(String folderPathName) {
        File folder = new File(folderPathName);
        File[] listOfFiles = folder.listFiles();

        List<String> listOfStrings = new ArrayList<String>();
        InputStream inputStream = null;
        String data;
        String filePathname;
        for (File file : listOfFiles) {
            filePathname = folderPathName + "/" + file.getName();
            if (file.isFile()) {
                try {

                    System.out.println(file.getName());
                    inputStream = new FileInputStream(file);
                    long timeStart = System.currentTimeMillis();
                    scanner = new Scanner(inputStream).useDelimiter("\\A");
                    data = scanner.hasNext() ? scanner.next() : "";
                    fileCreator.createFileImpl(data.getBytes(), filePathname);
                    long timeEnd = System.currentTimeMillis();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (Objects.nonNull(inputStream)) {
                            inputStream.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (file.isDirectory()) {
                readAllFilesFromFolder(filePathname);
            }
        }
    }
}

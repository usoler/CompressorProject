package domain.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class FileReader {

    static private Scanner scanner;
    static private FileCreator fileCreator;

    public FileReader()
    {
        scanner = null;
        fileCreator = new FileCreator();
    }

    public static void readSpecificFile(String filePathname, FileManager fileManager)
    {
        File file = new File(filePathname);
        if (file.isDirectory())
        {
            System.out.println("You are reading a Folder. Reading Folder instead");
            readAllFilesFromFolder(filePathname,fileManager);
        }
        else
        {
            InputStream inputStream = null;
            String data = null;
            try {
                inputStream = new FileInputStream(file);
                scanner = new Scanner(inputStream).useDelimiter("\\A");
                data = scanner.hasNext() ? scanner.next() : "";
                fileCreator.createFileImpl(data,filePathname, fileManager);
            } catch (IOException e) {
                e.printStackTrace();
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
    public static void readAllFilesFromFolder(String folderPathName, FileManager fileManager)
    {
        File folder = new File(folderPathName);
        File [] listOfFiles = folder.listFiles();

        List<String> listOfStrings = new ArrayList<String>();
        InputStream inputStream = null;
        String data = null;
        String filePathname = null;
        for (File file : listOfFiles)
        {
            filePathname = folderPathName + "/" + file.getName();
            if (file.isFile() )
            {
                try {

                    System.out.println(file.getName());
                    inputStream = new FileInputStream(file);
                    scanner = new Scanner(inputStream).useDelimiter("\\A");
                    data = scanner.hasNext() ? scanner.next() : "";
                    fileCreator.createFileImpl(data,filePathname, fileManager);
                } catch (IOException e){
                    e.printStackTrace();
                }finally {
                    try{
                        if (inputStream != null){
                            inputStream.close();
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }

            }
            else if (file.isDirectory())
            {
                readAllFilesFromFolder(filePathname, fileManager);
            }
        }
    }


}

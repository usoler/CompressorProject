package domain.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class FileReader {

    static private Scanner scanner;

    public static String readSpecificFile(String filePathname)
    {
        File file = new File(filePathname);
        InputStream inputStream = null;
        String data = null;
        try {
            inputStream = new FileInputStream(file);
            scanner = new Scanner(inputStream).useDelimiter("\\A");
            data = scanner.hasNext() ? scanner.next() : "";
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
        return data;

    }
    public static List<String> readAllFilesFromFolder(String folderPathName)
    {
        File folder = new File(folderPathName);
        File [] listOfFiles = folder.listFiles();

        List<String> listOfStrings = new ArrayList<String>();
        InputStream inputStream = null;
        String data = null;
        for (File file : listOfFiles)
        {

            if (file.isFile())
            {

                try {

                    System.out.println(file.getName());
                    inputStream = new FileInputStream(file);
                    scanner = new Scanner(inputStream).useDelimiter("\\A");
                    data = scanner.hasNext() ? scanner.next() : "";
                    listOfStrings.add(data);


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
                String newPathname = folderPathName + "/" + file.getName();
                listOfStrings.addAll(readAllFilesFromFolder(newPathname));

            }
        }
        return listOfStrings;
    }


}

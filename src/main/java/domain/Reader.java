package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Reader {

    public static void readSpecificFile(String filePathname)
    {
        File file = new File(filePathname);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            Fichero fichero = new Fichero(inputStream);
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
    public static void readAllFilesFromFolder(String folderPathName)
    {
        File folder = new File(folderPathName);
        File [] listOfFiles = folder.listFiles();

        InputStream inputStream = null;
        for (File file : listOfFiles)
        {
            if (file.isFile())
            {


                    System.out.println(file.getName());
                    System.out.println(file.getName());

                //inputStream = new FileInputStream(file);

                    //Fichero fichero = new Fichero(file.)


            }
        }
    }


}

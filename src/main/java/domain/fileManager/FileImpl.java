package domain.fileManager;

import java.io.InputStream;
import java.util.Scanner;


public class FileImpl {

    private String data;
    private String pathname;
    public FileImpl(String string,String i_pathname)
    {
        System.out.println("Fichero generado correctamente");
        data = string;
        pathname=i_pathname;
    }

    public String GetData()
    {
        return data;
    }
    public String GetPathname()
    {
        return pathname;
    }
}

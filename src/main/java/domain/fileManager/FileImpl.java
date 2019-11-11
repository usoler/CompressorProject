package domain.fileManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class FileImpl {

    private byte[] data;
    protected String pathname;

    public FileImpl(byte[] i_data,String i_pathname)
    {
        System.out.println("Fichero generado correctamente");
        data = i_data;
        pathname=i_pathname;
    }

    public byte[] getData()
    {
        return data;
    }
    public String getPathname()
    {
        return pathname;
    }
}

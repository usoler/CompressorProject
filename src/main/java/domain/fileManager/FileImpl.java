package domain.fileManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


public class FileImpl {

    private byte[] data;
    protected String pathname;

    public FileImpl(byte[] data, String pathname) {
        System.out.println("File generated successfully");
        this.data = data;
        this.pathname = pathname;
    }

    public byte[] getData() {
        return data;
    }

    public String getPathname() {
        return pathname;
    }
}

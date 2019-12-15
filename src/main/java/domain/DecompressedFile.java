package domain;


public class DecompressedFile extends File {


    public DecompressedFile(byte[] data, String pathname,String name, int size, String format)
    {
        super(data,pathname, name, format,size);
    }

}

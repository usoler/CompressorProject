package domain;


public class CompressedFile extends File {

    public CompressedFile(byte[] data, String pathname,String name, int size, String format)
    {
        super(data,pathname, name, format,size);
    }

}

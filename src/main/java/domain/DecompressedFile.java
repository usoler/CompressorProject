package domain;

public class DecompressedFile extends File {

    public DecompressedFile(String name, String format, int size, Folder folder) {
        super(name, format, size, folder);
    }

    public DecompressedFile(byte[] data, String pathname){super(data,pathname);}
}

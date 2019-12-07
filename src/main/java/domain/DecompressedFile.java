package domain;

public class DecompressedFile extends File {


    public DecompressedFile(byte[] data, String pathname)
    {
        super(data,pathname);
    }

    @Override
    public void addFile(IFile file) {

    }
}

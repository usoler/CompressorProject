package domain;


public class CompressedFile extends File {

    public CompressedFile(byte[] data, String pathname){ super(data,pathname);}

    @Override
    public void addFile(IFile file) {

    }
}

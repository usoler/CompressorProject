package domain;

import java.util.ArrayList;

public class FileStub extends File {

    public FileStub(byte[] data, String pathname,String name, int size, String format)
    {
        super(data,pathname, name, format,size);
    }

    public String toString() {
        return String.format("Fichero{name='example', format='format', size=2 , folder=}");
    }

    public String getName() {
        return "example";
    }

    public String getFormat() {
        return "format";
    }

    @Override
    public ArrayList<IFile> getFiles() {
        return null;
    }

    public int getNumberOfFiles() {
        return 2;
    }

    @Override
    public void addFile(IFile file) {

    }

    @Override
    public void setFiles(ArrayList<IFile> files) {

    }


}

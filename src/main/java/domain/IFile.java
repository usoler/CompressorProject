package domain;

import java.util.ArrayList;

public interface IFile {

    //Interface Getters
    String getPathname();

    byte[] getData();

    int getNumberOfFiles();

    String getName();

    String getFormat();

    ArrayList<IFile> getFiles();

    int getSize();

    //Interface Setters

    void addFile(IFile file);

    void setFiles(ArrayList<IFile> files);

    void setName(String name);

    void setFormat(String format);

    void setSize(int size);

    void setNumberOfFiles(int numberOfFiles);

    void setPathname(String pathname);

    void setData(byte[] data);



}

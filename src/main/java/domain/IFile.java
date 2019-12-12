package domain;

import java.util.ArrayList;

public interface IFile {

    //Interface Getters
    String getPathname();

    byte[] getData();

    int getNumberOfFiles();

    //String getName();

    //String getFormat();

    //ArrayList<File> getFiles();

    //Interface Setters

    void addFile(IFile file);

    //void addFolder(Folder folder);

    //void setFiles(ArrayList<File> files);





}

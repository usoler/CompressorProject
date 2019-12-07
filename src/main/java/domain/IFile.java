package domain;

import java.util.ArrayList;

public interface IFile {

    //Interface Getters
    String getPathname();

    byte[] getData();

    int getSize();

    //String getName();

    //String getFormat();

    //ArrayList<File> getFiles();

    //ArrayList<Folder> getFolders();





    //Interface Setters

    void addFile(IFile file);

    //void addFolder(Folder folder);

    //void setFiles(ArrayList<File> files);





}

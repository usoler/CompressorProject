package domain;

import java.util.ArrayList;

public class CompressedFile extends File {
    public CompressedFile(String name, String format, int size, Folder folder) {
        super(name, format, size, folder);
    }

    public void setFolders(ArrayList<Folder> folders)
    {
        super.setFolders(folders);
    }

    public CompressedFile(byte[] data, String pathname){ super(data,pathname);}
}

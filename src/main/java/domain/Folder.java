package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Folder implements IFile{
    private String name, format;
    private int size;
    private ArrayList<IFile> files;
    private String pathname;

    public Folder(String name, String format) {
        this.name = name;
        this.format = format;
        this.size = 0;
        files = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public String getPathname(){return null;}

    public byte[] getData(){ return null;}

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<IFile> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<IFile> files) {
        for (IFile file : this.files) {
            size -= file.getSize();
        }
        if (!Objects.isNull(files)) {
            this.files = files;
            for (IFile file : files) {
                size += file.getSize();
            }
        }
    }

    public void addFile(IFile file) {
        files.add(file);
        size += file.getSize();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return size == folder.size &&
                name.equals(folder.name) &&
                format.equals(folder.format) &&
                Objects.equals(files, folder.files);
    }

    @Override
    public String toString() {
        return String.format("Folder{name='%s', format='%s', size=%s}", name, format, size);
    }
}

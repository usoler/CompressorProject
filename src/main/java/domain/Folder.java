package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Folder {
    private String name, format;
    private int size;
    private ArrayList<File> files;
    private ArrayList<Folder> folders;

    public Folder(String name, String format) {
        this.name = name;
        this.format = format;
        this.size = 0;
        files = new ArrayList<>();
        folders = new ArrayList<>();
    }

    public Folder(String name, String format, ArrayList<File> files, ArrayList<Folder> folders) {
        this(name, format);
        setFiles(files);
        setFolders(folders);
    }

    public String getName() {
        return name;
    }

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

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        for (File file : this.files) {
            size -= file.getSize();
        }
        if (!Objects.isNull(files)) {
            this.files = files;
            for (File file : files) {
                size += file.getSize();
            }
        }
    }

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Folder> folders) {
        for (Folder folder : this.folders) {
            size -= folder.getSize();
        }
        if (!Objects.isNull(folders)) {
            this.folders = folders;
            for (Folder folder : folders) {
                size += folder.getSize();
            }
        }
    }

    public void addFile(File file) {
        files.add(file);
        size += file.getSize();
        file.setFolder(this);
    }


    public void addFolder(Folder folder) {
        folders.add(folder);
        size += folder.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return size == folder.size &&
                name.equals(folder.name) &&
                format.equals(folder.format) &&
                Objects.equals(files, folder.files) &&
                Objects.equals(folders, folder.folders);
    }

    @Override
    public String toString() {
        return String.format("Folder{name='%s', format='%s', size=%s}", name, format, size);
    }
}

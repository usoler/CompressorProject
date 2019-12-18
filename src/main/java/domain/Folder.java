package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Folder implements IFile {

    private String name, format;
    private int numberOfFiles;
    private ArrayList<IFile> files;
    private String pathname;

    /**
     * Constructs a new {@link Folder}
     *
     * @param name     the folder name
     * @param format   the folder format
     * @param pathname the folder pathname
     */
    public Folder(String name, String format, String pathname) {
        this.pathname = pathname;
        this.name = name;
        this.format = format;
        this.numberOfFiles = 0;
        files = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPathname() {
        return pathname;
    }

    @Override
    public void setPathname(String pathname) {
        this.pathname = pathname;
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    @Override
    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    @Override
    public ArrayList<IFile> getFiles() {
        return files;
    }

    @Override
    public void setFiles(ArrayList<IFile> files) {
        for (IFile file : this.files) {
            numberOfFiles -= file.getNumberOfFiles();
        }
        if (!Objects.isNull(files)) {
            this.files = files;
            for (IFile file : files) {
                numberOfFiles += file.getNumberOfFiles();
            }
        }
    }

    @Override
    public void addFile(IFile file) {
        files.add(file);
        numberOfFiles += file.getNumberOfFiles();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return numberOfFiles == folder.numberOfFiles &&
                name.equals(folder.name) &&
                format.equals(folder.format) &&
                Objects.equals(files, folder.files);
    }

    @Override
    public String toString() {
        return String.format("Folder{name='%s', format='%s', size=%s}", name, format, numberOfFiles);
    }

    // File only functions // TODO ??
    @Override
    public byte[] getData() {
        return null;
    }

    @Override
    public void setData(byte[] data) {
    }

    @Override
    public int getSize() {
        return -1;
    }

    @Override
    public void setSize(int size) {
    }
}

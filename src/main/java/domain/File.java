package domain;

import java.util.ArrayList;

public abstract class File implements IFile {

    protected String pathname;
    private String name, format;
    private int size;
    private byte[] data;

    /**
     * Constructs a new {@link File}
     *
     * @param data     the file data
     * @param pathname the file pathname
     * @param name     the file name
     * @param format   the file format
     * @param size     the file size
     */
    public File(byte[] data, String pathname, String name, String format, int size) {
        this.data = data;
        this.name = name;
        this.format = format;
        this.size = size;
        this.pathname = pathname;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
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
    public byte[] getData() {
        return data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
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
    public String getFormat() {
        return format;
    }

    @Override
    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return String.format("Fichero{name='%s', format='%s', size=%s , folder=%s}", name, format, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File file = (File) o;
        return size == file.size &&
                name.equals(file.name) &&
                format.equals(file.format);
    }

    @Override
    public ArrayList<IFile> getFiles() {
        return null;
    }

    @Override
    public void setFiles(ArrayList<IFile> files) {
    }

    @Override
    public void addFile(IFile file) {
    }

    @Override
    public int getNumberOfFiles() {
        return -1;
    }
}

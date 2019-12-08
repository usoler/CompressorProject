package domain;

public abstract class File implements IFile{
    private String name, format;
    private int size;
    private byte[] data;
    protected String pathname;


    public File(byte[] data, String pathname, String name, String format, int size) {
        this.data = data;
        this.name = name;
        this.format = format;
        this.size = size;
        this.pathname = pathname;
    }

    public String getPathname() { return pathname; }

    public byte[] getData() { return data;}

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

    public int getNumberOfFiles() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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
}

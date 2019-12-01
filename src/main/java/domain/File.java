package domain;

import java.util.Objects;

public abstract class File {
    private String name, format;
    private int size;
    private Folder folder;

    public File(String name, String format, int size, Folder folder) {
        this.name = name;
        this.format = format;
        this.size = size;
        this.folder = folder;
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

    public void setSize(int size) {
        this.size = size;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        String folderName;
        if (Objects.isNull(folder)) folderName = "";
        else folderName = folder.getName();
        return String.format("Fichero{name='%s', format='%s', size=%s , folder=%s}", name, format, size, folderName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;
        File fichero = (File) o;
        return size == fichero.size &&
                name.equals(fichero.name) &&
                format.equals(fichero.format);
    }
}

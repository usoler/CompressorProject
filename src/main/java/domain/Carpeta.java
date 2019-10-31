package domain;

import java.util.Objects;
import java.util.Set;

public class Carpeta {
    private String name, format;
    private int size;
    private Set<Fichero> files;
    private Set<Carpeta> folders;

    public Carpeta(String name, String format, int size, Set<Fichero> files, Set<Carpeta> folders) {
        this.name = name;
        this.format = format;
        this.size = size;
        this.files = files;
        this.folders = folders;
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

    public Set<Fichero> getFiles() {
        return files;
    }

    public void setFiles(Set<Fichero> files) {
        this.files = files;
    }

    public Set<Carpeta> getFolders() {
        return folders;
    }

    public void setFolders(Set<Carpeta> folders) {
        this.folders = folders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carpeta)) return false;
        Carpeta carpeta = (Carpeta) o;
        return size == carpeta.size &&
                name.equals(carpeta.name) &&
                format.equals(carpeta.format) &&
                Objects.equals(files, carpeta.files) &&
                Objects.equals(folders, carpeta.folders);
    }

    @Override
    public String toString() {
        return String.format("Carpeta{name='%s', format='%s', size=%s}", name, format, size);
    }
}

package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Carpeta {
    private String name, format;
    private int size;
    private ArrayList<Fichero> files;
    private ArrayList<Carpeta> folders;

    public Carpeta(String name, String format) {
        this.name = name;
        this.format = format;
        this.size = 0;
        files = new ArrayList<>();
        folders = new ArrayList<>();
    }

    public Carpeta(String name, String format, ArrayList<Fichero> files, ArrayList<Carpeta> folders) {
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

    public ArrayList<Fichero> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<Fichero> files) {
        for (Fichero f: this.files) {
            size -= f.getSize();
        }
        if (!Objects.isNull(files)) {
            this.files = files;
            for (Fichero f: files) {
                size += f.getSize();
            }
        }
    }

    public ArrayList<Carpeta> getFolders() {
        return folders;
    }

    public void setFolders(ArrayList<Carpeta> folders) {
        for (Carpeta c: this.folders) {
            size -= c.getSize();
        }
        if (!Objects.isNull(folders)) {
            this.folders = folders;
            for (Carpeta c: folders){
                size += c.getSize();
            }
        }
    }

    public void addFile(Fichero file) {
        files.add(file);
        size += file.getSize();
        file.setFolder(this);
    }


    public void addFolder(Carpeta folder) {
        folders.add(folder);
        size += folder.getSize();
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

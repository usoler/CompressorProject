package domain;

import java.util.Objects;

public abstract class Fichero {
    private String name, format;
    private int size;
    private Carpeta folder;
    protected String pathname;
    private byte[] data;

    public Fichero(String name, String format, int size, Carpeta folder)
    {
        this.name = name;
        this.format = format;
        this.size = size;
        this.folder = folder;
    }


    public Fichero(byte[] data, String pathname, String nombreFichero, String formatoFichero, int tamañoFichero, Carpeta carpeta)
    {
        this.pathname = pathname;
        this.data = data;
        this.name = nombreFichero;
        this.format = formatoFichero;
        this.size = tamañoFichero;
        this.folder = carpeta;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() { return format; }

    public void setFormat(String format) {
        this.format = format;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Carpeta getFolder() {
        return folder;
    }

    public byte[] getData() {
        return data;
    }

    public String getPathname() {
        return pathname;
    }

    public void setFolder(Carpeta folder) {
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
        if (!(o instanceof Fichero)) return false;
        Fichero fichero = (Fichero) o;
        return size == fichero.size &&
                name.equals(fichero.name) &&
                format.equals(fichero.format);
    }
}

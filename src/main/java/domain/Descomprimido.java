package domain;

public class Descomprimido extends Fichero {

    public Descomprimido(byte[] data, String pathname, String nombreFichero, String formatoFichero, int tamañoFichero, Carpeta carpeta) {
        super(data, pathname, nombreFichero, formatoFichero, tamañoFichero, carpeta);
    }

    public Descomprimido(String name, String format, int size, Carpeta carpeta)
    {
        super (name,format,size,carpeta);
    }

}

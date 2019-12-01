package domain;


public class Comprimido extends Fichero {
    public Comprimido(byte[] data, String pathname, String nombreFichero, String formatoFichero, int tamañoFichero, Carpeta carpeta) {
        super(data, pathname, nombreFichero, formatoFichero, tamañoFichero, carpeta);
    }

    public Comprimido(String name, String format, int size, Carpeta carpeta)
    {
        super (name,format,size,carpeta);
    }

}

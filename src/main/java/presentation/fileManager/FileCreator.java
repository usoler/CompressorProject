package presentation.fileManager;

import domain.Carpeta;
import domain.Comprimido;
import domain.Descomprimido;
import domain.Fichero;

import java.io.File;

public class FileCreator {

    private ControladorDeArchivos controladorDeArchivos;

    public FileCreator(ControladorDeArchivos _controladorDeArchivos) {
        controladorDeArchivos = _controladorDeArchivos;
    }

    public void creaFicheroDescomprimido(byte[] data, String pathname, String nombreArchivo, String formatoArchivo, int tamañoFichero, Carpeta carpeta) {
        Fichero file = new Descomprimido(data, pathname, nombreArchivo, formatoArchivo, tamañoFichero, carpeta);
        controladorDeArchivos.setNewFichero(file);
    }

    public void creaFicheroComprimido(byte[] data, String pathname, String nombreArchivo, String formatoArchivo, int tamañoFichero, Carpeta carpeta) {
        Fichero file = new Comprimido(data, pathname, nombreArchivo, formatoArchivo, tamañoFichero, carpeta);
        controladorDeArchivos.setNewFichero(file);
    }

    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

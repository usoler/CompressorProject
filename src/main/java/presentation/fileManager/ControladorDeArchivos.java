package presentation.fileManager;

import domain.Carpeta;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import domain.Fichero;

import java.util.ArrayList;
import java.util.List;

public class ControladorDeArchivos {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControladorDeArchivos.class);
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    static private FileCreator fileCreator;
    private List<Fichero> listaDeFicheros;

    public ControladorDeArchivos() {
        listaDeFicheros = new ArrayList<Fichero>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
        fileCreator.createWorkingFolder(System.getProperty("user.dir"));
    }

    // TODO: DEPRECATED ????
    public static void escribirFichero(Fichero file, boolean append_value) throws CompressorException {
        fileWriter.writeToFile(file, append_value);
    }


    private Fichero encuentraFicheroConPath(String pathname) {
        for (Fichero file : listaDeFicheros) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        System.out.println("FILE NOT FOUND");
        return null;
    }

    public Fichero getFile(String pathname) {
        return encuentraFicheroConPath(pathname);
    }


    public void LeeFichero(String pathname) {
        fileReader.readSpecificFile(pathname, null);
    }


    public void crearComprimido(byte[] data, String pathname, String nombreArchivo, String formatoArchivo, int tamañoFichero, Carpeta carpeta) {
        fileCreator.creaFicheroComprimido(data, pathname, nombreArchivo, formatoArchivo, tamañoFichero, carpeta);
    }
    public void crearDescomprimido(byte[] data, String pathname, String nombreArchivo, String formatoArchivo, int tamañoFichero, Carpeta carpeta) {
        fileCreator.creaFicheroDescomprimido(data, pathname, nombreArchivo, formatoArchivo, tamañoFichero, carpeta);
    }

    public void escribirFichero(String pathname, boolean append_value) throws CompressorException {
        Fichero file = encuentraFicheroConPath(pathname);
        fileWriter.writeToFile(file, append_value);
    }


    public void readFolder(String pathname) {
        fileReader.readAllFilesFromFolder(pathname, null);
    }

    public void setNewFichero(Fichero file) {
        listaDeFicheros.add(file);
    }


    public List<Fichero> getListaDeFicheros() {
        return listaDeFicheros;
    }
}

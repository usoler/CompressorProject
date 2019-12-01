package presentation.fileManager;

import domain.Carpeta;

import javax.sql.rowset.CachedRowSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {

    static private Scanner scanner;
    static private FileCreator fileCreator;

    public FileReader(FileCreator _fileCreator) {
        fileCreator = _fileCreator;
    }

    public static void readSpecificFile(String filePathname, Carpeta carpeta) {
        File file = new File(filePathname);
        if (file.isDirectory()) {
            System.out.println("YOU ARE READING A FOLDER. READING A FOLDER INSTEAD");
            readAllFilesFromFolder(filePathname,creaCarpeta(file,carpeta));
        } else {
            InputStream inputStream = null;
            String data;
            try {
                String nombre = file.getName();
                String formato = obtenerFormatoArchivo(nombre);

                inputStream = new FileInputStream(file);
                scanner = new Scanner(inputStream).useDelimiter("\\A");
                data = scanner.hasNext() ? scanner.next() : "";

                if (estaComprimido(formato)) fileCreator.creaFicheroComprimido(data.getBytes(), filePathname,nombre,formato,data.getBytes().length,carpeta);
                else fileCreator.creaFicheroDescomprimido(data.getBytes(), filePathname,nombre,formato,data.getBytes().length,carpeta);


            } catch (IOException e) {
                System.out.println("FILE NOT FOUND");
                System.out.println("DID YOU FORGET THE EXTENSION OF THE FILE?");
                System.exit(0);
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }


    }

    public static void readAllFilesFromFolder(String folderPathName, Carpeta carpeta) {
        File folder = new File(folderPathName);
        File[] listOfFiles = folder.listFiles();
        InputStream inputStream = null;
        String data;
        String filePathname;
        for (File file : listOfFiles) {
            filePathname = folderPathName + "/" + file.getName();
            if (file.isFile()) {
                try {
                    String nombre = file.getName();
                    String formato = obtenerFormatoArchivo(nombre);

                    inputStream = new FileInputStream(file);
                    scanner = new Scanner(inputStream).useDelimiter("\\A");
                    data = scanner.hasNext() ? scanner.next() : "";


                    if (estaComprimido(formato)) fileCreator.creaFicheroDescomprimido(data.getBytes(), filePathname,nombre,formato,data.getBytes().length,carpeta);
                    else fileCreator.creaFicheroDescomprimido(data.getBytes(), filePathname,nombre,formato,data.getBytes().length,carpeta);


                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (Objects.nonNull(inputStream)) {
                            inputStream.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (file.isDirectory()) {
                readAllFilesFromFolder(filePathname, creaCarpeta(file,carpeta));
            }
        }
    }

    private static boolean estaComprimido(String formato)
    {
        if (formato == "LZ78" || formato == "LZW" || formato == "JPEG")
        {
            return true;
        }
        else return false;
    }

    private static String obtenerFormatoArchivo(String name)
    {
        String formato = null;
        for (int iterador = name.length()-1; iterador >=0; --iterador)
        {
            if (name.charAt(iterador) == '.')
            {
                break;
            }
            else
            {
                if (formato == null) formato = Character.toString(name.charAt(iterador));
                else formato += name.charAt(iterador);
            }
        }
        invertirString(formato);
        return formato;
    }

    private static String invertirString(String string)
    {
        String resultado = null;
        for (int iterador = 0; iterador < string.length(); ++iterador)
        {
            if (resultado==null) resultado = Character.toString(string.charAt(iterador));
            else resultado += string.charAt(iterador);
        }
        return resultado;
    }

    private static Carpeta creaCarpeta(File file, Carpeta _carpeta)
    {
        String carpetaNombre = file.getName();
        String formatoCarpeta = obtenerFormatoArchivo(carpetaNombre);
        Carpeta carpeta = new Carpeta(carpetaNombre,formatoCarpeta);
        carpeta.setCarpetaMadre(_carpeta);
        return carpeta;
    }
}

package domain.fileManager;


import java.util.List;

public class FileWriter {

    public static void writeFile(String string){
        Fichero file = new Fichero(string);
    }

    public static void writeFiles(List<String> listOfStrings){
        for (String string : listOfStrings)
        {
            Fichero file = new Fichero(string);
        }
    }
}

package domain.fileManager;


import java.util.List;

public class FileManager {
    static private FileReader fileReader;
    static private FileWriter fileWriter;

    public static void readAndWriteFile(String pathname){
        String data = fileReader.readSpecificFile(pathname);
        fileWriter.writeFile(data);
    }
    public static void readAndWriteFolder(String pathname){
        List<String> listOfStrings = fileReader.readAllFilesFromFolder(pathname);
        fileWriter.writeFiles(listOfStrings);
    }
}

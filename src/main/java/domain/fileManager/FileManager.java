package domain.fileManager;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    private List<FileImpl> listOfFiles;

    public FileManager(){
        listOfFiles = new ArrayList<FileImpl>();
        fileReader = new FileReader();
        fileWriter = new FileWriterImpl();
    }

    public void readFile(String pathname){
        fileReader.readSpecificFile(pathname,this);
    }

    public static void writeFile(FileImpl file,boolean append_value)throws IOException{
        fileWriter.writeToFile(file,append_value);
    }


    public void readFolder(String pathname) {
        fileReader.readAllFilesFromFolder(pathname,this);
    }

    public static void writeFolder(File file, boolean append_value) throws IOException{
        fileWriter.writeFromFolderToFile(file,append_value);
    }

    public void setNewFile(FileImpl file){
        listOfFiles.add(file);
    }

    public List<FileImpl> getListOfFiles()
    {
        return listOfFiles;
    }
}

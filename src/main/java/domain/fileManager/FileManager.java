package domain.fileManager;

import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    static private FileCreator fileCreator;
    private List<FileImpl> listOfFiles;

    public FileManager() {
        listOfFiles = new ArrayList<FileImpl>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
        fileCreator.createWorkingFolder(System.getProperty("user.dir"));
    }

    // TODO: DEPRECATED ????
    public static void writeFile(FileImpl file, boolean append_value) throws CompressorException {
        fileWriter.writeToFile(file, append_value);
    }

//    public static void writeFolder(File file, boolean append_value) throws CompressorException {
//        fileWriter.writeFromFolderToFile(file, append_value);
//    }

    private FileImpl findFileWithPathname(String pathname) {
        for (FileImpl file : listOfFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        System.out.println("FILE NOT FOUND");
        return null;
    }

    public FileImpl getFile(String pathname) {
        return findFileWithPathname(pathname);
    }


    public void readFile(String pathname) {
        fileReader.readSpecificFile(pathname);
    }

    public void createFile(byte[] data, String pathname) {
        fileCreator.createFileImpl(data, pathname);
    }


    public void writeFile(String pathname, boolean append_value) throws CompressorException {
        FileImpl file = findFileWithPathname(pathname);
        fileWriter.writeToFile(file, append_value);
    }


    public void readFolder(String pathname) {
        fileReader.readAllFilesFromFolder(pathname);
    }

    public void setNewFile(FileImpl file) {
        listOfFiles.add(file);
    }


    public List<FileImpl> getListOfFiles() {
        return listOfFiles;
    }
}

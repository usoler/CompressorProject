package domain.fileManager;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class FileManager {
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    static private FileCreator fileCreator;
    private List<FileImpl> listOfFiles;
    private List<CompressedFile> listOfCompressedFiles;

    public FileManager() {
        listOfFiles = new ArrayList<FileImpl>();
        listOfCompressedFiles = new ArrayList<CompressedFile>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
    }

    private FileImpl findFileWithPathname(String pathname) {
        for (FileImpl file : listOfFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        System.out.println("File not Found");
        return null;
    }

    private CompressedFile findCompressedFileWithPathname(String pathname) {
        for (CompressedFile file : listOfCompressedFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        System.out.println("File not Found");
        return null;
    }

    public FileImpl getFile(String pathname) {
        return findFileWithPathname(pathname);
    }

    public CompressedFile getCompressedFile(String pathname) {
        return findCompressedFileWithPathname(pathname);
    }

    public void readFile(String pathname) {
        fileReader.readSpecificFile(pathname);
    }

    public void createFile(byte[] data, String pathname) {
        fileCreator.createFileImpl(data, pathname);
    }

    public void createCompressedFile(OutputStream oStream, String pathname) {
        fileCreator.createCompressedFile(oStream, pathname);
    }

    public static void writeFile(FileImpl file, boolean appendValue) throws IOException {
        fileWriter.writeToFile(file, appendValue);
    }

    public void writeFile(String pathname, boolean appendValue) throws IOException {
        FileImpl file = findFileWithPathname(pathname);
        fileWriter.writeToFile(file, appendValue);

    }

    public void writeCompressedFile(String pathname, boolean appendValue) throws IOException {
        CompressedFile file = findCompressedFileWithPathname(pathname);
        fileWriter.writeCompressedToFile(file, appendValue);

    }


    public void readFolder(String pathname) {
        fileReader.readAllFilesFromFolder(pathname);
    }

    public static void writeFolder(File file, boolean appendValue) throws IOException {
        fileWriter.writeFromFolderToFile(file, appendValue);
    }

    public void setNewFile(FileImpl file) {
        listOfFiles.add(file);
    }

    public void setNewCompressedFile(CompressedFile file) {
        listOfCompressedFiles.add(file);
    }

    public List<FileImpl> getListOfFiles() {
        return listOfFiles;
    }
}

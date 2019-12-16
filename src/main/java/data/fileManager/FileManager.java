package data.fileManager;

import domain.IFile;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;




public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    static private FileCreator fileCreator;
    private List<IFile> listOfFiles;

    public FileManager() {
        listOfFiles = new ArrayList<>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
        fileCreator.createWorkingFolder(System.getProperty("user.dir"));
    }

    private IFile findFileWithPathname(String pathname) {
        for (IFile file : listOfFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        LOGGER.warn("File not found with pathname '{}'", pathname);
        return null;
    }


    public IFile getFile(String pathname) {
        return findFileWithPathname(pathname);
    }


    public void readFile(String pathname) throws CompressorException {
        fileReader.readFile(pathname,null);
    }

    public void createCompressedFile(byte[] data, String pathname, String name, int size, String format) {
        fileCreator.createCompressedFile(data, pathname,null, name, size, format);
    }

    public void createDecompressedFile(byte[] data, String pathname, String name, int size, String format) {
        fileCreator.createDecompressedFile(data, pathname,null, name, size, format);
    }

    public void writeFile(String pathname, boolean append_value) throws CompressorException {
        IFile file = findFileWithPathname(pathname);
        fileWriter.writeToFile(file, append_value);
    }


    public void readFolder(String pathname) throws CompressorException {
        fileReader.readAllFilesFromFolder(pathname,null);
    }

    public void setNewFile(IFile file) {
        listOfFiles.add(file);
    }


}

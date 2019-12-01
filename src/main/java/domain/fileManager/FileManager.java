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
    private List<CompressedFile> listOfCompressedFiles;

    public FileManager() {
        listOfFiles = new ArrayList<FileImpl>();
        listOfCompressedFiles = new ArrayList<CompressedFile>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
        fileCreator.createWorkingFolder(System.getProperty("user.dir"));
    }

    private FileImpl findFileWithPathname(String pathname) {
        for (FileImpl file : listOfFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        LOGGER.warn("File not found with pathname '{}'", pathname);
        return null;
    }

    private CompressedFile findCompressedFileWithPathname(String pathname) {
        for (CompressedFile file : listOfCompressedFiles) {
            if (file.getPathname().equals(pathname)) {
                return file;
            }
        }
        LOGGER.warn("File not found with pathname '{}'", pathname);
        return null;
    }

    public FileImpl getFile(String pathname) {
        return findFileWithPathname(pathname);
    }

    public CompressedFile getCompressedFile(String pathname) {
        return findCompressedFileWithPathname(pathname);
    }

    public void readFile(String pathname) throws CompressorException {
        fileReader.readSpecificFile(pathname);
    }

    public void createFile(byte[] data, String pathname) {
        fileCreator.createFileImpl(data, pathname);
    }

    public void createCompressedFile(OutputStream oStream, String pathname) {
        fileCreator.createCompressedFile(oStream, pathname);
    }

    public void writeFile(String pathname, boolean append_value) throws CompressorException {
        FileImpl file = findFileWithPathname(pathname);
        fileWriter.writeToFile(file, append_value);
    }

    public void writeCompressedFile(String pathname, boolean append_value) throws CompressorException {
        CompressedFile file = findCompressedFileWithPathname(pathname);
        fileWriter.writeCompressedToFile(file, append_value);

    }

    public void readFolder(String pathname) throws CompressorException {
        fileReader.readAllFilesFromFolder(pathname);
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

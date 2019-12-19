package domain.fileManager;

import domain.IFile;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    static private FileReader fileReader;
    static private FileWriterImpl fileWriter;
    static private FileCreator fileCreator;
    private List<IFile> listOfFiles;

    /**
     * Constructs a new {@link FileManager}
     */
    public FileManager() {
        listOfFiles = new ArrayList<>();
        fileCreator = new FileCreator(this);
        fileReader = new FileReader(fileCreator);
        fileWriter = new FileWriterImpl();
        fileCreator.createWorkingFolder(System.getProperty("user.dir"));
    }

    /**
     * Gets a file from a given pathname
     *
     * @param pathname
     * @return
     */
    public IFile getFile(String pathname) {
        return findFileWithPathname(pathname);
    }

    /**
     * Reads a file from a given pathname
     *
     * @param pathname the file pathname
     * @throws CompressorException If any error occurs
     */
    public void readFile(String pathname) throws CompressorException {
        fileReader.readFile(pathname, null);
    }

    /**
     * Creates a new compressed file
     *
     * @param data     the file data
     * @param pathname the file pathname
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public void createCompressedFile(byte[] data, String pathname, String name, int size, String format) {
        fileCreator.createCompressedFile(data, pathname, null, name, size, format);
    }

    /**
     * Creates a new decompressed file
     *
     * @param data     the file data
     * @param pathname the file pathname
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public void createDecompressedFile(byte[] data, String pathname, String name, int size, String format) {
        fileCreator.createDecompressedFile(data, pathname, null, name, size, format);
    }

    /**
     * Writes a new file with a given pathname
     *
     * @param pathname the file pathname
     * @throws CompressorException If any error occurs
     */
    public void writeFile(String pathname) throws CompressorException {
        IFile file = findFileWithPathname(pathname);
        fileWriter.writeToFile(file);
    }

    /**
     * Reads a folder from a given pathname
     *
     * @param pathname the folder pathname
     * @throws CompressorException If any error occurs
     */
    public void readFolder(String pathname) throws CompressorException {
        fileReader.readAllFilesFromFolder(pathname, null);
    }

    /**
     * Adds a new file in the list of files
     *
     * @param file the file to add
     */
    public void setNewFile(IFile file) {
        listOfFiles.add(file);
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

    public void removeFile(String pathname)
    {
        IFile file = findFileWithPathname(pathname);
        if (file != null)
        {
            listOfFiles.remove(file);
        }
    }

}

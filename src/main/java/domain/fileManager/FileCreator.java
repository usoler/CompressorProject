package domain.fileManager;

import domain.CompressedFile;
import domain.DecompressedFile;
import domain.Folder;
import domain.IFile;

import java.io.File;

public class FileCreator {

    private FileManager fileManager;

    /**
     * Constructs a new {@link FileCreator} with a given {@link FileManager}
     *
     * @param fileManager the file manager of this file creator
     */
    public FileCreator(FileManager fileManager) {
        this.fileManager = fileManager;
    }


    /**
     * Creates a new compressed file
     *
     * @param data     the file data
     * @param pathname the file pathname
     * @param folder   the file folder
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public void createCompressedFile(byte[] data, String pathname, IFile folder, String name, int size, String format) {

        IFile file = new CompressedFile(data, pathname, name, size, format);
        if (folder != null) {
            folder.addFile(file);
        }
        fileManager.setNewFile(file);
    }

    /**
     * Creates a new decompressed file
     *
     * @param data     the file data
     * @param pathname the file pathname
     * @param folder   the file folder
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public void createDecompressedFile(byte[] data, String pathname, IFile folder, String name, int size, String format) {
        IFile file = new DecompressedFile(data, pathname, name, size, format);
        if (folder != null) {
            folder.addFile(file);
        }
        fileManager.setNewFile(file);
    }

    /**
     * Creates a new folder with a given file and pathname
     *
     * @param file           the folder file
     * @param folderPathname the folder pathname
     * @return the created folder
     */
    public Folder createFolder(File file, String folderPathname) {
        String folderName = file.getName();
        Folder folder = new Folder(folderName, "folder", folderPathname);
        fileManager.setNewFile(folder);
        return folder;
    }

    /**
     * Creates a new working folder with a given path
     *
     * @param path the working folder path
     */
    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

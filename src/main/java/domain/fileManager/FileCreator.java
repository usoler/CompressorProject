package domain.fileManager;

import domain.DecompressedFile;
import domain.IFile;

import java.io.File;
import java.io.OutputStream;

public class FileCreator {

    private FileManager fileManager;

    public FileCreator(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createFileImpl(byte[] data, String pathname) {
        IFile file = new DecompressedFile(data, pathname);
        fileManager.setNewFile(file);
    }



    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

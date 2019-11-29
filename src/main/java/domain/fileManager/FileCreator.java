package domain.fileManager;

import java.io.File;
import java.io.OutputStream;

public class FileCreator {

    private FileManager fileManager;

    public FileCreator(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void createFileImpl(byte[] data, String pathname) {
        FileImpl file = new FileImpl(data, pathname);
        fileManager.setNewFile(file);
    }

    public void createCompressedFile(OutputStream outputStream, String pathname) {
        CompressedFile file = new CompressedFile(pathname, outputStream);
        fileManager.setNewCompressedFile(file);
    }

    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

package domain.fileManager;

import java.io.File;
import java.io.OutputStream;

public class FileCreator {

    private FileManager fileManager;

    public FileCreator(FileManager _fileManager) {
        fileManager = _fileManager;
    }

    public void createFileImpl(byte[] data, String pathname) {
        FileImpl file = new FileImpl(data, pathname);
        fileManager.setNewFile(file);
    }

    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

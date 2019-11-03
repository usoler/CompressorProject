package domain.fileManager;

public class FileCreator {

    private FileManager fileManager;

    public FileCreator(FileManager _fileManager)
    {
        fileManager = _fileManager;
    }

    public void createFileImpl(String data, String pathname)
    {
        FileImpl file = new FileImpl(data,pathname);
        fileManager.setNewFile(file);
    }
}

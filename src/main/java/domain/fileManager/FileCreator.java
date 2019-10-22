package domain.fileManager;

public class FileCreator {

    public static void createFileImpl(String data, String pathname, FileManager fileManager)
    {
        FileImpl file = new FileImpl(data,pathname);
        fileManager.setNewFile(file);
    }
}

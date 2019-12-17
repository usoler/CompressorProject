package domain.fileManager;

import domain.CompressedFile;
import domain.DecompressedFile;
import domain.Folder;
import domain.IFile;

import java.io.File;

public class FileCreator {

    private FileManager fileManager;

    public FileCreator(FileManager fileManager) {
        this.fileManager = fileManager;
    }


    public void createCompressedFile(byte[] data, String pathname, IFile folder, String name, int size, String format)
    {

        IFile file = new CompressedFile(data, pathname,name,size,format);
        if (folder != null)
        {
            folder.addFile(file);
        }
        fileManager.setNewFile(file);
    }
    public void createDecompressedFile(byte[] data, String pathname, IFile folder,  String name, int size, String format)
    {
        IFile file = new DecompressedFile(data, pathname,name,size,format);
        if (folder != null)
        {
            folder.addFile(file);
        }
        fileManager.setNewFile(file);
    }

    Folder createFolder(File file, String folderPathname)
    {
        String folderName = file.getName();
        Folder folder = new Folder(folderName,"folder", folderPathname);
        fileManager.setNewFile(folder);
        return folder;
    }

    public void createWorkingFolder(String path) {
        new File(path + "/OUTPUT").mkdir();
    }
}

package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CarpetaTest {
    private Carpeta folder;
    private static final String name = "name";
    private static final String format = "txt";

    @Before
    public void setUp() {
        folder = new Carpeta(name, format);
    }

    @Test
    public void verify_setFiles_updateSize_whenPassFiles() {
        ArrayList<Fichero> files = new ArrayList<>();
        int expected = 0;
        for (int i = 0; i < 5; i++) {
            expected += i;
            files.add(new Comprimido(name, format, i, folder));
        }
        folder.setFiles(files);
        Assert.assertEquals(expected, folder.getSize());
    }

    @Test
    public void verify_setFolders_updateSize_whenPassFolders() {
        ArrayList<Carpeta> folders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            folders.add(new Carpeta(name, format));
        }
        int expected = 0;
        folder.setFolders(folders);
        Assert.assertEquals(expected, folder.getSize());
    }

    @Test
    public void verify_addFile_setThisFolderAsFileFolder() {
        Fichero file = new Descomprimido(name, format, 0, null);
        folder.addFile(file);
        Assert.assertSame(folder, file.getFolder());
    }

}
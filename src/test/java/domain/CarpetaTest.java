package domain;

import org.junit.After;
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
        System.out.println("STARTING CARPETA TEST");
        folder = new Carpeta(name, format);
    }

    @Test
    public void verify_setFiles_updateSize_whenPassFiles() {
        System.out.println("TESTING VERIFY_SETFILES_UPDATESIZE_WHENPASSFILES");
        ArrayList<Fichero> files = new ArrayList<>();
        int expected = 0;
        for (int i = 0; i < 5; i++) {
            expected += i;
            files.add(new FicheroStub(name, format, i, folder));
        }
        folder.setFiles(files);
        Assert.assertEquals(expected, folder.getSize());
        System.out.println("ENDED TEST");

    }

    @Test
    public void verify_setFolders_updateSize_whenPassFolders() {
        System.out.println("TESTING VERIFY_SETFOLDERS_UPDATESIZE_WHENPASSFOLDERS");
        ArrayList<Carpeta> folders = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            folders.add(new Carpeta(name, format));
        }
        int expected = 0;
        folder.setFolders(folders);
        Assert.assertEquals(expected, folder.getSize());
        System.out.println("ENDED TEST");

    }


    @Test
    public void verify_addFile_setThisFolderAsFileFolder() {
        System.out.println("TESTING VERIFY_ADDFILE_SETTHISFOLDERASFILEFOLDER");
        Fichero file = new FicheroStub(name, format, 0, folder);
        folder.addFile(file);
        Assert.assertSame(folder, file.getFolder());
        System.out.println("ENDED TEST");

    }


    @Test
    public void verify_adding_and_getting_File_returns_FileValuesCorrectly(){
        System.out.println("TESTING VERIFY_ADDING_AND_GETTING_FILE_RETURN_FILEVALUESCORRECTLY");
        Fichero file = new FicheroStub(name,format,0,null);
        folder.addFile(file);
        ArrayList<Fichero> files = folder.getFiles();
        Fichero fileTest = files.get(0);
        String response = fileTest.toString();
        Assert.assertEquals("Fichero{name='example', format='format', size=2 , folder=}", response);
        System.out.println("ENDED TEST");


    }


    @After
    public void end()
    {
        System.out.println("CARPETA TEST ENDED");
    }


}
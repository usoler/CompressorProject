package domain;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CarpetaTest {
    private Carpeta folder;
    private static final String name = "name";
    private static final String format = "txt";


    @BeforeClass
    public static void startUp(){
        System.out.println("STARTING CARPETA TEST");
        System.out.println("STUB CLASS IS IMPLEMENTED IN THIS TEST SUITE");
        System.out.println("STUB CLASS IS FICHEROSTUB");
    }


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
            files.add(new FicheroStub(name, format, i, folder));
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
        Fichero file = new FicheroStub(name, format, 0, folder);
        folder.addFile(file);
        Assert.assertSame(folder, file.getFolder());

    }


    @Test
    public void verify_adding_and_getting_File_returns_FileValuesCorrectly(){
        Fichero file = new FicheroStub(name,format,0,null);
        folder.addFile(file);
        ArrayList<Fichero> files = folder.getFiles();
        Fichero fileTest = files.get(0);
        String response = fileTest.toString();
        Assert.assertEquals("Fichero{name='example', format='format', size=2 , folder=}", response);


    }


    @AfterClass
    public static void end()
    {
        System.out.println("CARPETA TEST ENDED");
    }


}
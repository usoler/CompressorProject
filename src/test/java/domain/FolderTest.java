package domain;

import org.junit.*;

import java.util.ArrayList;

public class FolderTest {
    private Folder folder;
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

        folder = new Folder(name, format);
    }
/*
    @Test
    public void verify_setFiles_updateSize_whenPassFiles() {
        ArrayList<IFile> files = new ArrayList<>();
        int expected = 0;
        for (int i = 0; i < 5; i++) {
            expected += i;
            files.add(new FileStub(name, format, i, folder));
        }
        folder.setFiles(files);
        Assert.assertEquals(expected, folder.getSize());

    }
    */

/*

    @Test
    public void verify_addFile_setThisFolderAsFileFolder() {
        File ifile = new FileStub(name, format, 0, folder);
        folder.addFile(file);
        Assert.assertSame(folder, file.getFolder());

    }


    @Test
    public void verify_adding_and_getting_File_returns_FileValuesCorrectly(){
        File file = new FileStub(name,format,0,null);
        folder.addFile(file);
        ArrayList<IFile> files = folder.getFiles();
        File fileTest = files.get(0);
        String response = fileTest.toString();
        Assert.assertEquals("Fichero{name='example', format='format', size=2 , folder=}", response);


    }



 */

    @AfterClass
    public static void end()
    {
        System.out.println("CARPETA TEST ENDED");
    }


}
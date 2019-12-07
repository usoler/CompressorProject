package domain;

import org.junit.*;

public class FileTest {
    private File file;
    private static final String name = "example";
    private static final String format = "txt";
    private static int size = 1;

    /*
    @BeforeClass
    public static void startUp(){
        System.out.println("STARTING FICHERO TEST");
    }

    @Before
    public void setUp() {
        file = new DecompressedFile(name, format, size, null);


    }

    @Test
    public void verify_toString_returnsFolderNameEmpty_whenFolderIsNull() {
        String response = file.toString();
        Assert.assertEquals("Fichero{name='example', format='txt', size=1 , folder=}", response);

    }

    @Test
    public void verify_equals_returnsTrue_whenNotSame() {
        File file2 = new DecompressedFile(new String(name), new String(format), size, null);
        Assert.assertEquals(file2, file);
    }

    @Test
    public void verify_equals_returnsTrue_whenFolderIsDifferent() {
        Folder folder = new Folder(name, format);
        File file2 = new DecompressedFile(new String(name), new String(format), size, folder);
        Assert.assertEquals(file2, file);
    }

    @AfterClass
    public static void end()
    {
        System.out.println("FICHERO TEST ENDED");
    }

     */
}
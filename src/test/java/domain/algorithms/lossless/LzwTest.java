package domain.algorithms.lossless;

import domain.IFile;
import data.fileManager.FileManager;
import org.junit.*;

public class LzwTest {
    private static String inputPathnameFolder = "input";
    private FileManager fileManager;
    private IFile file;
    private String emptyFile = "testn.txt";
    private String bigFile = "verybig.txt";
    private Lzw algorithm;

    @BeforeClass
    public static void startUp() {
        System.out.println("STARTING LZWTEST");
    }

    @AfterClass
    public static void end() {
        System.out.println("LZW TEST ENDED");
    }

    @Before
    public void setUp() {
        fileManager = new FileManager();
        algorithm = new Lzw();
    }

    @Test
    public void verify_encode_encode_whenPassEmptyFile() throws Exception {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder + '/' + emptyFile);
        byte[] result = algorithm.encode(file.getData());
        byte[] expected = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assert.assertArrayEquals(expected, result);
    }

    @Test(timeout = 10000)
    public void verify_encode__compressQuickly_whenPassVeryBigFile() throws Exception {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder + '/' + bigFile);
        byte[] result = algorithm.encode(file.getData());
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassEmptyFile() throws Exception {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder + '/' + emptyFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassNotEmptyFile() throws Exception {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder + '/' + bigFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }
}
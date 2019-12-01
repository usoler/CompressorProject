package domain.algorithms.lossless;

import domain.Fichero;
import presentation.fileManager.ControladorDeArchivos;
import org.junit.*;

public class LzwTest {
    private ControladorDeArchivos controladorDeArchivos;
    private static String inputPathnameFolder = "input";
    private Fichero file;
    private String emptyFile = "testn.txt";
    private String bigFile = "verybig.txt";
    private Lzw algorithm;

    @BeforeClass
    public static void startUp(){
        System.out.println("STARTING LZWTEST");
    }

    @Before
    public void setUp() {
        controladorDeArchivos = new ControladorDeArchivos();
        algorithm = new Lzw();
    }

    @Test
    public void verify_encode_encode_whenPassEmptyFile() {
        controladorDeArchivos.readFolder(inputPathnameFolder);
        file = controladorDeArchivos.getFile(inputPathnameFolder+'/'+emptyFile);
        byte[] result = algorithm.encode(file.getData());
        byte[] expected = {0,0,0,0,0,0,0,0,0,0,0,0};
        Assert.assertArrayEquals(expected, result);
    }

    @Test(timeout = 10000)
    public void verify_encode__compressQuickly_whenPassVeryBigFile() {
        controladorDeArchivos.readFolder(inputPathnameFolder);
        file = controladorDeArchivos.getFile(inputPathnameFolder+'/'+bigFile);
        byte[] result = algorithm.encode(file.getData());
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassEmptyFile() {
        controladorDeArchivos.readFolder(inputPathnameFolder);
        file = controladorDeArchivos.getFile(inputPathnameFolder+'/'+emptyFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassNotEmptyFile() {
        controladorDeArchivos.readFolder(inputPathnameFolder);
        file = controladorDeArchivos.getFile(inputPathnameFolder+'/'+bigFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }

    @AfterClass
    public static void end(){
        System.out.println("LZW TEST ENDED");
    }
}
package domain.algorithms;

import org.junit.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class AlgorithmTest {
    private Algorithm algorithm;

    @BeforeClass
    public static void startUp(){
        System.out.println("STARTING ALGORITHMTEST");
    }

    @Before
    public void setUp() {
        algorithm = new Algorithm();
        algorithm.setAlgorithmInterface(new AlgortihmImplStub());
    }

    @Test
    public void verify_encodeFile() throws IOException {
        String uncompressedFile = "aaaaa";
        byte[] result = algorithm.encodeFile(uncompressedFile.getBytes());
        byte[] expected = new byte[] {0, 1, 2, 3};
        Assert.assertArrayEquals(result, expected);
    }

    @Test
    public void verify_decodeFile() throws UnsupportedEncodingException {
        String compressedFile = "aa";
        byte[] result = algorithm.decodeFile(compressedFile.getBytes());
        byte[] expected = new byte[] {3, 2, 1, 0};
        Assert.assertArrayEquals(result, expected);
    }

    @AfterClass
    public static void end(){
        System.out.println("ALGORITHM TEST ENDED");
    }
}
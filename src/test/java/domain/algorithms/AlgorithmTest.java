package domain.algorithms;

import domain.exception.CompressorException;
import org.junit.*;

public class AlgorithmTest {
    private Algorithm algorithm;

    @BeforeClass
    public static void startUp() {
        System.out.println("STARTING ALGORITHM TEST");
    }

    @AfterClass
    public static void end() {
        System.out.println("ALGORITHM TEST ENDED");
    }

    @Before
    public void setUp() {
        algorithm = new Algorithm();
        algorithm.setAlgorithmInterface(new AlgorithmImplStub());
    }

    @Test
    public void verify_encodeFile_returnsCompressedFileAsByteArray_whenParamFileAsByteArrayIsValid() throws Exception {
        // Mock
        String uncompressedFile = "aaaaa";
        byte[] expected = new byte[]{0, 1, 2, 3};

        // Test
        byte[] result = algorithm.encodeFile(uncompressedFile.getBytes());

        Assert.assertArrayEquals(result, expected);
    }

    @Test(expected = CompressorException.class)
    public void verify_encodeFile_throwsCompressorException_whenEncodeFails() throws Exception {
        algorithm.encodeFile(null);
    }

    @Test
    public void verify_decodeFile() throws Exception {
        // Mock
        String compressedFile = "aa";
        byte[] expected = new byte[]{3, 2, 1, 0};
        // Test
        byte[] result = algorithm.decodeFile(compressedFile.getBytes());

        Assert.assertArrayEquals(result, expected);
    }
}
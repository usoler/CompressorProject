package domain.algorithms.lossless;

import domain.fileManager.FileImpl;
import domain.fileManager.FileManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LzwTest {
    private FileManager fileManager;
    private static String inputPathnameFolder = "input";
    private FileImpl file;
    private String emptyFile = "testn.txt";
    private String bigFile = "verybig.txt";
    private Lzw algorithm;

    @Before
    public void setUp() {
        fileManager = new FileManager();
        algorithm = new Lzw();
    }

    @Test
    public void verify_encode_encode_whenPassEmptyFile() {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder+'/'+emptyFile);
        byte[] result = algorithm.encode(file.getData());
        byte[] expected = {0,0,0,0,0,0,0,0,0,0,0,0};
        Assert.assertArrayEquals(expected, result);
    }

    @Test(timeout = 10000)
    public void verify_encode__compressQuickly_whenPassVeryBigFile() {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder+'/'+bigFile);
        byte[] result = algorithm.encode(file.getData());
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassEmptyFile() {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder+'/'+emptyFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }

    @Test
    public void verify_encode_encodeCorrectly_whenPassNotEmptyFile() {
        fileManager.readFolder(inputPathnameFolder);
        file = fileManager.getFile(inputPathnameFolder+'/'+bigFile);
        byte[] result = algorithm.encode(file.getData());
        result = algorithm.decode(result);
        Assert.assertArrayEquals(file.getData(), result);
    }

}
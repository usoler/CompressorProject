package domain.components;

import domain.dataObjects.CoefficientEnum;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

public class HuffmanComponentTest {

    // TODO: corner cases

    @Test
    public void verify_encodeDC_returnsDCEncoded_whenParamDCisValid() throws Exception {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        StringBuffer buffer = new StringBuffer();
        int dc = 1118;
        StringBuffer expected = new StringBuffer("11111111111010001011110");

        // Test
        StringBuffer response = huffmanComponent.encodeDC(dc, buffer);

        Assert.assertNotNull(response);
        Assert.assertTrue((expected.toString()).equals(response.toString()));
    }

    @Test
    public void verify_encodeAC_returnsDCEncoded_whenParamACisValid_AndCoefficientIsLuminance() throws Exception {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        StringBuffer buffer = new StringBuffer();
        int ac = 2;
        int numberOfZeros = 0;

        StringBuffer expected = new StringBuffer("0110");
        // 01|10

        // Test
        StringBuffer response = huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.LUMINANCE, buffer);

        Assert.assertNotNull(response);
        Assert.assertTrue((expected.toString()).equals(response.toString()));
    }

    @Test
    public void verify_encodeAC_returnsDCEncoded_whenParamACisValid_AndCoefficientIsChrominance() throws Exception {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        StringBuffer buffer = new StringBuffer();
        int ac = -2;
        int numberOfZeros = 1;

        StringBuffer expected = new StringBuffer("11100101");
        // 111001|01

        // Test
        StringBuffer response = huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.CHROMINANCE, buffer);

        Assert.assertNotNull(response);
        Assert.assertTrue((expected.toString()).equals(response.toString()));
    }

    @Test
    public void verify_getNumOfBitsOfColumn_returnsNumberColumnInTable_whenHuffmanValueIsValid() throws Exception {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        int expected = 11;

        // Test
        int response = huffmanComponent.getNumOfBitsOfColumn("111111111110");

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void verify_decodeDC_returnsDCValue_whenParamsRowAndColumnAreValid() throws Exception {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        int expected = 1118;

        // Test
        int response = huffmanComponent.decodeDC(11, Integer.parseInt("10001011110", 2));

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void verify_getPreZerosAndRowOfValueLuminance_returnsNumOfPreZerosAndRow_whenParamHuffmanCodeIsValid() {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        String huffmanCode = "11011";
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(1, 2);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueLuminance(huffmanCode);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_getPreZerosAndRowOfValueChrominance_returnsNumOfPreZerosAndRow_whenParamHuffmanCodeIsValid() {
        // Mock
        HuffmanComponent huffmanComponent = new HuffmanComponent();
        String huffmanCode = "111111110110";
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(2,4);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueChrominance(huffmanCode);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

}
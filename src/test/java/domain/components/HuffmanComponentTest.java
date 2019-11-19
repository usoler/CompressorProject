package domain.components;

import domain.dataObjects.CoefficientEnum;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

public class HuffmanComponentTest {
    private static final HuffmanComponent huffmanComponent = new HuffmanComponent();

    @Test
    public void verify_encodeDC_returnsDCEncoded_whenParamDCisValid() throws Exception {
        // Mock
        StringBuffer buffer = new StringBuffer();
        int dc = 1118;
        StringBuffer expected = new StringBuffer("11111111111010001011110");

        // Test
        StringBuffer response = huffmanComponent.encodeDC(dc, buffer);

        Assert.assertNotNull(response);
        Assert.assertTrue((expected.toString()).equals(response.toString()));
    }

    @Test
    public void verify_encodeDC_throwsIllegalArgumentException_whenParamStringBufferIsNull() {
        try {
            huffmanComponent.encodeDC(2, null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param String Buffer could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_encodeAC_returnsDCEncoded_whenParamACisValid_AndCoefficientIsLuminance()
            throws IllegalArgumentException {
        // Mock
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
    public void verify_encodeAC_throwsIllegalArgumentException_whenParamStringBufferIsNull() {
        try {
            huffmanComponent.encodeAC(2, 0, CoefficientEnum.LUMINANCE, null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param String Buffer could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_encodeAC_throwsIllegalArgumentException_whenRowIsInvalid_AndCoefficientIsLuminance()
            throws IllegalArgumentException {
        // Mock
        StringBuffer buffer = new StringBuffer();
        int ac = 8192;
        int numberOfZeros = 0;

        // Test
        try {
            huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.LUMINANCE, buffer);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Row param don't match", ex.getMessage());
        }
    }

    @Test
    public void verify_encodeAC_throwsIllegalArgumentException_whenNumOfPreZerosIsInvalid_AndCoefficientIsLuminance()
            throws IllegalArgumentException {
        // Mock
        StringBuffer buffer = new StringBuffer();
        int ac = -2;
        int numberOfZeros = 16;

        // Test
        try {
            huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.LUMINANCE, buffer);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Num of pre zeros param don't match", ex.getMessage());
        }
    }

    @Test
    public void verify_encodeAC_returnsDCEncoded_whenParamACisValid_AndCoefficientIsChrominance()
            throws IllegalArgumentException {
        // Mock
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
    public void verify_encodeAC_throwsIllegalArgumentException_whenRowIsInvalid_AndCoefficientIsChrominance()
            throws IllegalArgumentException {
        // Mock
        StringBuffer buffer = new StringBuffer();
        int ac = 8192;
        int numberOfZeros = 0;

        // Test
        try {
            huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.CHROMINANCE, buffer);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Row param don't match", ex.getMessage());
        }
    }

    @Test
    public void verify_encodeAC_throwsIllegalArgumentException_whenNumOfPreZerosIsInvalid_AndCoefficientIsChrominance()
            throws IllegalArgumentException {
        // Mock
        StringBuffer buffer = new StringBuffer();
        int ac = -2;
        int numberOfZeros = 16;

        // Test
        try {
            huffmanComponent.encodeAC(ac, numberOfZeros, CoefficientEnum.CHROMINANCE, buffer);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Num of pre zeros param don't match", ex.getMessage());
        }
    }

    @Test
    public void verify_getNumOfBitsOfColumn_returnsNumberColumnInTable_whenHuffmanValueIsValid() {
        // Mock
        int expected = 11;

        // Test
        int response = huffmanComponent.getNumOfBitsOfColumn("111111111110");

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void verify_getNumOfBitsOfColumn_returnsDefaultValue_whenHuffmanValueIsInvalid() {
        // Mock
        int expected = -1;

        // Test
        int response = huffmanComponent.getNumOfBitsOfColumn("11");

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void verify_decodeCoefficient_returnsDCValue_whenParamsRowAndColumnAreValid() throws Exception {
        // Mock
        int expected = 1118;

        // Test
        int response = huffmanComponent.decodeCoefficient(11, Integer.parseInt("10001011110", 2));

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void verify_decodeCoefficient_throwsIllegalArgumentException_whenRowIsInvalid() throws Exception {
        try {
            huffmanComponent.decodeCoefficient(20, Integer.parseInt("10001011110", 2));
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Row param don't match", ex.getMessage());
        }
    }

    @Test
    public void verify_getPreZerosAndRowOfValueLuminance_returnsNumOfPreZerosAndRow_whenParamHuffmanCodeIsValid() {
        // Mock
        String huffmanCode = "11011";
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(1, 2);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueLuminance(huffmanCode);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_getPreZerosAndRowOfValueLuminance_returnsDefaultValue_whenParamHuffmanCodeIsInvalid() {
        // Mock
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(-1, -1);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueLuminance("0");

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_getPreZerosAndRowOfValueChrominance_returnsNumOfPreZerosAndRow_whenParamHuffmanCodeIsValid() {
        // Mock
        String huffmanCode = "111111110110";
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(2, 4);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueChrominance(huffmanCode);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_getPreZerosAndRowOfValueChrominance_returnsDefaultValue_whenParamHuffmanCodeIsInvalid() {
        // Mock
        Pair<Integer, Integer> expected = new Pair<Integer, Integer>(-1, -1);

        // Test
        Pair<Integer, Integer> response = huffmanComponent.getPreZerosAndRowOfValueChrominance("0");

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }
}
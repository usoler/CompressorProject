package domain.utils;

import domain.dataObjects.CoefficientEnum;
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
        // 111111111110|01110100010

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

}
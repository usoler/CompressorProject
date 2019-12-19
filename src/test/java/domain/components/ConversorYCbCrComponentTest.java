package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.junit.Assert;
import org.junit.Test;

public class ConversorYCbCrComponentTest {

    private static final int NUM_OF_ROWS = 2;
    private static final int NUM_OF_COLUMNS = 3;

    private static final ConversorYCbCrComponent conversor = new ConversorYCbCrComponent();

    @Test
    public void verify_convertFromRGB_returnsYCbCrMatrix_whenRgbMatrixIsConverted() throws CompressorException {
        // Mock
        Matrix<Pixel> rgbMatrix = mockValidRgbMatrix();
        Matrix<Pixel> expected = mockValidYCbCrMatrix();

        // Test
        Matrix<Pixel> response = conversor.convertFromRGB(rgbMatrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_convertFromRGB_throwsCompressorException_whenParamRgbMatrixIsNull() {
        try {
            conversor.convertFromRGB(null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.CONVERT_RGB_TO_YCBCR_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_convertFromRGB_throwsCompressorException_whenPixelIsNull_fromParamRgbMatrix() {
        // Mock
        Matrix<Pixel> rgbMatrix = mockInvalidMatrix();

        // Test
        try {
            conversor.convertFromRGB(rgbMatrix);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.CONVERT_RGB_TO_YCBCR_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_convertToRGB_throwsCompressorException_whenParamYCbCrMatrixIsNull() {
        try {
            conversor.convertToRGB(null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.CONVERT_YCBCR_TO_RGB_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_convertToRGB_throwsCompressorException_whenPixelIsNull_fromParamRgbMatrix() {
        // Mock
        Matrix<Pixel> yCbCrMatrix = mockInvalidMatrix();

        // Test
        try {
            conversor.convertToRGB(yCbCrMatrix);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.CONVERT_YCBCR_TO_RGB_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    private Matrix<Pixel> mockValidYCbCrMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);

        pixels.setElementAt(new Pixel(76.245f, 84.972f, 255f), 0, 0);
        pixels.setElementAt(new Pixel(149.685f, 43.528f, 21.235f), 0, 1);
        pixels.setElementAt(new Pixel(29.07f, 255f, 107.265f), 0, 2);
        pixels.setElementAt(new Pixel(225.93f, 0.5f, 148.735f), 1, 0);
        pixels.setElementAt(new Pixel(255f, 128f, 128f), 1, 1);
        pixels.setElementAt(new Pixel(0f, 128f, 128f), 1, 2);

        return pixels;
    }

    private Matrix<Pixel> mockValidRgbMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);

        pixels.setElementAt(new Pixel(255, 0, 0), 0, 0);
        pixels.setElementAt(new Pixel(0, 255, 0), 0, 1);
        pixels.setElementAt(new Pixel(0, 0, 255), 0, 2);
        pixels.setElementAt(new Pixel(255, 255, 0), 1, 0);
        pixels.setElementAt(new Pixel(255, 255, 255), 1, 1);
        pixels.setElementAt(new Pixel(0, 0, 0), 1, 2);

        return pixels;
    }

    private Matrix<Pixel> mockInvalidMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);
        pixels.setElementAt(null, 0, 0);

        return pixels;
    }
}
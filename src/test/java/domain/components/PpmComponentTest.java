package domain.components;

import domain.dataObjects.Pixel;
import domain.dataObjects.PpmResponse;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.junit.Assert;
import org.junit.Test;

public class PpmComponentTest {
    private static final PpmComponent ppmComponent = new PpmComponent();
    private static final String pathTest = "input/prueba.ppm";
    private static final int NUM_OF_ROWS = 16;
    private static final int NUM_OF_COLUMNS = 16;
    private static final String STRING_OF_DATA = "P3\n" +
            "16 16\n" +
            "255\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0\n" +
            "255 0 0 0 0 0 0 0 0 0 0 0";

    @Test
    public void verify_readPpmFile_returnsMatrixOfPixels_whenIsRead() throws Exception {
        // Mocks
        Matrix<Pixel> expectedMatrix = mockValidPixelMatrix();

        // Test
        PpmResponse response = ppmComponent.readPpmFile(STRING_OF_DATA.getBytes());

        Assert.assertNotNull(response);
        Assert.assertEquals(16, response.getWidth());
        Assert.assertEquals(16, response.getHeight());
        Assert.assertTrue(expectedMatrix.equals(response.getMatrix()));
    }

    @Test
    public void verify_readPpmFile_throwsCompressorException_whenParamDataIsNull() {
        try {
            ppmComponent.readPpmFile(null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.READ_PPM_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_writePpmFile_returnsStringOfMatrixData_whenRgbMatrixIsValid() throws CompressorException {
        // Mock
        Matrix<Pixel> rgbMatrix = mockValidPixelMatrix();

        // Test
        byte[] response = ppmComponent.writePpmFile(3, 16, 16, rgbMatrix);

        Assert.assertNotNull(response);
        Assert.assertEquals(STRING_OF_DATA, new String(response));
    }

    @Test
    public void verify_writePpmFile_throwsCompressorException_whenParamRgbMatrixIsNull() {
        try {
            ppmComponent.writePpmFile(3, 16, 16, null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.WRITE_PPM_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_writePpmFile_throwsCompressorException_whenPixelFromParamRgbMatrixIsNull() {
        Matrix<Pixel> rgbMatrix = new Matrix<Pixel>(2, 2, new Pixel[2][2]);

        try {
            ppmComponent.writePpmFile(3, 2, 2, rgbMatrix);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.WRITE_PPM_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    private Matrix<Pixel> mockValidPixelMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);

        pixels.setElementAt(new Pixel(255, 0, 0), 0, 0);
        pixels.setElementAt(new Pixel(0, 0, 0), 0, 1);
        pixels.setElementAt(new Pixel(0, 0, 0), 0, 2);
        pixels.setElementAt(new Pixel(0, 0, 0), 0, 3);

        int i = 0;
        int j = 4;
        for (int k = 0; k < 63; ++k) {
            pixels.setElementAt(new Pixel(255, 0, 0), i, j);
            pixels.setElementAt(new Pixel(0, 0, 0), i, j + 1);
            pixels.setElementAt(new Pixel(0, 0, 0), i, j + 2);
            pixels.setElementAt(new Pixel(0, 0, 0), i, j + 3);

            if (j + 3 + 4 < 16) {
                j += 4;
            } else {
                ++i;
                j = 0;
            }
        }


        return pixels;
    }
}
package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class PpmComponentTest {
    private static final PpmComponent ppmComponent = new PpmComponent();
    private static final String pathTest = "input/prueba.ppm";
    private static final int NUM_OF_ROWS = 2;
    private static final int NUM_OF_COLUMNS = 3;
    private static final String STRING_OF_DATA = "P3\n3 2\n255\n255 0 0 0 255 0 0 0 255\n255 255 0 255 255 255 0 0 0";


    @Test
    public void verify_readPpmFile_returnsMatrixOfPixels_whenIsRead() throws Exception {
        // Mocks
        Matrix<Pixel> expected = mockValidPixelMatrix();

        // Test
        Matrix<Pixel> response = ppmComponent.readPpmFile(STRING_OF_DATA);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_readPpmFile_throwsIllegalArgumentException_whenParamDataIsNull() {
        try {
            ppmComponent.readPpmFile(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param data could not be null, empty or blank", ex.getMessage());
        }
    }

    @Test
    public void verify_readPpmFile_throwsIllegalArgumentException_whenParamDataIsEmpty() {
        try {
            ppmComponent.readPpmFile("");
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param data could not be null, empty or blank", ex.getMessage());
        }
    }

    @Test
    public void verify_readPpmFile_throwsIllegalArgumentException_whenParamDataIsBlank() {
        try {
            ppmComponent.readPpmFile("       ");
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param data could not be null, empty or blank", ex.getMessage());
        }
    }

    @Test
    public void verify_writePpmFile_returnsStringOfMatrixData_whenRgbMatrixIsValid() {
        // Mock
        Matrix<Pixel> rgbMatrix = mockValidPixelMatrix();

        // Test
        String response = ppmComponent.writePpmFile(2, 3, rgbMatrix);

        Assert.assertNotNull(response);
        Assert.assertEquals(STRING_OF_DATA, response);
    }

    @Test
    public void verify_writePpmFile_throwsIllegalArgumentException_whenParamRgbMatrixIsNull() {
        try {
            ppmComponent.writePpmFile(2, 2, null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param RGB Matrix could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_writePpmFile_throwsIllegalArgumentException_whenPixelFromParamRgbMatrixIsNull() {
        Matrix<Pixel> rgbMatrix = new Matrix<Pixel>(2,2, new Pixel[2][2]);

        try {
            ppmComponent.writePpmFile(2, 2, rgbMatrix);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Pixel from param RGB Matrix at position (0,0) could not be " +
                    "null, empty or blank", ex.getMessage());
        }
    }

    private Matrix<Pixel> mockValidPixelMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);

        pixels.setElementAt(new Pixel(255, 0, 0), 0, 0);
        pixels.setElementAt(new Pixel(0, 255, 0), 0, 1);
        pixels.setElementAt(new Pixel(0, 0, 255), 0, 2);
        pixels.setElementAt(new Pixel(255, 255, 0), 1, 0);
        pixels.setElementAt(new Pixel(255, 255, 255), 1, 1);
        pixels.setElementAt(new Pixel(0, 0, 0), 1, 2);

        return pixels;
    }
}
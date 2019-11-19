package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class DownsamplingComponentTest {
    private static final int MATRIX_16x16_NUM_OF_ROW_AND_COLUMN = 16;
    private static final int MATRIX_8x8_NUM_OF_ROW_AND_COLUMN = 8;

    private static final DownsamplingComponent downsamplingComponent = new DownsamplingComponent();

    @Test
    public void verify_downsampling_returnsMacroBlockYCbCr_whenIsSuccessful() {
        // Mock
        Matrix<Pixel> yCbCrMatrix = mockYCbCrMatrix();
        MacroBlockYCbCr expected = mockMacroBlockYCbCr();

        // Test
        MacroBlockYCbCr response = downsamplingComponent.downsampling(yCbCrMatrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_downsampling_throwsIllegalArgumentException_whenParamYCbCrMatrixIsNull() {
        try {
            downsamplingComponent.downsampling(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param YCbCr Matrix could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_downsampling_throwsIllegalArgumentException_whenPixelIsNull_fromParamYCbCrMatrix() {
        // Mock
        Matrix<Pixel> yCbCrMatrix = mockInvalidMatrix();

        // Test
        try {
            downsamplingComponent.downsampling(yCbCrMatrix);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Pixel from param YCbCr Matrix at position (0,0) could not be null",
                    ex.getMessage());
        }
    }

    // FIXME
 /*   @Test
    public void verify_upsampling_returnsYCbCrMatrix_whenIsSuccessful() {
        // Mock
        MacroBlockYCbCr macroBlockYCbCr = mockMacroBlockYCbCr();
        Matrix<Pixel> expected = mockYCbCrMatrix();

        // Test
        Matrix<Pixel> response = downsamplingComponent.upsampling(macroBlockYCbCr);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }*/

    @Test
    public void verify_upsampling_throwsIllegalArgumentException_whenParamMacroBlockYCbCrIsNull() {
        try {
            downsamplingComponent.upsampling(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param MacroBlock YCbCr could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_upsampling_throwsIllegalArgumentException_whenListOfYBlocksIsNull_fromParamMacroBlockYCbCr() {
        // Mock
        MacroBlockYCbCr macroBlock = new MacroBlockYCbCr(null, null, null);

        // Test
        try {
            downsamplingComponent.upsampling(macroBlock);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("List of Y block from param MacroBlock YCbCr could not be null",
                    ex.getMessage());
        }
    }

    @Test
    public void verify_upsampling_throwsIllegalArgumentException_whenListOfYBlocksIsLessThan4_fromParamMacroBlockYCbCr() {
        // Mock
        MacroBlockYCbCr macroBlock = new MacroBlockYCbCr();

        // Test
        try {
            downsamplingComponent.upsampling(macroBlock);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param MacroBlock YCbCr should have 4 Y blocks",
                    ex.getMessage());
        }
    }

    @Test
    public void verify_upsampling_throwsIllegalArgumentException_whenCbBlockIsNull_fromParamMacroBlockYCbCr() {
        // Mock
        MacroBlockYCbCr macroBlock = new MacroBlockYCbCr();

        for (int i = 0; i < 4; ++i) {
            macroBlock.addYBlock(new Matrix<Float>(8, 8, new Float[8][8]));
        }

        // Test
        try {
            downsamplingComponent.upsampling(macroBlock);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Cb block from param MacroBlock YCbCr could not be null",
                    ex.getMessage());
        }
    }

    @Test
    public void verify_upsampling_throwsIllegalArgumentException_whenCrBlockIsNull_fromParamMacroBlockYCbCr() {
        // Mock
        MacroBlockYCbCr macroBlock = new MacroBlockYCbCr();

        for (int i = 0; i < 4; ++i) {
            macroBlock.addYBlock(new Matrix<Float>(8, 8, new Float[8][8]));
        }

        macroBlock.setCbBlock(new Matrix<Float>(8, 8, new Float[8][8]));

        // Test
        try {
            downsamplingComponent.upsampling(macroBlock);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Cr block from param MacroBlock YCbCr could not be null",
                    ex.getMessage());
        }
    }

    private Matrix<Pixel> mockYCbCrMatrix() {
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(MATRIX_16x16_NUM_OF_ROW_AND_COLUMN,
                MATRIX_16x16_NUM_OF_ROW_AND_COLUMN,
                new Pixel[MATRIX_16x16_NUM_OF_ROW_AND_COLUMN][MATRIX_16x16_NUM_OF_ROW_AND_COLUMN]);

        for (int i = 0; i < MATRIX_16x16_NUM_OF_ROW_AND_COLUMN; ++i) {
            for (int j = 0; j < MATRIX_16x16_NUM_OF_ROW_AND_COLUMN; j += 2) {
                yCbCrMatrix.setElementAt(new Pixel(76.245f, 84.972f, 255f), i, j);
                yCbCrMatrix.setElementAt(new Pixel(149.685f, 43.528f, 21.235f), i, j + 1);
            }
        }

        return yCbCrMatrix;
    }

    private MacroBlockYCbCr mockMacroBlockYCbCr() {
        MacroBlockYCbCr macroBlockYCbCr = new MacroBlockYCbCr();
        float[] ys = new float[]{76.245f, 149.685f};

        // Y component
        for (int k = 0; k < 4; ++k) {
            Matrix<Float> yMatrix = new Matrix<Float>(MATRIX_8x8_NUM_OF_ROW_AND_COLUMN,
                    MATRIX_8x8_NUM_OF_ROW_AND_COLUMN, new Float[8][8]);
            for (int i = 0; i < 8; ++i) {
                int t = 0;
                for (int j = 0; j < 8; ++j) {
                    yMatrix.setElementAt(ys[t], i, j);

                    if (t == 0) {
                        t = 1;
                    } else {
                        t = 0;
                    }
                }
            }
            macroBlockYCbCr.addYBlock(yMatrix);
        }

        // Cb component
        Matrix<Float> cbMatrix = new Matrix<Float>(MATRIX_8x8_NUM_OF_ROW_AND_COLUMN, MATRIX_8x8_NUM_OF_ROW_AND_COLUMN,
                new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                cbMatrix.setElementAt(84.972f, i, j);
            }
        }

        macroBlockYCbCr.setCbBlock(cbMatrix);

        // Cr component
        Matrix<Float> crMatrix = new Matrix<Float>(MATRIX_8x8_NUM_OF_ROW_AND_COLUMN, MATRIX_8x8_NUM_OF_ROW_AND_COLUMN,
                new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                crMatrix.setElementAt(255f, i, j);
            }
        }

        macroBlockYCbCr.setCrBlock(crMatrix);

        return macroBlockYCbCr;
    }

    private Matrix<Pixel> mockInvalidMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(MATRIX_8x8_NUM_OF_ROW_AND_COLUMN, MATRIX_8x8_NUM_OF_ROW_AND_COLUMN,
                new Pixel[MATRIX_8x8_NUM_OF_ROW_AND_COLUMN][MATRIX_8x8_NUM_OF_ROW_AND_COLUMN]);
        pixels.setElementAt(null, 0, 0);

        return pixels;
    }

}
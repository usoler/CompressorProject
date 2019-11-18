package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class DownsamplingComponentTest {
    private static final int NUM_OF_ROW_AND_COLUMN = 16;

    // TODO: corner cases

    @Test
    public void verify_downsampling_returnsMacroBlockYCbCr_whenIsSuccessful() {
        // Mock
        DownsamplingComponent downsamplingComponent = new DownsamplingComponent();

        Matrix<Pixel> yCbCrMatrix = mockYCbCrMatrix();
        MacroBlockYCbCr expected = mockMacroBlockYCbCr();

        // Test
        MacroBlockYCbCr response = downsamplingComponent.downsampling(yCbCrMatrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    private Matrix<Pixel> mockYCbCrMatrix() {
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(NUM_OF_ROW_AND_COLUMN, NUM_OF_ROW_AND_COLUMN,
                new Pixel[NUM_OF_ROW_AND_COLUMN][NUM_OF_ROW_AND_COLUMN]);

        for (int i = 0; i < NUM_OF_ROW_AND_COLUMN; ++i) {
            for (int j = 0; j < NUM_OF_ROW_AND_COLUMN; j += 2) {
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
            Matrix<Float> yMatrix = new Matrix<Float>(8, 8, new Float[8][8]);
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
        Matrix<Float> cbMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                cbMatrix.setElementAt(84.972f, i, j);
            }
        }

        macroBlockYCbCr.setCbBlock(cbMatrix);

        // Cr component
        Matrix<Float> crMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                crMatrix.setElementAt(255f, i, j);
            }
        }

        macroBlockYCbCr.setCrBlock(crMatrix);

        return macroBlockYCbCr;
    }

}
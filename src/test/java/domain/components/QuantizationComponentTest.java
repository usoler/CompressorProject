package domain.components;

import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class QuantizationComponentTest {
    private static final QuantizationComponent quantizationComponent = new QuantizationComponent();

    @Test
    public void verify_quantizeMatrix_returnsQuantizedDCTMatrix_whenMatrixParamIsValid() {
        // Mock
        Matrix<Float> dctMatrix = mockDCTMatrix();
        Matrix<Float> expected = mockQuantizedMatrix();

        // Test
        Matrix<Float> response = quantizationComponent.quantizeMatrix(dctMatrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_quantizeMatrix_throwsIllegalArgumentException_whenParamDctMatrixIsNull() {
        try {
            quantizationComponent.quantizeMatrix(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("DCT Matrix could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_desquantizeMatrix_returnsDesquantizedMatrix_whenParamQuantizedMatrixIsValid() {
        // Mock
        Matrix<Integer> quantizedMatrix = mockIntegerQuantizedMatrix();
        Matrix<Integer> expected = mockIntegerDCTMatrix();

        // Test
        Matrix<Integer> response = quantizationComponent.dequantizeMatrix(quantizedMatrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_desquantizeMatrix_throwsIllegalArgumentException_whenParamQuantizedMatrixIsNull() {
        try {
            quantizationComponent.dequantizeMatrix(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Quantized Matrix could not be null", ex.getMessage());
        }
    }

    private Matrix<Float> mockDCTMatrix() {
        Matrix<Float> dctMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        dctMatrix.setElementAt(-415.37f, 0, 0);
        dctMatrix.setElementAt(-30.19f, 0, 1);
        dctMatrix.setElementAt(-61.20f, 0, 2);
        dctMatrix.setElementAt(27.24f, 0, 3);
        dctMatrix.setElementAt(56.12f, 0, 4);
        dctMatrix.setElementAt(-20.10f, 0, 5);
        dctMatrix.setElementAt(-2.39f, 0, 6);
        dctMatrix.setElementAt(0.46f, 0, 7);

        dctMatrix.setElementAt(4.47f, 1, 0);
        dctMatrix.setElementAt(-21.86f, 1, 1);
        dctMatrix.setElementAt(-60.76f, 1, 2);
        dctMatrix.setElementAt(10.25f, 1, 3);
        dctMatrix.setElementAt(13.15f, 1, 4);
        dctMatrix.setElementAt(-7.09f, 1, 5);
        dctMatrix.setElementAt(-8.54f, 1, 6);
        dctMatrix.setElementAt(4.88f, 1, 7);

        dctMatrix.setElementAt(-46.83f, 2, 0);
        dctMatrix.setElementAt(7.37f, 2, 1);
        dctMatrix.setElementAt(77.13f, 2, 2);
        dctMatrix.setElementAt(-24.56f, 2, 3);
        dctMatrix.setElementAt(-28.91f, 2, 4);
        dctMatrix.setElementAt(9.93f, 2, 5);
        dctMatrix.setElementAt(5.42f, 2, 6);
        dctMatrix.setElementAt(-5.65f, 2, 7);

        dctMatrix.setElementAt(-48.53f, 3, 0);
        dctMatrix.setElementAt(12.07f, 3, 1);
        dctMatrix.setElementAt(34.10f, 3, 2);
        dctMatrix.setElementAt(-14.76f, 3, 3);
        dctMatrix.setElementAt(-10.24f, 3, 4);
        dctMatrix.setElementAt(6.30f, 3, 5);
        dctMatrix.setElementAt(1.83f, 3, 6);
        dctMatrix.setElementAt(1.95f, 3, 7);

        dctMatrix.setElementAt(12.12f, 4, 0);
        dctMatrix.setElementAt(-6.55f, 4, 1);
        dctMatrix.setElementAt(-13.20f, 4, 2);
        dctMatrix.setElementAt(-3.95f, 4, 3);
        dctMatrix.setElementAt(-1.87f, 4, 4);
        dctMatrix.setElementAt(1.75f, 4, 5);
        dctMatrix.setElementAt(-2.79f, 4, 6);
        dctMatrix.setElementAt(3.14f, 4, 7);

        dctMatrix.setElementAt(-7.73f, 5, 0);
        dctMatrix.setElementAt(2.91f, 5, 1);
        dctMatrix.setElementAt(2.38f, 5, 2);
        dctMatrix.setElementAt(-5.94f, 5, 3);
        dctMatrix.setElementAt(-2.38f, 5, 4);
        dctMatrix.setElementAt(0.94f, 5, 5);
        dctMatrix.setElementAt(4.30f, 5, 6);
        dctMatrix.setElementAt(1.85f, 5, 7);

        dctMatrix.setElementAt(-1.03f, 6, 0);
        dctMatrix.setElementAt(0.18f, 6, 1);
        dctMatrix.setElementAt(0.42f, 6, 2);
        dctMatrix.setElementAt(-2.42f, 6, 3);
        dctMatrix.setElementAt(-0.88f, 6, 4);
        dctMatrix.setElementAt(-3.02f, 6, 5);
        dctMatrix.setElementAt(4.12f, 6, 6);
        dctMatrix.setElementAt(-0.66f, 6, 7);

        dctMatrix.setElementAt(-0.17f, 7, 0);
        dctMatrix.setElementAt(0.14f, 7, 1);
        dctMatrix.setElementAt(-1.07f, 7, 2);
        dctMatrix.setElementAt(-4.19f, 7, 3);
        dctMatrix.setElementAt(-1.17f, 7, 4);
        dctMatrix.setElementAt(-0.10f, 7, 5);
        dctMatrix.setElementAt(0.50f, 7, 6);
        dctMatrix.setElementAt(1.68f, 7, 7);

        return dctMatrix;
    }

    private Matrix<Integer> mockIntegerDCTMatrix() {
        Matrix<Integer> dctMatrix = new Matrix<Integer>(8, 8, new Integer[8][8]);

        dctMatrix.setElementAt(-416, 0, 0);
        dctMatrix.setElementAt(-33, 0, 1);
        dctMatrix.setElementAt(-60, 0, 2);
        dctMatrix.setElementAt(32, 0, 3);
        dctMatrix.setElementAt(48, 0, 4);
        dctMatrix.setElementAt(-40, 0, 5);
        dctMatrix.setElementAt(0, 0, 6);
        dctMatrix.setElementAt(0, 0, 7);

        dctMatrix.setElementAt(0, 1, 0);
        dctMatrix.setElementAt(-24, 1, 1);
        dctMatrix.setElementAt(-56, 1, 2);
        dctMatrix.setElementAt(19, 1, 3);
        dctMatrix.setElementAt(26, 1, 4);
        dctMatrix.setElementAt(0, 1, 5);
        dctMatrix.setElementAt(0, 1, 6);
        dctMatrix.setElementAt(0, 1, 7);

        dctMatrix.setElementAt(-42, 2, 0);
        dctMatrix.setElementAt(13, 2, 1);
        dctMatrix.setElementAt(80, 2, 2);
        dctMatrix.setElementAt(-24, 2, 3);
        dctMatrix.setElementAt(-40, 2, 4);
        dctMatrix.setElementAt(0, 2, 5);
        dctMatrix.setElementAt(0, 2, 6);
        dctMatrix.setElementAt(0, 2, 7);

        dctMatrix.setElementAt(-42, 3, 0);
        dctMatrix.setElementAt(17, 3, 1);
        dctMatrix.setElementAt(44, 3, 2);
        dctMatrix.setElementAt(-29, 3, 3);
        dctMatrix.setElementAt(0, 3, 4);
        dctMatrix.setElementAt(0, 3, 5);
        dctMatrix.setElementAt(0, 3, 6);
        dctMatrix.setElementAt(0, 3, 7);

        for (int i = 4; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                dctMatrix.setElementAt(0, i, j);
            }
        }

        dctMatrix.setElementAt(18, 4, 0);


        return dctMatrix;
    }

    private Matrix<Float> mockQuantizedMatrix() {
        Matrix<Float> quantizedMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        quantizedMatrix.setElementAt(-26f, 0, 0);
        quantizedMatrix.setElementAt(-3f, 0, 1);
        quantizedMatrix.setElementAt(-6f, 0, 2);
        quantizedMatrix.setElementAt(2f, 0, 3);
        quantizedMatrix.setElementAt(2f, 0, 4);
        quantizedMatrix.setElementAt(-1f, 0, 5);
        quantizedMatrix.setElementAt(0f, 0, 6);
        quantizedMatrix.setElementAt(0f, 0, 7);

        quantizedMatrix.setElementAt(0f, 1, 0);
        quantizedMatrix.setElementAt(-2f, 1, 1);
        quantizedMatrix.setElementAt(-4f, 1, 2);
        quantizedMatrix.setElementAt(1f, 1, 3);
        quantizedMatrix.setElementAt(1f, 1, 4);
        quantizedMatrix.setElementAt(0f, 1, 5);
        quantizedMatrix.setElementAt(0f, 1, 6);
        quantizedMatrix.setElementAt(0f, 1, 7);

        quantizedMatrix.setElementAt(-3f, 2, 0);
        quantizedMatrix.setElementAt(1f, 2, 1);
        quantizedMatrix.setElementAt(5f, 2, 2);
        quantizedMatrix.setElementAt(-1f, 2, 3);
        quantizedMatrix.setElementAt(-1f, 2, 4);
        quantizedMatrix.setElementAt(0f, 2, 5);
        quantizedMatrix.setElementAt(0f, 2, 6);
        quantizedMatrix.setElementAt(0f, 2, 7);

        quantizedMatrix.setElementAt(-3f, 3, 0);
        quantizedMatrix.setElementAt(1f, 3, 1);
        quantizedMatrix.setElementAt(2f, 3, 2);
        quantizedMatrix.setElementAt(-1f, 3, 3);
        quantizedMatrix.setElementAt(0f, 3, 4);
        quantizedMatrix.setElementAt(0f, 3, 5);
        quantizedMatrix.setElementAt(0f, 3, 6);
        quantizedMatrix.setElementAt(0f, 3, 7);

        for (int i = 4; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                quantizedMatrix.setElementAt(0f, i, j);
            }
        }

        quantizedMatrix.setElementAt(1f, 4, 0);

        return quantizedMatrix;
    }

    private Matrix<Integer> mockIntegerQuantizedMatrix() {
        Matrix<Integer> quantizedMatrix = new Matrix<Integer>(8, 8, new Integer[8][8]);

        quantizedMatrix.setElementAt(-26, 0, 0);
        quantizedMatrix.setElementAt(-3, 0, 1);
        quantizedMatrix.setElementAt(-6, 0, 2);
        quantizedMatrix.setElementAt(2, 0, 3);
        quantizedMatrix.setElementAt(2, 0, 4);
        quantizedMatrix.setElementAt(-1, 0, 5);
        quantizedMatrix.setElementAt(0, 0, 6);
        quantizedMatrix.setElementAt(0, 0, 7);

        quantizedMatrix.setElementAt(0, 1, 0);
        quantizedMatrix.setElementAt(-2, 1, 1);
        quantizedMatrix.setElementAt(-4, 1, 2);
        quantizedMatrix.setElementAt(1, 1, 3);
        quantizedMatrix.setElementAt(1, 1, 4);
        quantizedMatrix.setElementAt(0, 1, 5);
        quantizedMatrix.setElementAt(0, 1, 6);
        quantizedMatrix.setElementAt(0, 1, 7);

        quantizedMatrix.setElementAt(-3, 2, 0);
        quantizedMatrix.setElementAt(1, 2, 1);
        quantizedMatrix.setElementAt(5, 2, 2);
        quantizedMatrix.setElementAt(-1, 2, 3);
        quantizedMatrix.setElementAt(-1, 2, 4);
        quantizedMatrix.setElementAt(0, 2, 5);
        quantizedMatrix.setElementAt(0, 2, 6);
        quantizedMatrix.setElementAt(0, 2, 7);

        quantizedMatrix.setElementAt(-3, 3, 0);
        quantizedMatrix.setElementAt(1, 3, 1);
        quantizedMatrix.setElementAt(2, 3, 2);
        quantizedMatrix.setElementAt(-1, 3, 3);
        quantizedMatrix.setElementAt(0, 3, 4);
        quantizedMatrix.setElementAt(0, 3, 5);
        quantizedMatrix.setElementAt(0, 3, 6);
        quantizedMatrix.setElementAt(0, 3, 7);

        for (int i = 4; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                quantizedMatrix.setElementAt(0, i, j);
            }
        }

        quantizedMatrix.setElementAt(1, 4, 0);

        return quantizedMatrix;
    }
}
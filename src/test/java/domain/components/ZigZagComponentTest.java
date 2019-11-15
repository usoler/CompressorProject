package domain.components;

import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ZigZagComponentTest {

    // TODO: corner cases

    @Test
    public void verify_makeZigZag_returnsZigZagVector_whenIsValidMatrixParam() {
        // Mock
        ZigZagComponent zigZagComponent = new ZigZagComponent();
        Matrix<Float> matrix = mockQuantizedMatrix();
        float[] expected = mockZigZagVector();

        // Test
        float[] response = zigZagComponent.makeZigZag(matrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(Arrays.equals(expected, response));
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

    private float[] mockZigZagVector() {
        float[] zigZagVector = new float[]{
                -26f, -3f, 0, -3f, -2f, -6f, 2f, -4f, 1f, -3f, 1f, 1f, 5f,
                1f, 2f, -1f, 1f, -1f, 2f, 0, 0, 0, 0, 0, -1f, -1f, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        return zigZagVector;
    }

}
package domain.components;

import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ZigZagComponentTest {

    private static final ZigZagComponent zigZagComponent = new ZigZagComponent();

    @Test
    public void verify_makeZigZag_returnsZigZagVector_whenIsValidMatrixParam() throws CompressorException {
        // Mock
        Matrix<Float> matrix = mockQuantizedMatrix();
        float[] expected = mockZigZagVector();

        // Test
        float[] response = zigZagComponent.makeZigZag(matrix);

        Assert.assertNotNull(response);
        Assert.assertTrue(Arrays.equals(expected, response));
    }

    @Test
    public void verify_makeZigZag_throwsCompressorException_whenParamMatrix8x8IsNull() {
        try {
            zigZagComponent.makeZigZag(null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.MAKE_ZIGZAG_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
    }

    @Test
    public void verify_undoZigZag_returnsQuantizedMatrix_whenZigZagVectorIsValid() throws CompressorException {
        // Mock
        List<Integer> zigZagVector = mockListZigZagVector();
        Matrix<Integer> expected = mockIntegerQuantizedMatrix();

        // Test
        Matrix<Integer> response = zigZagComponent.undoZigZag(zigZagVector);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }


    @Test
    public void verify_undoZigZag_throwsCompressorException_whenParamZigZagVectorIsNull() {
        try {
            zigZagComponent.undoZigZag(null);
            Assert.fail();
        } catch (CompressorException ex) {
            Assert.assertNotNull(ex.getErrorCode());
            Assert.assertEquals(CompressorErrorCode.UNDO_ZIGZAG_FAILURE.getCode(), ex.getErrorCode().getCode());
        }
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

    private float[] mockZigZagVector() {
        float[] zigZagVector = new float[]{
                -26f, -3f, 0, -3f, -2f, -6f, 2f, -4f, 1f, -3f, 1f, 1f, 5f,
                1f, 2f, -1f, 1f, -1f, 2f, 0, 0, 0, 0, 0, -1f, -1f, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        return zigZagVector;
    }

    private List<Integer> mockListZigZagVector() {
        List<Integer> zigZagVector = new LinkedList<Integer>();

        zigZagVector.add(-26);
        zigZagVector.add(-3);
        zigZagVector.add(0);
        zigZagVector.add(-3);
        zigZagVector.add(-2);
        zigZagVector.add(-6);
        zigZagVector.add(2);
        zigZagVector.add(-4);
        zigZagVector.add(1);
        zigZagVector.add(-3);
        zigZagVector.add(1);
        zigZagVector.add(1);
        zigZagVector.add(5);
        zigZagVector.add(1);
        zigZagVector.add(2);
        zigZagVector.add(-1);
        zigZagVector.add(1);
        zigZagVector.add(-1);
        zigZagVector.add(2);
        zigZagVector.add(0);
        zigZagVector.add(0);
        zigZagVector.add(0);
        zigZagVector.add(0);
        zigZagVector.add(0);
        zigZagVector.add(-1);
        zigZagVector.add(-1);

        for (int i = 0; i < 38; ++i) {
            zigZagVector.add(0);
        }

        return zigZagVector;
    }
}
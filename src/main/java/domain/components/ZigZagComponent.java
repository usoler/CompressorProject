package domain.components;

import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class ZigZagComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZigZagComponent.class);

    private static final int[] is = new int[]{
            0, 0, 1, 2, 1, 0, 0, 1, 2, 3, 4, 3, 2,
            1, 0, 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2,
            1, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5,
            4, 3, 2, 1, 2, 3, 4, 5, 6, 7, 7, 6, 5,
            4, 3, 4, 5, 6, 7, 7, 6, 5, 6, 7, 7};

    private static final int[] js = new int[]{
            0, 1, 0, 0, 1, 2, 3, 2, 1, 0, 0, 1, 2,
            3, 4, 5, 4, 3, 2, 1, 0, 0, 1, 2, 3, 4,
            5, 6, 7, 6, 5, 4, 3, 2, 1, 0, 1, 2, 3,
            4, 5, 6, 7, 7, 6, 5, 4, 3, 2, 3, 4, 5,
            6, 7, 7, 6, 5, 4, 5, 6, 7, 7, 6, 7};

    /**
     * Apply a zig-zag algorithm to a 8x8 matrix
     *
     * @param matrix8x8 the 8x8 matrix to apply the zig-zag algorithm
     * @return the zig-zag vector
     * @throws CompressorException if any error occurs
     */
    public float[] makeZigZag(Matrix<Float> matrix8x8) throws CompressorException {
        checkMatrix(matrix8x8);
        int size = matrix8x8.getNumberOfRows() * matrix8x8.getNumberOfColumns();
        float[] zigZagVector = new float[size];

        for (int k = 0; k < size; ++k) {
            zigZagVector[k] = matrix8x8.getElementAt(is[k], js[k]);
        }

        return zigZagVector;
    }

    private void checkMatrix(Matrix<Float> matrix8x8) throws CompressorException {
        if (Objects.isNull(matrix8x8)) {
            String message = "Param Matrix 8x8 could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.MAKE_ZIGZAG_FAILURE);
        }
    }

    /**
     * Undo a zig-zag algorithm to a zig-zag vector
     *
     * @param zigZagVector the zig-zag vector to undo
     * @return the 8x8 matrix after to undo the zig-zag algorithm
     * @throws CompressorException if any error occurs
     */
    public Matrix<Integer> undoZigZag(List<Integer> zigZagVector) throws CompressorException {
        checkZigZagVector(zigZagVector);
        Matrix<Integer> matrix8x8 = new Matrix<Integer>(8, 8, new Integer[8][8]);

        for (int k = 0; k < zigZagVector.size(); ++k) {
            matrix8x8.setElementAt(zigZagVector.get(k), is[k], js[k]);
        }

        return matrix8x8;
    }

    private void checkZigZagVector(List<Integer> zigZagVector) throws CompressorException {
        if (Objects.isNull(zigZagVector)) {
            String message = "Param ZigZag Vector could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UNDO_ZIGZAG_FAILURE);
        }
    }
}

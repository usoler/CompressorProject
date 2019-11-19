package domain.components;

import domain.dataStructure.Matrix;
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

    public float[] makeZigZag(Matrix<Float> matrix8x8) throws IllegalArgumentException {
        if (Objects.isNull(matrix8x8)) {
            String message = "Param Matrix 8x8 could not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        int size = matrix8x8.getNumberOfRows() * matrix8x8.getNumberOfColumns();
        float[] zigZagVector = new float[size];

        for (int k = 0; k < size; ++k) {
            zigZagVector[k] = matrix8x8.getElementAt(is[k], js[k]);
        }

        return zigZagVector;
    }

    public Matrix<Integer> undoZigZag(List<Integer> zigZagVector) throws IllegalArgumentException {
        if (Objects.isNull(zigZagVector)) {
            String message = "Param ZigZag Vector could not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        Matrix<Integer> matrix8x8 = new Matrix<Integer>(8, 8, new Integer[8][8]);

        for (int k = 0; k < zigZagVector.size(); ++k) {
            matrix8x8.setElementAt(zigZagVector.get(k), is[k], js[k]);
        }

        return matrix8x8;
    }
}

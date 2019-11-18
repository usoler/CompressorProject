package domain.components;

import domain.dataStructure.Matrix;

import java.util.List;

public class ZigZagComponent {
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

    public float[] makeZigZag(Matrix<Float> matrix8x8) {
        int size = matrix8x8.getNumberOfRows() * matrix8x8.getNumberOfColumns();
        float[] zigZagVector = new float[size];

        for (int k = 0; k < size; ++k) {
            zigZagVector[k] = matrix8x8.getElementAt(is[k], js[k]);
        }

        return zigZagVector;
    }

    // TODO: check if it works
    public Matrix<Integer> undoZigZag(List<Integer> zigZagVector) {
        Matrix<Integer> matrix8x8 = new Matrix<Integer>(8, 8, new Integer[8][8]);

        for (int k = 0; k < zigZagVector.size(); ++k) {
            matrix8x8.setElementAt(zigZagVector.get(k), is[k], js[k]);
        }

        return matrix8x8;
    }
}

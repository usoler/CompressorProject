package domain.dataObjects;

import domain.dataStructure.Matrix;

public class PpmResponse {
    private final int width;
    private final int height;
    private final Matrix<Pixel> matrix;

    public PpmResponse(int width, int height, Matrix<Pixel> matrix) {
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Matrix<Pixel> getMatrix() {
        return matrix;
    }
}

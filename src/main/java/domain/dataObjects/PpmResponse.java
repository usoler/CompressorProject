package domain.dataObjects;

import domain.dataStructure.Matrix;

public class PpmResponse {
    private final int magicNumber;
    private final int width;
    private final int height;
    private final Matrix<Pixel> matrix;

    public PpmResponse(int magicNumber, int width, int height, Matrix<Pixel> matrix) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    public int getMagicNumber() {
        return magicNumber;
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

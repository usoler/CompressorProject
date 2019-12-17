package domain.dataObjects;

import domain.dataStructure.Matrix;

public class PpmResponse {
    private final int magicNumber;
    private final int width;
    private final int height;
    private final Matrix<Pixel> matrix;

    /**
     * Constructs a new {@link PpmResponse}
     *
     * @param magicNumber the PPM magic number
     * @param width       the PPM with
     * @param height      the PPM height
     * @param matrix      the PPM {@link Matrix<Pixel>}
     */
    public PpmResponse(int magicNumber, int width, int height, Matrix<Pixel> matrix) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.matrix = matrix;
    }

    /**
     * Gets the PPM magic number
     *
     * @return the PPM magic number
     */
    public int getMagicNumber() {
        return magicNumber;
    }

    /**
     * Gets the PPM width
     *
     * @return the PPM width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the PPM height
     *
     * @return the PPM height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the PPM {@link Matrix<Pixel>}
     *
     * @return the PPM {@link Matrix<Pixel>}
     */
    public Matrix<Pixel> getMatrix() {
        return matrix;
    }
}

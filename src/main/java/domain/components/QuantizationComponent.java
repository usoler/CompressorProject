package domain.components;

import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class QuantizationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantizationComponent.class);
    private static final int NUM_OF_ROWS_AND_COLS = 8;
    private static final float[] quantizationValues = {16f, 11f, 10f, 16f, 24f, 40f, 51f, 61f,
            12f, 12f, 14f, 19f, 26f, 58f, 60f, 55f,
            14f, 13f, 16f, 24f, 40f, 57f, 69f, 56f,
            14f, 17f, 22f, 29f, 51f, 87f, 80f, 62f,
            18f, 22f, 37f, 56f, 68f, 109f, 103f, 77f,
            24f, 35f, 55f, 64f, 81f, 104f, 113f, 92f,
            49f, 64f, 78f, 87f, 103f, 121f, 120f, 101f,
            72f, 92f, 95f, 98f, 112f, 100f, 103f, 99f};
    private static final Matrix<Float> quantizationMatrix = initQuantizationMatrix();

    private static Matrix<Float> initQuantizationMatrix() {
        Matrix<Float> quantizationMatrix = new Matrix<Float>(NUM_OF_ROWS_AND_COLS, NUM_OF_ROWS_AND_COLS,
                new Float[NUM_OF_ROWS_AND_COLS][NUM_OF_ROWS_AND_COLS]);

        int k = 0;
        for (int i = 0; i < NUM_OF_ROWS_AND_COLS; ++i) {
            for (int j = 0; j < NUM_OF_ROWS_AND_COLS; ++j) {
                quantizationMatrix.setElementAt(quantizationValues[k], i, j);
                ++k;
            }
        }

        return quantizationMatrix;
    }

    /**
     * Quantize a DCT Matrix
     *
     * @param dctMatrix the DCT Matrix to quantize
     * @return the quantized matrix after quantize the DCT Matrix
     * @throws CompressorException if any error occurs
     */
    public Matrix<Float> quantizeMatrix(Matrix<Float> dctMatrix) throws CompressorException {
        checkDctMatrix(dctMatrix);
        Matrix<Float> quantizedMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                float quantizedPixel = Math.round(dctMatrix.getElementAt(i, j) / quantizationMatrix.getElementAt(i, j));
                quantizedMatrix.setElementAt(quantizedPixel, i, j);
            }
        }

        return quantizedMatrix;
    }

    /**
     * Dequantize a quantize Matrix
     *
     * @param quantizedMatrix the quantized matrix to dequantize
     * @return the dequantized matrix after dequantize the quantize Matrix
     * @throws CompressorException if any error occurs
     */
    public Matrix<Integer> dequantizeMatrix(Matrix<Integer> quantizedMatrix) throws CompressorException {
        checkQuantizedMatrix(quantizedMatrix);
        Matrix<Integer> desquantizedMatrix = new Matrix<Integer>(8, 8, new Integer[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                int desquantizedPixel = Math.round(quantizedMatrix.getElementAt(i, j) * quantizationMatrix.getElementAt(i, j));
                desquantizedMatrix.setElementAt(desquantizedPixel, i, j);
            }
        }

        return desquantizedMatrix;
    }

    private void checkDctMatrix(Matrix<Float> dctMatrix) throws CompressorException {
        if (Objects.isNull(dctMatrix)) {
            String message = "DCT Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.QUANTIZE_FAILURE);
        }
    }

    private void checkQuantizedMatrix(Matrix<Integer> quantizedMatrix) throws CompressorException {
        if (Objects.isNull(quantizedMatrix)) {
            String message = "Quantized Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.DEQUANTIZE_FAILURE);
        }
    }
}

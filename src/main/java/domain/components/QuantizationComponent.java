package domain.components;

import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class QuantizationComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantizationComponent.class);
    private static final Matrix<Float> quantizationMatrix = initQuantizationMatrix();

    private static Matrix<Float> initQuantizationMatrix() {
        Matrix<Float> quantizationMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        quantizationMatrix.setElementAt(16f, 0, 0);
        quantizationMatrix.setElementAt(11f, 0, 1);
        quantizationMatrix.setElementAt(10f, 0, 2);
        quantizationMatrix.setElementAt(16f, 0, 3);
        quantizationMatrix.setElementAt(24f, 0, 4);
        quantizationMatrix.setElementAt(40f, 0, 5);
        quantizationMatrix.setElementAt(51f, 0, 6);
        quantizationMatrix.setElementAt(61f, 0, 7);

        quantizationMatrix.setElementAt(12f, 1, 0);
        quantizationMatrix.setElementAt(12f, 1, 1);
        quantizationMatrix.setElementAt(14f, 1, 2);
        quantizationMatrix.setElementAt(19f, 1, 3);
        quantizationMatrix.setElementAt(26f, 1, 4);
        quantizationMatrix.setElementAt(58f, 1, 5);
        quantizationMatrix.setElementAt(60f, 1, 6);
        quantizationMatrix.setElementAt(55f, 1, 7);

        quantizationMatrix.setElementAt(14f, 2, 0);
        quantizationMatrix.setElementAt(13f, 2, 1);
        quantizationMatrix.setElementAt(16f, 2, 2);
        quantizationMatrix.setElementAt(24f, 2, 3);
        quantizationMatrix.setElementAt(40f, 2, 4);
        quantizationMatrix.setElementAt(57f, 2, 5);
        quantizationMatrix.setElementAt(69f, 2, 6);
        quantizationMatrix.setElementAt(56f, 2, 7);

        quantizationMatrix.setElementAt(14f, 3, 0);
        quantizationMatrix.setElementAt(17f, 3, 1);
        quantizationMatrix.setElementAt(22f, 3, 2);
        quantizationMatrix.setElementAt(29f, 3, 3);
        quantizationMatrix.setElementAt(51f, 3, 4);
        quantizationMatrix.setElementAt(87f, 3, 5);
        quantizationMatrix.setElementAt(80f, 3, 6);
        quantizationMatrix.setElementAt(62f, 3, 7);

        quantizationMatrix.setElementAt(18f, 4, 0);
        quantizationMatrix.setElementAt(22f, 4, 1);
        quantizationMatrix.setElementAt(37f, 4, 2);
        quantizationMatrix.setElementAt(56f, 4, 3);
        quantizationMatrix.setElementAt(68f, 4, 4);
        quantizationMatrix.setElementAt(109f, 4, 5);
        quantizationMatrix.setElementAt(103f, 4, 6);
        quantizationMatrix.setElementAt(77f, 4, 7);

        quantizationMatrix.setElementAt(24f, 5, 0);
        quantizationMatrix.setElementAt(35f, 5, 1);
        quantizationMatrix.setElementAt(55f, 5, 2);
        quantizationMatrix.setElementAt(64f, 5, 3);
        quantizationMatrix.setElementAt(81f, 5, 4);
        quantizationMatrix.setElementAt(104f, 5, 5);
        quantizationMatrix.setElementAt(113f, 5, 6);
        quantizationMatrix.setElementAt(92f, 5, 7);

        quantizationMatrix.setElementAt(49f, 6, 0);
        quantizationMatrix.setElementAt(64f, 6, 1);
        quantizationMatrix.setElementAt(78f, 6, 2);
        quantizationMatrix.setElementAt(87f, 6, 3);
        quantizationMatrix.setElementAt(103f, 6, 4);
        quantizationMatrix.setElementAt(121f, 6, 5);
        quantizationMatrix.setElementAt(120f, 6, 6);
        quantizationMatrix.setElementAt(101f, 6, 7);

        quantizationMatrix.setElementAt(72f, 7, 0);
        quantizationMatrix.setElementAt(92f, 7, 1);
        quantizationMatrix.setElementAt(95f, 7, 2);
        quantizationMatrix.setElementAt(98f, 7, 3);
        quantizationMatrix.setElementAt(112f, 7, 4);
        quantizationMatrix.setElementAt(100f, 7, 5);
        quantizationMatrix.setElementAt(103f, 7, 6);
        quantizationMatrix.setElementAt(99f, 7, 7);

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

    private void checkDctMatrix(Matrix<Float> dctMatrix) throws CompressorException {
        if (Objects.isNull(dctMatrix)) {
            String message = "DCT Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.QUANTIZE_FAILURE);
        }
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

    private void checkQuantizedMatrix(Matrix<Integer> quantizedMatrix) throws CompressorException {
        if (Objects.isNull(quantizedMatrix)) {
            String message = "Quantized Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.DEQUANTIZE_FAILURE);
        }
    }

}

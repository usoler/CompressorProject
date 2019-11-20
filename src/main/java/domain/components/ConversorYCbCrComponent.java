package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class ConversorYCbCrComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversorYCbCrComponent.class);

    /**
     * Converts a RGB matrix into a YCbCr matrix
     *
     * @param rgbMatrix the RGB matrix to coverts
     * @return the YCbCr matrix converted
     * @throws CompressorException if any error occurs
     */
    public Matrix<Pixel> convertFromRGB(Matrix<Pixel> rgbMatrix) throws CompressorException {
        checkRgbMatrix(rgbMatrix);

        int height = rgbMatrix.getNumberOfRows();
        int width = rgbMatrix.getNumberOfColumns();
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);

                checkRgbPixel(i, j, pixel);

                float y = Math.round(((0.299f * pixel.getX()) + (0.587f * pixel.getY()) + (0.114f * pixel.getZ())) * 1000f) / 1000f;
                float cb = Math.round((128f - (0.168736f * pixel.getX()) - (0.331264f * pixel.getY()) + (0.5f * pixel.getZ())) * 1000f) / 1000f;
                float cr = Math.round((128f + (0.5f * pixel.getX()) - (0.418688f * pixel.getY()) - (0.081312f * pixel.getZ())) * 1000f) /
                        1000f;

                y = Math.max(0, Math.min(255f, y));
                cb = Math.max(0, Math.min(255f, cb));
                cr = Math.max(0, Math.min(255f, cr));

                yCbCrMatrix.setElementAt(new Pixel(y, cb, cr), i, j);
            }
        }

        return yCbCrMatrix;
    }

    private void checkRgbPixel(int i, int j, Pixel pixel) throws CompressorException {
        if (Objects.isNull(pixel)) {
            String message = String.format("Pixel from param RGB Matrix at position (%s,%s) could not be null", i, j);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.CONVERT_RGB_TO_YCBCR_FAILURE);
        }
    }

    private void checkRgbMatrix(Matrix<Pixel> rgbMatrix) throws CompressorException {
        if (Objects.isNull(rgbMatrix)) {
            String message = "Param RGB Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.CONVERT_RGB_TO_YCBCR_FAILURE);
        }
    }

    /**
     * Converts a YCbCr matrix into a RGB matrix
     *
     * @param yCbCrMatrix the YCbCr matrix to convert
     * @return the RGB matrix converted
     * @throws CompressorException if any error occurs
     */
    public Matrix<Pixel> convertToRGB(Matrix<Pixel> yCbCrMatrix) throws CompressorException {
        checkYCbCrMatrix(yCbCrMatrix);

        int height = yCbCrMatrix.getNumberOfRows();
        int width = yCbCrMatrix.getNumberOfColumns();
        Matrix<Pixel> rgbMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = yCbCrMatrix.getElementAt(i, j);

                checkYCbCrPixel(i, j, pixel);

                float red = Math.round((pixel.getX() + (1.402f * (pixel.getZ() - 128f))) * 1000f) / 1000f;
                float green = Math.round((pixel.getX() - (0.34414f * (pixel.getY() - 128f)) - (0.71414f * (pixel.getZ() - 128f))) * 1000f) / 1000f;
                float blue = Math.round((pixel.getX() + (1.772f * (pixel.getY() - 128f))) * 1000f) / 1000f;

                red = Math.max(0, Math.min(255f, red));
                green = Math.max(0, Math.min(255f, green));
                blue = Math.max(0, Math.min(255f, blue));

                rgbMatrix.setElementAt(new Pixel(red, green, blue), i, j);
            }
        }

        return rgbMatrix;
    }

    private void checkYCbCrMatrix(Matrix<Pixel> yCbCrMatrix) throws CompressorException {
        if (Objects.isNull(yCbCrMatrix)) {
            String message = "Param YCbCr Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.CONVERT_YCBCR_TO_RGB_FAILURE);
        }
    }

    private void checkYCbCrPixel(int i, int j, Pixel pixel) throws CompressorException {
        if (Objects.isNull(pixel)) {
            String message = String.format("Pixel from param YCbCr Matrix at position (%s,%s) could not be null", i, j);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.CONVERT_YCBCR_TO_RGB_FAILURE);
        }
    }
}
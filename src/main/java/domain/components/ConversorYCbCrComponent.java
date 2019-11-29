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
                Pixel rgbPixel = rgbMatrix.getElementAt(i, j);
                checkRgbPixel(i, j, rgbPixel);
                yCbCrMatrix.setElementAt(generateYCbCrPixelFrom(rgbPixel), i, j);
            }
        }

        return yCbCrMatrix;
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
                rgbMatrix.setElementAt(generateRGBPixelFrom(pixel), i, j);
            }
        }

        return rgbMatrix;
    }

    private Pixel generateYCbCrPixelFrom(Pixel rgbPixel) {
        return new Pixel(generateLuminance(rgbPixel), generateBlueChrominance(rgbPixel), generateRedChrominance(rgbPixel));
    }

    private Pixel generateRGBPixelFrom(Pixel yCbCrPixel) {
        return new Pixel(generateRed(yCbCrPixel), generateGreen(yCbCrPixel), generateBlue(yCbCrPixel));
    }

    private float generateLuminance(Pixel rgbPixel) {
        float luminance = Math.round(((0.299f * rgbPixel.getX()) + (0.587f * rgbPixel.getY()) + (0.114f * rgbPixel.getZ())) * 1000f) / 1000f;
        return Math.max(0, Math.min(255f, luminance));
    }

    private float generateBlueChrominance(Pixel rgbPixel) {
        float blueChrominance = Math.round((128f - (0.168736f * rgbPixel.getX()) - (0.331264f * rgbPixel.getY()) + (0.5f * rgbPixel.getZ())) * 1000f) / 1000f;
        return Math.max(0, Math.min(255f, blueChrominance));
    }

    private float generateRedChrominance(Pixel rgbPixel) {
        float redChrominance = Math.round((128f + (0.5f * rgbPixel.getX()) - (0.418688f * rgbPixel.getY()) - (0.081312f * rgbPixel.getZ())) * 1000f) /
                1000f;
        return Math.max(0, Math.min(255f, redChrominance));
    }

    private float generateRed(Pixel yCbCrPixel) {
        float red = Math.round((yCbCrPixel.getX() + (1.402f * (yCbCrPixel.getZ() - 128f))) * 1000f) / 1000f;
        return Math.max(0, Math.min(255f, red));
    }

    private float generateGreen(Pixel yCbCrPixel) {
        float green = Math.round((yCbCrPixel.getX() - (0.34414f * (yCbCrPixel.getY() - 128f)) - (0.71414f * (yCbCrPixel.getZ() - 128f))) * 1000f) / 1000f;
        return Math.max(0, Math.min(255f, green));
    }

    private float generateBlue(Pixel yCbCrPixel) {
        float blue = Math.round((yCbCrPixel.getX() + (1.772f * (yCbCrPixel.getY() - 128f))) * 1000f) / 1000f;
        return Math.max(0, Math.min(255f, blue));
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
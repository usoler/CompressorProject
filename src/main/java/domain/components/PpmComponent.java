package domain.components;

import domain.dataObjects.Pixel;
import domain.dataObjects.PpmResponse;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PpmComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PpmComponent.class);

    /**
     * Read a PPM file and converts it to a pixel matrix
     *
     * @param data the PPM file data
     * @return the pixel matrixof the PPM file data
     * @throws CompressorException if any error occurs
     */
    // TODO: check if it works
    public PpmResponse readPpmFile(String data) throws CompressorException {
        checkData(data);

        String dataWithoutComments = removeComments(data);

        String[] elements = dataWithoutComments.split("\\s+");

        int originalWidth = Integer.parseInt(elements[1]);
        int originalHeight = Integer.parseInt(elements[2]);

        int width = originalWidth;
        int height = originalHeight;

        while (width % 16 != 0) {
            ++width;
        }

        while (height % 16 != 0) {
            ++height;
        }

        Matrix<Pixel> pixels = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        int k = 4;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width * 3; j += 3) {
                if (i < originalHeight && j < originalWidth * 3) {
                    float red = Float.parseFloat(elements[k]);
                    float green = Float.parseFloat(elements[k + 1]);
                    float blue = Float.parseFloat(elements[k + 2]);

                    Pixel pixel = new Pixel(red, green, blue);
                    pixels.setElementAt(pixel, i, j / 3);

                    k += 3;
                } else {
                    pixels.setElementAt(new Pixel(pixels.getElementAt(Math.min(i, originalHeight - 1), Math.min(j, originalWidth - 1))), i, j / 3);
                }
            }
        }

        return new PpmResponse(originalWidth, originalHeight, pixels);
    }

    private void checkData(String data) throws CompressorException {
        if (Objects.isNull(data) || data.isEmpty() || data.trim().length() == 0) {
            String message = "Param data could not be null, empty or blank";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }
    }

    private String removeComments(String data) {
        return data.replaceAll("#.*\n", "");
    }

    /**
     * Write a PPM file given a RGB Matrix
     *
     * @param height    the height of the image
     * @param width     the width of the image
     * @param rgbMatrix the RGB Matrix to write into the PPM file
     * @return the PPM file obtained by the RGB Matrix
     * @throws CompressorException if any error occurs
     */
    public String writePpmFile(int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        checkRgbMatrix(rgbMatrix);

        String response = "P3\n" + width + " " + height + "\n255\n";
        StringBuffer buffer = new StringBuffer(response);
        int cont = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);

                checkPixel(i, j, pixel);

                int red = Math.round(pixel.getX());
                int green = Math.round(pixel.getY());
                int blue = Math.round(pixel.getZ());

                String values = red + " " + green + " " + blue;

                if (cont == 3) {
                    values = values + "\n";
                    cont = 0;
                } else {
                    values = values + " ";
                    ++cont;
                }

                buffer.append(values);
            }

            if (i < height - 1) {
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    private void checkPixel(int i, int j, Pixel pixel) throws CompressorException {
        if (Objects.isNull(pixel)) {
            String message = String.format("Pixel from param RGB Matrix at position (%s,%s) could not be " +
                    "null, empty or blank", i, j);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }
    }

    private void checkRgbMatrix(Matrix<Pixel> rgbMatrix) throws CompressorException {
        if (Objects.isNull(rgbMatrix)) {
            String message = "Param RGB Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }
    }
}

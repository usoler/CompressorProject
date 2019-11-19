package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PpmComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PpmComponent.class);

    public Matrix<Pixel> readPpmFile(String data) throws CompressorException {
        checkData(data);

        String dataWithoutComments = removeComments(data);

        String[] elements = dataWithoutComments.split("\\s+");

        int width = Integer.parseInt(elements[1]);
        int height = Integer.parseInt(elements[2]);

        Matrix<Pixel> pixels = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        int k = 4;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width * 3; j += 3) {
                float red = Float.parseFloat(elements[k]);
                float green = Float.parseFloat(elements[k + 1]);
                float blue = Float.parseFloat(elements[k + 2]);

                Pixel pixel = new Pixel(red, green, blue);
                pixels.setElementAt(pixel, i, j / 3);
                k += 3;
            }
        }

        return pixels;
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

package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class PpmComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PpmComponent.class);

    public Matrix<Pixel> readPpmFile(String data) throws IllegalArgumentException {
        if (Objects.isNull(data) || data.isEmpty() || data.trim().length() == 0) {
            String message = "Param data could not be null, empty or blank";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        String[] elements = data.split("\\s+");

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

    public String writePpmFile(int height, int width, Matrix<Pixel> rgbMatrix) throws IllegalArgumentException {
        if (Objects.isNull(rgbMatrix)) {
            String message = "Param RGB Matrix could not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        String response = "P3\n" + width + " " + height + "\n255\n";
        StringBuffer buffer = new StringBuffer(response);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);

                if(Objects.isNull(pixel)){
                    String message = String.format("Pixel from param RGB Matrix at position (%s,%s) could not be " +
                            "null, empty or blank", i, j);
                    LOGGER.error(message);
                    throw new IllegalArgumentException(message);
                }

                int red = Math.round(pixel.getX());
                int green = Math.round(pixel.getY());
                int blue = Math.round(pixel.getZ());

                String values;
                if (j < width - 1) {
                    values = red + " " + green + " " + blue + " ";
                } else {
                    values = red + " " + green + " " + blue;
                }
                buffer.append(values);
            }

            if (i < height - 1) {
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }
}

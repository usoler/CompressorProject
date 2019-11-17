package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversorYCbCrComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversorYCbCrComponent.class);

    public Matrix<Pixel> convertFromRGB(Matrix<Pixel> rgbMatrix) {
        int height = rgbMatrix.getNumberOfRows();
        int width = rgbMatrix.getNumberOfColumns();
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);
                float y = Math.round(((0.299f * pixel.getX()) + (0.587f * pixel.getY()) + (0.114f * pixel.getZ())) * 1000f) / 1000f;
                float cb = Math.round((128f - (0.168736f * pixel.getX()) - (0.331264f * pixel.getY()) + (0.5f * pixel.getZ())) * 1000f) / 1000f;
                float cr = Math.round((128f + (0.5f * pixel.getX()) - (0.418688f * pixel.getY()) - (0.081312f * pixel.getZ())) * 1000f) /
                        1000f;

                if (y > 255f) {
                    y = 255f;
                }

                if (cb > 255f) {
                    cb = 255f;
                }

                if (cr > 255f) {
                    cr = 255f;
                }

                yCbCrMatrix.setElementAt(new Pixel(y, cb, cr), i, j);
            }
        }

        return yCbCrMatrix;
    }

    public Matrix<Pixel> convertToRGB(Matrix<Pixel> yCbCrMatrix) throws Exception {
        int height = yCbCrMatrix.getNumberOfRows();
        int width = yCbCrMatrix.getNumberOfColumns();
        Matrix<Pixel> rgbMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        for (int i = 0; i < height; ++i) {
            //LOGGER.debug("i: {}", i);
            for (int j = 0; j < width; ++j) {
                //LOGGER.debug("j: {}", j);
                if (j == 144 && i == 336) {
                    String o = "o";
                }
                Pixel pixel = yCbCrMatrix.getElementAt(i, j);
                float red = 0;
                float green = 0;
                float blue = 0;
                try {
                    red = Math.round((pixel.getX() + (1.402f * (pixel.getZ() - 128f))) * 1000f) / 1000f;
                    green = Math.round((pixel.getX() - (0.344136f * (pixel.getY() - 128f)) - (0.714136f * (pixel.getZ() - 128f))) * 1000f) / 1000f;
                    blue = Math.round((pixel.getX() + (1.772f * (pixel.getY() - 128f))) * 1000f) / 1000f;
                } catch (Exception e) {
                    LOGGER.error("i: {}, j: {}", i, j);
                    throw new Exception();
                }

                if (red < 0) {
                    red = 0;
                }
                if (green < 0) {
                    green = 0;
                }
                if (blue < 0) {
                    blue = 0;
                }
                if (red > 255f) {
                    red = 255f;
                }
                if (green > 255f) {
                    green = 255f;
                }
                if (blue > 255f) {
                    blue = 255f;
                }

                rgbMatrix.setElementAt(new Pixel(red, green, blue), i, j);
            }
        }

        return rgbMatrix;
    }
}
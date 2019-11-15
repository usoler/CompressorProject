package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;

public class ConversorYCbCrComponent {

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
}
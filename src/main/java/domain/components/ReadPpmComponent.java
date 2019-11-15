package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;

public class ReadPpmComponent {
    private static final int INITIAL_DATA_ROW = 3;

    // TODO: DEPRECATED
    public Matrix<Pixel> readPpmFile(String data) throws Exception {
        String[] lines = data.split("\r?\n");
        String[] dimensions = lines[1].split(" ");

        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);

        Matrix<Pixel> pixels = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        for (int i = 0; i < height; ++i) {
            String[] rows = lines[INITIAL_DATA_ROW + i].split("\\s+");

            for (int j = 0; j < width * 3; j += 3) {
                float red = Float.parseFloat(rows[j]);
                float green = Float.parseFloat(rows[j + 1]);
                float blue = Float.parseFloat(rows[j + 2]);

                Pixel pixel = new Pixel(red, green, blue);
                pixels.setElementAt(pixel, i, j / 3);
            }
        }

        return pixels;
    }

    public Matrix<Pixel> readPpmFileV2(String data) throws Exception {
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
}

package domain.utils;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.fileManager.FileImpl;

public class ReadPpmComponent {
    private static final int INITIAL_DATA_ROW = 3;

    public Matrix<Pixel> readPpmFile(FileImpl file) throws Exception {
        String data = file.GetData();
        String[] lines = data.split("\r\n");
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
}

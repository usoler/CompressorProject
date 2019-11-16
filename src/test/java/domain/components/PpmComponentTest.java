package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;

public class PpmComponentTest {
    private static final String pathTest = "input/prueba.ppm";
    private static final int NUM_OF_ROWS = 2;
    private static final int NUM_OF_COLUMNS = 3;

    // TODO: corner cases
//
//    @Test
//    public void verify_readPpmFile_returnsMatrixOfPixels_whenIsRead() throws Exception {
//        // Mocks
//        FileManager fileManager = new FileManager();
//        fileManager.readFile(pathTest);
//
//        ReadPpmComponent readPpmComponent = new ReadPpmComponent();
//
//        Matrix<Pixel> expected = mockValidPixelMatrix();
//
//        // Test
//        Matrix<Pixel> response = readPpmComponent.readPpmFile(fileManager.getFile(pathTest).GetData());
//
//        Assert.assertNotNull(response);
//        Assert.assertTrue(expected.equals(response));
//    }

    private Matrix<Pixel> mockValidPixelMatrix() {
        Matrix<Pixel> pixels = new Matrix<Pixel>(NUM_OF_ROWS, NUM_OF_COLUMNS, new Pixel[NUM_OF_ROWS][NUM_OF_COLUMNS]);

        pixels.setElementAt(new Pixel(255, 0, 0), 0, 0);
        pixels.setElementAt(new Pixel(0, 255, 0), 0, 1);
        pixels.setElementAt(new Pixel(0, 0, 255), 0, 2);
        pixels.setElementAt(new Pixel(255, 255, 0), 1, 0);
        pixels.setElementAt(new Pixel(255, 255, 255), 1, 1);
        pixels.setElementAt(new Pixel(0, 0, 0), 1, 2);

        return pixels;
    }
}
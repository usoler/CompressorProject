package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.dataObjects.Pixel;
import domain.dataStructure.Matrix;
import domain.utils.ConversorYCbCrComponent;
import domain.utils.ReadPpmComponent;

public class Jpeg implements AlgorithmInterface {
    private static final ReadPpmComponent readPpmComponent = new ReadPpmComponent();
    private static final ConversorYCbCrComponent conversorYCbCrComponent = new ConversorYCbCrComponent();

    @Override
    public String encode(String file) throws Exception {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");

        // 0. Read BMP file
        Matrix<Pixel> rgbMatrix = readPpmComponent.readPpmFile(file);

        // 1. Color conversion
        Matrix<Pixel> yCbCrMatrix = conversorYCbCrComponent.convertFromRGB(rgbMatrix);

        // 2. Downsampling


        return null;
    }

    @Override
    public String decode(String file) {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");
        return null;
    }
}

package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import domain.utils.ConversorYCbCrComponent;
import domain.utils.DCTComponent;
import domain.utils.DownsamplingComponent;
import domain.utils.ReadPpmComponent;

import java.util.LinkedList;
import java.util.List;

public class Jpeg implements AlgorithmInterface {
    private static final ReadPpmComponent readPpmComponent = new ReadPpmComponent();
    private static final ConversorYCbCrComponent conversorYCbCrComponent = new ConversorYCbCrComponent();
    private static final DownsamplingComponent downsamplingComponent = new DownsamplingComponent();
    private static final DCTComponent dctComponent = new DCTComponent();

    @Override
    public String encode(String file) throws Exception {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");

        // 0. Read BMP file
        Matrix<Pixel> rgbMatrix = readPpmComponent.readPpmFile(file);

        // 1. Color conversion
        Matrix<Pixel> yCbCrMatrix = conversorYCbCrComponent.convertFromRGB(rgbMatrix);

        // 2. Downsampling
        int numOfMacroBlockByColumn = yCbCrMatrix.getNumberOfColumns() / 16;
        int numOfMacroBlockByRow = yCbCrMatrix.getNumberOfRows() / 16;

        int y = 0;
        for (int i = 0; i < numOfMacroBlockByColumn; ++i) {
            int x = 0;

            for (int j = 0; j < numOfMacroBlockByRow; ++j) {

                Matrix<Pixel> matrix16x16 = new Matrix<Pixel>(16, 16, new Pixel[16][16]);
                int u = 0;
                for (int k = y; k < y + 16; ++k) {
                    int v = 0;
                    for (int s = x; s < x + 16; ++s) {
                        matrix16x16.setElementAt(yCbCrMatrix.getElementAt(k, s), u, v);
                        ++v;
                    }

                    ++u;
                }

                MacroBlockYCbCr macroBlockYCbCr = downsamplingComponent.downsampling(matrix16x16);

                List<Matrix<Float>> blocksOf8x8 = new LinkedList<Matrix<Float>>();
                // 3. Discrete Cosine Transform (DCT)
                // Y Blocks
                for (int m = 0; m < macroBlockYCbCr.getyBlocks().size(); ++m) {
                    blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getyBlocks().get(m)));
                }

                // Cb block
                blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCbBlock()));

                // Cr block
                blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCrBlock()));

                // 4. Quantization
                // ................

                x += 16;
            }

            y += 16;
        }

        return null;
    }

    @Override
    public String decode(String file) {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");
        return null;
    }
}

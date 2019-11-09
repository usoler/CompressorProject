package domain.utils;

import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;

public class DownsamplingComponent {
    private static final int NUM_8x8_BLOCKS = 4;
    private static final int[] ks = new int[]{0, 0, 4, 4};
    private static final int[] ss = new int[]{0, 4, 0, 4};
    private static final int[] is = new int[]{0, 0, 8, 8};
    private static final int[] js = new int[]{0, 8, 0, 8};

    public MacroBlockYCbCr downsampling(Matrix<Pixel> yCbCrMatrix) {
        MacroBlockYCbCr macroBlockYCbCr = new MacroBlockYCbCr();
        Matrix<Float> cbMatrix = new Matrix<Float>(8, 8, new Float[8][8]);
        Matrix<Float> crMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int r = 0; r < NUM_8x8_BLOCKS; ++r) {
            Matrix<Float> yMatrix = new Matrix<Float>(8, 8, new Float[8][8]);
            int k = ks[r];
            int x = 0;
            for (int i = is[r]; i < (is[r] + 8); ++i) {
                int s = ss[r];
                int y = 0;
                for (int j = js[r]; j < (js[r] + 8); ++j) {
                    Pixel pixel = yCbCrMatrix.getElementAt(i, j);

                    // Y component
                    yMatrix.setElementAt(pixel.getX(), x, y);

                    // Cb and Cr component
                    if (((i % 2) == 0) && ((j % 2) == 0)) {
                        cbMatrix.setElementAt(pixel.getY(), k, s);
                        crMatrix.setElementAt(pixel.getZ(), k, s);
                        ++s;
                    }
                    ++y;
                }
                ++x;

                if ((i % 2) == 0) {
                    ++k;
                }
            }
            macroBlockYCbCr.addYBlock(yMatrix);
        }

        macroBlockYCbCr.setCbBlock(cbMatrix);
        macroBlockYCbCr.setCrBlock(crMatrix);

        return macroBlockYCbCr;
    }
}

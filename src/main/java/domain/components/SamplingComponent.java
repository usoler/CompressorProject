package domain.components;

import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class SamplingComponent {
    private static final int NUM_8x8_BLOCKS = 4;
    private static final int[] ks = new int[]{0, 0, 4, 4};
    private static final int[] ss = new int[]{0, 4, 0, 4};
    private static final int[] is = new int[]{0, 0, 8, 8};
    private static final int[] js = new int[]{0, 8, 0, 8};

    private static final Logger LOGGER = LoggerFactory.getLogger(SamplingComponent.class);

    public MacroBlockYCbCr downsampling(Matrix<Pixel> yCbCrMatrix) throws CompressorException {
        checkYCbCrMatrix(yCbCrMatrix);

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

                    checkYCbCrPixel(i, j, pixel);

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

    private void checkYCbCrPixel(int i, int j, Pixel pixel) throws CompressorException {
        if (Objects.isNull(pixel)) {
            String message = String.format("Pixel from param YCbCr Matrix at position (%s,%s) could not be null", i, j);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.DOWNSAMPLING_FAILURE);
        }
    }

    private void checkYCbCrMatrix(Matrix<Pixel> yCbCrMatrix) throws CompressorException {
        if (Objects.isNull(yCbCrMatrix)) {
            String message = "Param YCbCr Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.DOWNSAMPLING_FAILURE);
        }
    }

    public Matrix<Pixel> upsampling(MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        validateMacroBlock(macroBlockYCbCr);

        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(16, 16, new Pixel[16][16]);

        for (int n = 0; n < NUM_8x8_BLOCKS; ++n) {
            int k = ks[n];
            float cb = macroBlockYCbCr.getCbBlock().getElementAt(ks[n], ss[n]);
            float cr = macroBlockYCbCr.getCrBlock().getElementAt(ks[n], ss[n]);
            for (int i = 0; i < 8; ++i) {
                int s = ss[n];
                for (int j = 0; j < 8; ++j) {
                    // Luminance
                    float luminance = macroBlockYCbCr.getyBlocks().get(n).getElementAt(i, j);

                    // Chrominance
                    if ((j != 0) && (j % 2 == 0)) {
                        ++s;
                        cb = macroBlockYCbCr.getCbBlock().getElementAt(k, s);
                        cr = macroBlockYCbCr.getCrBlock().getElementAt(k, s);
                    }
                    if ((i % 2 != 0) && (j == 7)) {
                        ++k;
                    }

                    yCbCrMatrix.setElementAt(new Pixel(luminance, cb, cr), i + is[n], j + js[n]);
                }
            }
        }

        return yCbCrMatrix;
    }

    private void validateMacroBlock(MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        if (Objects.isNull(macroBlockYCbCr)) {
            String message = "Param MacroBlock YCbCr could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UPSAMPLING_FAILURE);
        }

        if (Objects.isNull(macroBlockYCbCr.getyBlocks())) {
            String message = "List of Y block from param MacroBlock YCbCr could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UPSAMPLING_FAILURE);
        }

        if (macroBlockYCbCr.getyBlocks().size() != 4) {
            String message = "Param MacroBlock YCbCr should have 4 Y blocks";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UPSAMPLING_FAILURE);
        }

        if (Objects.isNull(macroBlockYCbCr.getCbBlock())) {
            String message = "Cb block from param MacroBlock YCbCr could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UPSAMPLING_FAILURE);
        }

        if (Objects.isNull(macroBlockYCbCr.getCrBlock())) {
            String message = "Cr block from param MacroBlock YCbCr could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.UPSAMPLING_FAILURE);
        }
    }
}

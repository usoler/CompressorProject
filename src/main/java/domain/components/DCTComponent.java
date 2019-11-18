package domain.components;

import domain.dataStructure.Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DCTComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(DCTComponent.class);

    public Matrix<Float> applyDCT(Matrix<Float> matrix8x8) throws IllegalArgumentException {
        if (Objects.isNull(matrix8x8)) {
            String message = "Param Matrix 8x8 could not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        Matrix<Float> dctMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                float ci = getCfValue(i);
                float cj = getCfValue(j);

                float sum = 0;

                for (int x = 0; x < 8; ++x) {
                    for (int y = 0; y < 8; ++y) {
                        float pixel = matrix8x8.getElementAt(x, y) - 128;

                        float term1 = ((2 * x) + 1) * i * (float) Math.PI;
                        float cosinus1 = (float) Math.cos(term1 / 16f);

                        float term2 = ((2 * y) + 1) * j * (float) Math.PI;
                        float cosinus2 = (float) Math.cos(term2 / 16f);

                        sum += (pixel * cosinus1 * cosinus2);
                    }
                }

                float dctPixel = (1f / 4f) * ci * cj * sum;
                dctMatrix.setElementAt(Math.round(dctPixel * 100f) / 100f, i, j);
            }
        }

        return dctMatrix;
    }

    public Matrix<Float> undoDCT(Matrix<Integer> dctMatrix) throws IllegalArgumentException {
        if (Objects.isNull(dctMatrix)) {
            String message = "Param Matrix 8x8 could not be null";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        Matrix<Float> matrix8x8 = new Matrix<Float>(8, 8, new Float[8][8]);

        for (int x = 0; x < 8; ++x) {
            for (int y = 0; y < 8; ++y) {

                float sum = 0;
                for (int u = 0; u < 8; ++u) {
                    for (int v = 0; v < 8; ++v) {
                        float cu = getCfValue(u);
                        float cv = getCfValue(v);

                        float dctPixel = dctMatrix.getElementAt(u, v);

                        float term1 = ((2 * x) + 1) * u * (float) Math.PI;
                        float cosinus1 = (float) Math.cos(term1 / 16f);

                        float term2 = ((2 * y) + 1) * v * (float) Math.PI;
                        float cosinus2 = (float) Math.cos(term2 / 16f);

                        sum += (cu * cv * dctPixel * cosinus1 * cosinus2);
                    }
                }

                float matrix8x8Pixel = (1f / 4f) * sum;
                float roundedPixel = (Math.round((matrix8x8Pixel + 128f) * 100f) / 100f);
                if (roundedPixel > 255f) {
                    roundedPixel = 255f;
                }
                matrix8x8.setElementAt(roundedPixel, x, y);
            }
        }

        return matrix8x8;
    }

    private float getCfValue(int f) {
        if (f == 0) {
            return (1f / (float) Math.sqrt(2));
        } else {
            return 1f;
        }
    }
}

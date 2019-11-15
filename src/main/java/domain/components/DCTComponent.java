package domain.components;

import domain.dataStructure.Matrix;

public class DCTComponent {

    public Matrix<Float> applyDCT(Matrix<Float> matrix8x8) {
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

    private float getCfValue(int f) {
        if (f == 0) {
            return (1f / (float) Math.sqrt(2));
        } else {
            return 1f;
        }
    }
}

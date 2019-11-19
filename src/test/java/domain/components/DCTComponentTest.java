package domain.components;

import domain.dataStructure.Matrix;
import org.junit.Assert;
import org.junit.Test;

public class DCTComponentTest {
    private static final DCTComponent dctComponent = new DCTComponent();

    @Test
    public void verify_applyDCT_returnsDCTMatrix_whenParamMatrix8x8IsValid() {
        // Mock
        Matrix<Float> matrix8x8 = mockMatrixWikipedia();
        Matrix<Float> expected = mockDCTMatrix();

        // Test
        Matrix<Float> response = dctComponent.applyDCT(matrix8x8);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_applyDCT_throwsIllegalArgumentException_whenParamMatrix8x8IsNull() {
        try {
            dctComponent.applyDCT(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param Matrix 8x8 could not be null", ex.getMessage());
        }
    }

    @Test
    public void verify_undoDCT_returnsDCTMatrix_whenParamMatrix8x8IsValid() {
        // Mock
        Matrix<Integer> matrix8x8 = mockIntegerDCTMatrix();
        Matrix<Float> expected = mockUndoDCTtMatrixWikipedia();

        // Test
        Matrix<Float> response = dctComponent.undoDCT(matrix8x8);

        Assert.assertNotNull(response);
        Assert.assertTrue(expected.equals(response));
    }

    @Test
    public void verify_undoDCT_throwsIllegalArgumentException_whenParamMatrix8x8IsNull() {
        try {
            dctComponent.undoDCT(null);
            Assert.fail();
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Param Matrix 8x8 could not be null", ex.getMessage());
        }
    }

    private Matrix<Float> mockMatrixWikipedia() {
        Matrix<Float> matrix8x8 = new Matrix<Float>(8, 8, new Float[8][8]);

        matrix8x8.setElementAt(52f, 0, 0);
        matrix8x8.setElementAt(55f, 0, 1);
        matrix8x8.setElementAt(61f, 0, 2);
        matrix8x8.setElementAt(66f, 0, 3);
        matrix8x8.setElementAt(70f, 0, 4);
        matrix8x8.setElementAt(61f, 0, 5);
        matrix8x8.setElementAt(64f, 0, 6);
        matrix8x8.setElementAt(73f, 0, 7);

        matrix8x8.setElementAt(63f, 1, 0);
        matrix8x8.setElementAt(59f, 1, 1);
        matrix8x8.setElementAt(55f, 1, 2);
        matrix8x8.setElementAt(90f, 1, 3);
        matrix8x8.setElementAt(109f, 1, 4);
        matrix8x8.setElementAt(85f, 1, 5);
        matrix8x8.setElementAt(69f, 1, 6);
        matrix8x8.setElementAt(72f, 1, 7);

        matrix8x8.setElementAt(62f, 2, 0);
        matrix8x8.setElementAt(59f, 2, 1);
        matrix8x8.setElementAt(68f, 2, 2);
        matrix8x8.setElementAt(113f, 2, 3);
        matrix8x8.setElementAt(144f, 2, 4);
        matrix8x8.setElementAt(104f, 2, 5);
        matrix8x8.setElementAt(66f, 2, 6);
        matrix8x8.setElementAt(73f, 2, 7);

        matrix8x8.setElementAt(63f, 3, 0);
        matrix8x8.setElementAt(58f, 3, 1);
        matrix8x8.setElementAt(71f, 3, 2);
        matrix8x8.setElementAt(122f, 3, 3);
        matrix8x8.setElementAt(154f, 3, 4);
        matrix8x8.setElementAt(106f, 3, 5);
        matrix8x8.setElementAt(70f, 3, 6);
        matrix8x8.setElementAt(69f, 3, 7);

        matrix8x8.setElementAt(67f, 4, 0);
        matrix8x8.setElementAt(61f, 4, 1);
        matrix8x8.setElementAt(68f, 4, 2);
        matrix8x8.setElementAt(104f, 4, 3);
        matrix8x8.setElementAt(126f, 4, 4);
        matrix8x8.setElementAt(88f, 4, 5);
        matrix8x8.setElementAt(68f, 4, 6);
        matrix8x8.setElementAt(70f, 4, 7);

        matrix8x8.setElementAt(79f, 5, 0);
        matrix8x8.setElementAt(65f, 5, 1);
        matrix8x8.setElementAt(60f, 5, 2);
        matrix8x8.setElementAt(70f, 5, 3);
        matrix8x8.setElementAt(77f, 5, 4);
        matrix8x8.setElementAt(68f, 5, 5);
        matrix8x8.setElementAt(58f, 5, 6);
        matrix8x8.setElementAt(75f, 5, 7);

        matrix8x8.setElementAt(85f, 6, 0);
        matrix8x8.setElementAt(71f, 6, 1);
        matrix8x8.setElementAt(64f, 6, 2);
        matrix8x8.setElementAt(59f, 6, 3);
        matrix8x8.setElementAt(55f, 6, 4);
        matrix8x8.setElementAt(61f, 6, 5);
        matrix8x8.setElementAt(65f, 6, 6);
        matrix8x8.setElementAt(83f, 6, 7);

        matrix8x8.setElementAt(87f, 7, 0);
        matrix8x8.setElementAt(79f, 7, 1);
        matrix8x8.setElementAt(69f, 7, 2);
        matrix8x8.setElementAt(68f, 7, 3);
        matrix8x8.setElementAt(65f, 7, 4);
        matrix8x8.setElementAt(76f, 7, 5);
        matrix8x8.setElementAt(78f, 7, 6);
        matrix8x8.setElementAt(94f, 7, 7);

        return matrix8x8;
    }

    private Matrix<Float> mockUndoDCTtMatrixWikipedia() {
        Matrix<Float> matrix8x8 = new Matrix<Float>(8, 8, new Float[8][8]);

        matrix8x8.setElementAt(62.17f, 0, 0);
        matrix8x8.setElementAt(65.37f, 0, 1);
        matrix8x8.setElementAt(56.96f, 0, 2);
        matrix8x8.setElementAt(59.96f, 0, 3);
        matrix8x8.setElementAt(72.35f, 0, 4);
        matrix8x8.setElementAt(63.01f, 0, 5);
        matrix8x8.setElementAt(59.63f, 0, 6);
        matrix8x8.setElementAt(82.28f, 0, 7);

        matrix8x8.setElementAt(57.13f, 1, 0);
        matrix8x8.setElementAt(55.41f, 1, 1);
        matrix8x8.setElementAt(55.93f, 1, 2);
        matrix8x8.setElementAt(82.18f, 1, 3);
        matrix8x8.setElementAt(107.7f, 1, 4);
        matrix8x8.setElementAt(87.37f, 1, 5);
        matrix8x8.setElementAt(62.26f, 1, 6);
        matrix8x8.setElementAt(70.89f, 1, 7);

        matrix8x8.setElementAt(57.6f, 2, 0);
        matrix8x8.setElementAt(49.88f, 2, 1);
        matrix8x8.setElementAt(59.87f, 2, 2);
        matrix8x8.setElementAt(110.6f, 2, 3);
        matrix8x8.setElementAt(147.72f, 2, 4);
        matrix8x8.setElementAt(113.55f, 2, 5);
        matrix8x8.setElementAt(67.16f, 2, 6);
        matrix8x8.setElementAt(64.62f, 2, 7);

        matrix8x8.setElementAt(65f, 3, 0);
        matrix8x8.setElementAt(54.79f, 3, 1);
        matrix8x8.setElementAt(66.22f, 3, 2);
        matrix8x8.setElementAt(120.44f, 3, 3);
        matrix8x8.setElementAt(154.97f, 3, 4);
        matrix8x8.setElementAt(114.41f, 3, 5);
        matrix8x8.setElementAt(67.79f, 3, 6);
        matrix8x8.setElementAt(70.25f, 3, 7);

        matrix8x8.setElementAt(69.98f, 4, 0);
        matrix8x8.setElementAt(62.91f, 4, 1);
        matrix8x8.setElementAt(66.64f, 4, 2);
        matrix8x8.setElementAt(100.94f, 4, 3);
        matrix8x8.setElementAt(121.71f, 4, 4);
        matrix8x8.setElementAt(87.55f, 4, 5);
        matrix8x8.setElementAt(60.3f, 4, 6);
        matrix8x8.setElementAt(77.86f, 4, 7);

        matrix8x8.setElementAt(70.74f, 5, 0);
        matrix8x8.setElementAt(70.91f, 5, 1);
        matrix8x8.setElementAt(63.66f, 5, 2);
        matrix8x8.setElementAt(70.38f, 5, 3);
        matrix8x8.setElementAt(79.98f, 5, 4);
        matrix8x8.setElementAt(62.42f, 5, 5);
        matrix8x8.setElementAt(55.64f, 5, 6);
        matrix8x8.setElementAt(80.75f, 5, 7);

        matrix8x8.setElementAt(74.55f, 6, 0);
        matrix8x8.setElementAt(82.48f, 6, 1);
        matrix8x8.setElementAt(67.11f, 6, 2);
        matrix8x8.setElementAt(54.4f, 6, 3);
        matrix8x8.setElementAt(63.45f, 6, 4);
        matrix8x8.setElementAt(64.57f, 6, 5);
        matrix8x8.setElementAt(65.79f, 6, 6);
        matrix8x8.setElementAt(83.33f, 6, 7);

        matrix8x8.setElementAt(80.86f, 7, 0);
        matrix8x8.setElementAt(93.62f, 7, 1);
        matrix8x8.setElementAt(74.74f, 7, 2);
        matrix8x8.setElementAt(54.22f, 7, 3);
        matrix8x8.setElementAt(67.81f, 7, 4);
        matrix8x8.setElementAt(80.94f, 7, 5);
        matrix8x8.setElementAt(81.12f, 7, 6);
        matrix8x8.setElementAt(87.2f, 7, 7);

        return matrix8x8;
    }

    private Matrix<Float> mockDCTMatrix() {
        Matrix<Float> dctMatrix = new Matrix<Float>(8, 8, new Float[8][8]);

        dctMatrix.setElementAt(-415.37f, 0, 0);
        dctMatrix.setElementAt(-30.19f, 0, 1);
        dctMatrix.setElementAt(-61.20f, 0, 2);
        dctMatrix.setElementAt(27.24f, 0, 3);
        dctMatrix.setElementAt(56.12f, 0, 4);
        dctMatrix.setElementAt(-20.10f, 0, 5);
        dctMatrix.setElementAt(-2.39f, 0, 6);
        dctMatrix.setElementAt(0.46f, 0, 7);

        dctMatrix.setElementAt(4.47f, 1, 0);
        dctMatrix.setElementAt(-21.86f, 1, 1);
        dctMatrix.setElementAt(-60.76f, 1, 2);
        dctMatrix.setElementAt(10.25f, 1, 3);
        dctMatrix.setElementAt(13.15f, 1, 4);
        dctMatrix.setElementAt(-7.09f, 1, 5);
        dctMatrix.setElementAt(-8.54f, 1, 6);
        dctMatrix.setElementAt(4.88f, 1, 7);

        dctMatrix.setElementAt(-46.83f, 2, 0);
        dctMatrix.setElementAt(7.37f, 2, 1);
        dctMatrix.setElementAt(77.13f, 2, 2);
        dctMatrix.setElementAt(-24.56f, 2, 3);
        dctMatrix.setElementAt(-28.91f, 2, 4);
        dctMatrix.setElementAt(9.93f, 2, 5);
        dctMatrix.setElementAt(5.42f, 2, 6);
        dctMatrix.setElementAt(-5.65f, 2, 7);

        dctMatrix.setElementAt(-48.53f, 3, 0);
        dctMatrix.setElementAt(12.07f, 3, 1);
        dctMatrix.setElementAt(34.10f, 3, 2);
        dctMatrix.setElementAt(-14.76f, 3, 3);
        dctMatrix.setElementAt(-10.24f, 3, 4);
        dctMatrix.setElementAt(6.30f, 3, 5);
        dctMatrix.setElementAt(1.83f, 3, 6);
        dctMatrix.setElementAt(1.95f, 3, 7);

        dctMatrix.setElementAt(12.12f, 4, 0);
        dctMatrix.setElementAt(-6.55f, 4, 1);
        dctMatrix.setElementAt(-13.20f, 4, 2);
        dctMatrix.setElementAt(-3.95f, 4, 3);
        dctMatrix.setElementAt(-1.87f, 4, 4);
        dctMatrix.setElementAt(1.75f, 4, 5);
        dctMatrix.setElementAt(-2.79f, 4, 6);
        dctMatrix.setElementAt(3.14f, 4, 7);

        dctMatrix.setElementAt(-7.73f, 5, 0);
        dctMatrix.setElementAt(2.91f, 5, 1);
        dctMatrix.setElementAt(2.38f, 5, 2);
        dctMatrix.setElementAt(-5.94f, 5, 3);
        dctMatrix.setElementAt(-2.38f, 5, 4);
        dctMatrix.setElementAt(0.94f, 5, 5);
        dctMatrix.setElementAt(4.30f, 5, 6);
        dctMatrix.setElementAt(1.85f, 5, 7);

        dctMatrix.setElementAt(-1.03f, 6, 0);
        dctMatrix.setElementAt(0.18f, 6, 1);
        dctMatrix.setElementAt(0.42f, 6, 2);
        dctMatrix.setElementAt(-2.42f, 6, 3);
        dctMatrix.setElementAt(-0.88f, 6, 4);
        dctMatrix.setElementAt(-3.02f, 6, 5);
        dctMatrix.setElementAt(4.12f, 6, 6);
        dctMatrix.setElementAt(-0.66f, 6, 7);

        dctMatrix.setElementAt(-0.17f, 7, 0);
        dctMatrix.setElementAt(0.14f, 7, 1);
        dctMatrix.setElementAt(-1.07f, 7, 2);
        dctMatrix.setElementAt(-4.19f, 7, 3);
        dctMatrix.setElementAt(-1.17f, 7, 4);
        dctMatrix.setElementAt(-0.10f, 7, 5);
        dctMatrix.setElementAt(0.50f, 7, 6);
        dctMatrix.setElementAt(1.68f, 7, 7);

        return dctMatrix;
    }

    private Matrix<Integer> mockIntegerDCTMatrix() {
        Matrix<Integer> dctMatrix = new Matrix<Integer>(8, 8, new Integer[8][8]);

        dctMatrix.setElementAt(-416, 0, 0);
        dctMatrix.setElementAt(-33, 0, 1);
        dctMatrix.setElementAt(-60, 0, 2);
        dctMatrix.setElementAt(32, 0, 3);
        dctMatrix.setElementAt(48, 0, 4);
        dctMatrix.setElementAt(-40, 0, 5);
        dctMatrix.setElementAt(0, 0, 6);
        dctMatrix.setElementAt(0, 0, 7);

        dctMatrix.setElementAt(0, 1, 0);
        dctMatrix.setElementAt(-24, 1, 1);
        dctMatrix.setElementAt(-56, 1, 2);
        dctMatrix.setElementAt(19, 1, 3);
        dctMatrix.setElementAt(26, 1, 4);
        dctMatrix.setElementAt(0, 1, 5);
        dctMatrix.setElementAt(0, 1, 6);
        dctMatrix.setElementAt(0, 1, 7);

        dctMatrix.setElementAt(-42, 2, 0);
        dctMatrix.setElementAt(13, 2, 1);
        dctMatrix.setElementAt(80, 2, 2);
        dctMatrix.setElementAt(-24, 2, 3);
        dctMatrix.setElementAt(-40, 2, 4);
        dctMatrix.setElementAt(0, 2, 5);
        dctMatrix.setElementAt(0, 2, 6);
        dctMatrix.setElementAt(0, 2, 7);

        dctMatrix.setElementAt(-42, 3, 0);
        dctMatrix.setElementAt(17, 3, 1);
        dctMatrix.setElementAt(44, 3, 2);
        dctMatrix.setElementAt(-29, 3, 3);
        dctMatrix.setElementAt(0, 3, 4);
        dctMatrix.setElementAt(0, 3, 5);
        dctMatrix.setElementAt(0, 3, 6);
        dctMatrix.setElementAt(0, 3, 7);

        dctMatrix.setElementAt(18, 4, 0);
        dctMatrix.setElementAt(0, 4, 1);
        dctMatrix.setElementAt(0, 4, 2);
        dctMatrix.setElementAt(0, 4, 3);
        dctMatrix.setElementAt(0, 4, 4);
        dctMatrix.setElementAt(0, 4, 5);
        dctMatrix.setElementAt(0, 4, 6);
        dctMatrix.setElementAt(0, 4, 7);

        for (int i = 5; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                dctMatrix.setElementAt(0, i, j);
            }
        }

        return dctMatrix;
    }
}
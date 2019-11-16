package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.components.*;
import domain.dataObjects.CoefficientEnum;
import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Jpeg implements AlgorithmInterface {
    private static final ReadPpmComponent readPpmComponent = new ReadPpmComponent();
    private static final ConversorYCbCrComponent conversorYCbCrComponent = new ConversorYCbCrComponent();
    private static final DownsamplingComponent downsamplingComponent = new DownsamplingComponent();
    private static final DCTComponent dctComponent = new DCTComponent();
    private static final QuantizationComponent quantizationComponent = new QuantizationComponent();
    private static final ZigZagComponent zigZagComponent = new ZigZagComponent();
    private static final HuffmanComponent huffmanComponent = new HuffmanComponent();

    @Override
    public byte[] encode(byte[] data) throws Exception {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");
        String file = new String(data, StandardCharsets.UTF_8);

        float[] lastDC = new float[]{0, 0, 0}; // Y, Cb, Cr
        StringBuffer buffer = new StringBuffer();

        // 0. Read BMP file
        Matrix<Pixel> rgbMatrix = readPpmComponent.readPpmFileV2(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte width = (byte) rgbMatrix.getNumberOfRows();
        byte height = (byte) rgbMatrix.getNumberOfColumns();
        byteArrayOutputStream.write(width);
        byteArrayOutputStream.write(height);

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

                // 3. Discrete Cosine Transform (DCT)
                List<Matrix<Float>> blocksOf8x8 = new LinkedList<Matrix<Float>>();
                // Y Blocks
                for (int m = 0; m < macroBlockYCbCr.getyBlocks().size(); ++m) {
                    blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getyBlocks().get(m)));
                }

                // Cb block
                blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCbBlock()));

                // Cr block
                blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCrBlock()));

                // 4. Quantization
                List<Matrix<Float>> quantizedBlocks = new LinkedList<Matrix<Float>>();
                for (int n = 0; n < blocksOf8x8.size(); ++n) {
                    quantizedBlocks.add(quantizationComponent.quantizeMatrix(blocksOf8x8.get(n)));
                }

                // 5. Entropy Coding
                // 5.1 Zig Zag Vector
                for (int m = 0; m < quantizedBlocks.size(); ++m) {
                    float[] zigZagValues = zigZagComponent.makeZigZag(quantizedBlocks.get(m));

                    int n;
                    CoefficientEnum coefficientEnum;
                    if (m < 4) {
                        n = 0;
                        coefficientEnum = CoefficientEnum.LUMINANCE;
                    } else if (m == 4) {
                        n = 1;
                        coefficientEnum = CoefficientEnum.CHROMINANCE;
                    } else {
                        n = 2;
                        coefficientEnum = CoefficientEnum.CHROMINANCE;
                    }

                    // Process DC value
                    float dpcmDC = zigZagValues[0] - lastDC[n];
                    lastDC[n] = zigZagValues[0];

                    buffer = huffmanComponent.encodeDC(Math.round(dpcmDC), buffer);

                    // Process 63 AC values
                    // First, check EOB
                    int countZerosEOB = 0;
                    boolean foundEOB = false;
                    for (int w = zigZagValues.length - 1; w >= 0 && !foundEOB; --w) {
                        if (zigZagValues[w] == 0) {
                            ++countZerosEOB;
                        } else {
                            foundEOB = true;
                        }
                    }

                    int numOfPreZeros = 0;
                    for (int w = 1; w < (zigZagValues.length - countZerosEOB); ++w) {
                        if (numOfPreZeros > 15) { // Catch ZRL case
                            if (coefficientEnum.equals(CoefficientEnum.LUMINANCE)) {
                                buffer.append("11111111001");
                            } else {
                                buffer.append("1111111010");
                            }
                            numOfPreZeros = 0;
                        } else {
                            float valueAC = zigZagValues[w];
                            if (valueAC != 0) {
                                buffer = huffmanComponent.encodeAC(Math.round(valueAC), numOfPreZeros, coefficientEnum, buffer);
                                numOfPreZeros = 0;
                            } else {
                                ++numOfPreZeros;
                            }
                        }
                    }

                    // Set EOB
                    if (coefficientEnum.equals(CoefficientEnum.LUMINANCE)) {
                        buffer.append("1010");
                    } else {
                        buffer.append("00");
                    }
                }

                x += 16;
            }

            y += 16;
        }
/*
        // TODO: optimizar ??
        StringBuffer extendedBuffer = new StringBuffer();
        int counter = 0;
        while ((buffer.length() + extendedBuffer.length() + 3) % 8 != 0) {
            extendedBuffer.append("0");
            ++counter;
        }

        String extensionOfZeros = bitExtension(Integer.toBinaryString(counter), 3);

        StringBuffer responseBuffer = new StringBuffer(extensionOfZeros);
        responseBuffer.append(extendedBuffer.toString());
        responseBuffer.append(buffer.toString());

        return new BigInteger(responseBuffer.toString(), 2).toByteArray();*/
        byteArrayOutputStream.write(new BigInteger(buffer.toString(), 2).toByteArray());
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public byte[] decode(byte[] data) throws Exception {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");

        int[] lastDC = new int[]{0, 0, 0}; // Y, Cb, Cr

        // 0. Read JPEG file
        byte[] widthByte = new byte[1];
        widthByte[0] = data[0];
        String widthBinary = new BigInteger(widthByte).toString(2);
        byte[] heighthByte = new byte[1];
        heighthByte[0] = data[1];
        String heightBinary = new BigInteger(widthByte).toString(2);
        int numOfRows = Integer.parseInt(widthBinary, 2);
        int numOfCols = Integer.parseInt(heightBinary, 2);

        StringBuffer dataBuffer = new StringBuffer(new BigInteger(Arrays.copyOfRange(data, 2, data.length)).toString(2));
        StringBuffer workingBuffer = new StringBuffer();

        // 1. Entropy decoding
        List<Matrix<Integer>> quantizedBlocks = new LinkedList<Matrix<Integer>>();
        boolean finish = false;
        int i = 0; // 0 <= i <= 3 -> 4*Y,  i == 4 -> 1*Cb, i == 5 -> 1*Cr
        int k = 0;
        while (!finish) {
            workingBuffer = new StringBuffer();

            // DC coefficient
            List<Integer> zigZagValues = new LinkedList<Integer>();
            int numOfBits = -1;
            while (numOfBits == -1) {
                workingBuffer.append(dataBuffer.charAt(k));
                numOfBits = huffmanComponent.getNumOfBitsOfColumn(workingBuffer.toString());
                ++k;
            }

            String columnBinary = dataBuffer.substring(k, k + numOfBits);
            int dc = huffmanComponent.decodeCoefficient(numOfBits, Integer.parseInt(columnBinary, 2));

            int n;
            if (i <= 3) {
                n = 0;
            } else if (i == 4) {
                n = 1;
            } else {
                n = 2;
            }

            zigZagValues.add(dc + lastDC[n]);
            lastDC[n] = dc + lastDC[n];
            k += numOfBits;

            // AC coefficients
            boolean endOfBlock = false;
            for (int j = 0; j < 63 && !endOfBlock; ++j) {
                workingBuffer = new StringBuffer();
                Pair<Integer, Integer> preZerosAndRow = new Pair<Integer, Integer>(-1, -1);
                while (preZerosAndRow.getKey() == -1) {
                    workingBuffer.append(dataBuffer.charAt(k));
                    if (i < 4) {
                        preZerosAndRow = huffmanComponent.getPreZerosAndRowOfValueLuminance(workingBuffer.toString());
                    } else {
                        preZerosAndRow = huffmanComponent.getPreZerosAndRowOfValueChrominance(workingBuffer.toString());
                    }
                    ++k;
                }

                if (preZerosAndRow.getKey() == -2) {
                    // TODO: ZLR, añadir 16 zeros y empezamos nueva iteracion - check if it works
                    for (int m = 0; m < 16; ++m) {
                        zigZagValues.add(0);
                    }
                } else if (preZerosAndRow.getKey() == -3) {
                    for (int m = zigZagValues.size() - 1; m < 63; ++m) {
                        zigZagValues.add(0);
                    }

                    endOfBlock = true;
                } else {
                    for (int m = 0; m < preZerosAndRow.getKey(); ++m) {
                        zigZagValues.add(0);
                    }

                    columnBinary = dataBuffer.substring(k, k + preZerosAndRow.getValue());
                    int ac = huffmanComponent.decodeCoefficient(preZerosAndRow.getValue(), Integer.parseInt(columnBinary, 2));

                    zigZagValues.add(ac);
                    k += preZerosAndRow.getValue();
                }
            }

            // TODO: Deshacer zigzag
            //quantizedBlocks.add(zigZagComponent.undoZigZag(zigZagValues, numOfRows, numOfCols));

            if (i == 5) {
                i = 0;
            } else {
                ++i;
            }

            if (k == dataBuffer.length()) {
                finish = true;
            }
        }

        return null;
    }

    private String bitExtension(String bits, int size) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < size - bits.length(); ++i) {
            buffer.append(0);
        }

        buffer.append(bits);

        return buffer.toString();
    }
}

package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.components.*;
import domain.dataObjects.*;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Jpeg implements AlgorithmInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(Jpeg.class);

    private static final PpmComponent ppmComponent = new PpmComponent();
    private static final ConversorYCbCrComponent conversorYCbCrComponent = new ConversorYCbCrComponent();
    private static final SamplingComponent samplingComponent = new SamplingComponent();
    private static final DCTComponent dctComponent = new DCTComponent();
    private static final QuantizationComponent quantizationComponent = new QuantizationComponent();
    private static final ZigZagComponent zigZagComponent = new ZigZagComponent();
    private static final HuffmanComponent huffmanComponent = new HuffmanComponent();

    /**
     * Encode a PPM file applying the JPEG algorithm
     *
     * @param data the PPM file data to encode
     * @return the file encoded
     * @throws CompressorException if any error occurs
     */
    @Override
    public byte[] encode(byte[] data) throws CompressorException {
        LOGGER.info("Encoding data file with JPEG algorithm");
        float[] lastDC = new float[]{0, 0, 0}; // Y, Cb, Cr
        StringBuffer buffer = new StringBuffer();

        // 0. Read PPM file
        Pair<Matrix<Pixel>, ByteArrayOutputStream> ppmFileResponse = readPpmFile(data);
        // 1. Color conversion
        Matrix<Pixel> yCbCrMatrix = conversorYCbCrComponent.convertFromRGB(ppmFileResponse.getKey());

        int numOfMacroBlockByRow = yCbCrMatrix.getNumberOfColumns() / 16;
        int numOfMacroBlockByColumn = yCbCrMatrix.getNumberOfRows() / 16;

        int y = 0;
        for (int i = 0; i < numOfMacroBlockByColumn; ++i) {
            int x = 0;
            for (int j = 0; j < numOfMacroBlockByRow; ++j) {
                // 2. Downsampling
                MacroBlockYCbCr macroBlockYCbCr = samplingComponent.downsampling(generateMatrix16x16(yCbCrMatrix, y, x));
                // 3. Discrete Cosine Transform (DCT)
                List<Matrix<Float>> blocksOf8x8 = applyDCT(macroBlockYCbCr);
                // 4. Quantization
                List<Matrix<Float>> quantizedBlocks = quantize(blocksOf8x8);
                // 5. Entropy Coding
                buffer = entropyCoding(quantizedBlocks, buffer, lastDC);

                x += 16;
            }
            y += 16;
        }

        return writeBufferToOutputStream(buffer, ppmFileResponse.getValue());
    }

    /**
     * Decode a JPEG file applying the JPEG algorithm
     *
     * @param data the JPEG file data to decode
     * @return the file decoded
     * @throws CompressorException if any error occurs
     */
    @Override
    public byte[] decode(byte[] data) throws CompressorException {
        LOGGER.info("Decoding data file with JPEG algorithm");
        int[] lastDC = new int[]{0, 0, 0}; // Y, Cb, Cr
        List<Matrix<Pixel>> blocksOfPixelMatrix16x16 = new LinkedList<>();

        // 0. Read JPEG file
        JpegResponse jpegResponse = readJpegFile(data);
        int width = jpegResponse.getWidth();
        int height = jpegResponse.getHeight();
        StringBuffer dataBuffer = new StringBuffer(new BigInteger(Arrays.copyOfRange(data, 3 +
                jpegResponse.getHeightNumOfBytes() + jpegResponse.getWidthNumOfBytes(), data.length)).toString(2));

        // 1. Entropy decoding
        // 1.1 Huffman decoding
        List<Matrix<Integer>> quantizedBlocks = new LinkedList<Matrix<Integer>>();
        boolean finish = false;
        int i = 0; // 0 <= i <= 3 -> 4*Y,  i == 4 -> 1*Cb, i == 5 -> 1*Cr
        int k = 0;
        while (!finish) {
            StringBuffer workingBuffer = new StringBuffer();
            int numOfBits = -1;
            while (numOfBits == -1) {
                workingBuffer.append(dataBuffer.charAt(k));
                numOfBits = huffmanComponent.getNumOfBitsOfColumn(workingBuffer.toString());
                ++k;
            }

            // DC coefficient
            Pair<Integer, List<Integer>> DCResponse = unprocessDCValue(k, lastDC, i, numOfBits, dataBuffer);
            k = DCResponse.getKey();
            List<Integer> zigZagValues = DCResponse.getValue();

            // AC coefficients
            Pair<Integer, List<Integer>> ACResponse = unprocessACValues(dataBuffer, k, i, zigZagValues);
            k = ACResponse.getKey();
            zigZagValues = ACResponse.getValue();

            // 1.2 Undo Zig Zag Vector
            quantizedBlocks.add(zigZagComponent.undoZigZag(zigZagValues));
            if (i == 5) {
                // 2. Desquantization
                List<Matrix<Integer>> blocksOf8x8 = dequantize(quantizedBlocks);
                // 3. Undo DCT
                MacroBlockYCbCr macroBlockYCbCr = undoDctProcess(blocksOf8x8);
                // 4. Undo downsampling
                Matrix<Pixel> yCbCrMatrix16x16 = samplingComponent.upsampling(macroBlockYCbCr);
                blocksOfPixelMatrix16x16.add(yCbCrMatrix16x16);

                i = 0;
                quantizedBlocks = new LinkedList<>();
            } else {
                ++i;
            }

            if (k == dataBuffer.length()) {
                finish = true;
            }
        }

        // 5. Reconstuct Total Matrix
        Matrix<Pixel> yCbCrMatrix = reconstructTotalMatrix(width, height, blocksOfPixelMatrix16x16);
        // 6. Undo Color Conversion
        Matrix<Pixel> rgbMatrix = conversorYCbCrComponent.convertToRGB(yCbCrMatrix);
        // 7. Write PPM file
        return ppmComponent.writePpmFile(jpegResponse.getMagicNumber(), height, width, rgbMatrix);
    }

    private Pair<Matrix<Pixel>, ByteArrayOutputStream> readPpmFile(byte[] data) throws CompressorException {
        PpmResponse ppmResponse = ppmComponent.readPpmFile(data);

        byte[] height = BigInteger.valueOf(ppmResponse.getHeight()).toByteArray();
        byte[] width = BigInteger.valueOf(ppmResponse.getWidth()).toByteArray();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream = writeMagicNumberToStream(byteArrayOutputStream, BigInteger.valueOf(ppmResponse.getMagicNumber()).toByteArray());
        byteArrayOutputStream = writeWidthSizeAndHeightSizeToStream(byteArrayOutputStream, width, height);
        byteArrayOutputStream = writeWidthAndHeightToStream(byteArrayOutputStream, width, height);

        return new Pair<>(ppmResponse.getMatrix(), byteArrayOutputStream);
    }

    private ByteArrayOutputStream writeMagicNumberToStream(ByteArrayOutputStream byteArrayOutputStream, byte[] magicNumber) throws CompressorException {
        try {
            byteArrayOutputStream.write(magicNumber);
            return byteArrayOutputStream;
        } catch (IOException e) {
            String message = "Error writting bytes into byte array output stream";
            LOGGER.error(message, e);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }
    }

    private ByteArrayOutputStream writeWidthSizeAndHeightSizeToStream(ByteArrayOutputStream byteArrayOutputStream, byte[] width, byte[] height) throws CompressorException {
        byte heightSize = (byte) height.length;
        byte widthSize = (byte) width.length;
        byteArrayOutputStream.write(widthSize);
        byteArrayOutputStream.write(heightSize);
        return byteArrayOutputStream;
    }

    private ByteArrayOutputStream writeWidthAndHeightToStream(ByteArrayOutputStream byteArrayOutputStream, byte[] width, byte[] height) throws CompressorException {
        try {
            byteArrayOutputStream.write(width);
            byteArrayOutputStream.write(height);
            return byteArrayOutputStream;
        } catch (IOException e) {
            String message = "Error writing bytes into byte array output stream";
            LOGGER.error(message, e);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }
    }

    private byte[] writeBufferToOutputStream(StringBuffer buffer, ByteArrayOutputStream byteArrayOutputStream) throws CompressorException {
        try {
            byteArrayOutputStream.write(new BigInteger(buffer.toString(), 2).toByteArray());
        } catch (IOException e) {
            String message = "Error writting buffer into byte array output stream";
            LOGGER.error(message, e);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private Matrix<Pixel> generateMatrix16x16(Matrix<Pixel> yCbCrMatrix, int y, int x) {
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
        return matrix16x16;
    }

    private Pair<Integer, CoefficientEnum> generateCoefficientEnum(int m) {
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

        return new Pair<>(n, coefficientEnum);
    }

    private StringBuffer entropyCoding(List<Matrix<Float>> quantizedBlocks, StringBuffer buffer, float[] lastDC) throws CompressorException {
        for (int m = 0; m < quantizedBlocks.size(); ++m) {
            // 5.1 Zig Zag Vector
            float[] zigZagValues = zigZagComponent.makeZigZag(quantizedBlocks.get(m));

            Pair<Integer, CoefficientEnum> coefficientResponse = generateCoefficientEnum(m);
            int n = coefficientResponse.getKey();
            CoefficientEnum coefficientEnum = coefficientResponse.getValue();

            // 5.2 Huffman Encoding
            float dpcmDC = zigZagValues[0] - lastDC[n];
            lastDC[n] = zigZagValues[0];
            buffer = huffmanComponent.encodeDC(Math.round(dpcmDC), buffer);
            buffer = processACValues(buffer, zigZagValues, coefficientEnum);
            buffer = addEOBToBuffer(buffer, coefficientEnum);
        }

        return buffer;
    }

    private StringBuffer processACValues(StringBuffer buffer, float[] zigZagValues, CoefficientEnum coefficientEnum) throws CompressorException {
        int countZerosEOB = checkEOB(zigZagValues);

        int numOfPreZeros = 0;
        for (int w = 1; w < (zigZagValues.length - countZerosEOB); ++w) {
            if (numOfPreZeros > 15) {
                buffer = addZRLToBuffer(buffer, coefficientEnum);
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
        return buffer;
    }

    private int checkEOB(float[] zigZagValues) {
        int countZerosEOB = 0;
        boolean foundEOB = false;
        for (int w = zigZagValues.length - 1; w >= 0 && !foundEOB; --w) {
            if (zigZagValues[w] == 0) {
                ++countZerosEOB;
            } else {
                foundEOB = true;
            }
        }
        return countZerosEOB;
    }

    private StringBuffer addZRLToBuffer(StringBuffer buffer, CoefficientEnum coefficientEnum) {
        if (coefficientEnum.equals(CoefficientEnum.LUMINANCE)) {
            buffer.append("11111111001");
        } else {
            buffer.append("1111111010");
        }
        return buffer;
    }

    private StringBuffer addEOBToBuffer(StringBuffer buffer, CoefficientEnum coefficientEnum) {
        if (coefficientEnum.equals(CoefficientEnum.LUMINANCE)) {
            buffer.append("1010");
        } else {
            buffer.append("00");
        }
        return buffer;
    }

    private List<Matrix<Float>> generateDctLuminance(MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        List<Matrix<Float>> blocksOf8x8 = new LinkedList<>();
        for (int m = 0; m < macroBlockYCbCr.getyBlocks().size(); ++m) {
            blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getyBlocks().get(m)));
        }
        return blocksOf8x8;
    }

    private List<Matrix<Float>> generateDctBlueChrominance(List<Matrix<Float>> blocksOf8x8, MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCbBlock()));
        return blocksOf8x8;
    }

    private List<Matrix<Float>> generateDctRedChrominance(List<Matrix<Float>> blocksOf8x8, MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        blocksOf8x8.add(dctComponent.applyDCT(macroBlockYCbCr.getCrBlock()));
        return blocksOf8x8;
    }

    private List<Matrix<Float>> applyDCT(MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        List<Matrix<Float>> blocksOf8x8 = generateDctLuminance(macroBlockYCbCr);
        blocksOf8x8 = generateDctBlueChrominance(blocksOf8x8, macroBlockYCbCr);
        return generateDctRedChrominance(blocksOf8x8, macroBlockYCbCr);
    }

    private List<Matrix<Float>> quantize(List<Matrix<Float>> blocksOf8x8) throws CompressorException {
        List<Matrix<Float>> quantizedBlocks = new LinkedList<>();
        for (int n = 0; n < blocksOf8x8.size(); ++n) {
            quantizedBlocks.add(quantizationComponent.quantizeMatrix(blocksOf8x8.get(n)));
        }
        return quantizedBlocks;
    }

    private Pair<Integer, List<Integer>> unprocessDCValue(int k, int[] lastDC, int i, int numOfBits, StringBuffer dataBuffer) throws CompressorException {
        List<Integer> zigZagValues = new LinkedList<>();
        String columnBinary = dataBuffer.substring(k, k + numOfBits);

        if (numOfBits == 0) {
            columnBinary = dataBuffer.substring(k, k + 1);
        }

        int dc = huffmanComponent.decodeCoefficient(numOfBits, Integer.parseInt(columnBinary, 2));
        int n = chooseNValue(i);

        zigZagValues.add(dc + lastDC[n]);
        lastDC[n] = dc + lastDC[n];
        if (numOfBits == 0) {
            ++k;
        } else {
            k += numOfBits;
        }

        return new Pair<>(k, zigZagValues);
    }

    private int chooseNValue(int i) {
        if (i <= 3) {
            return 0;
        } else if (i == 4) {
            return 1;
        } else {
            return 2;
        }
    }

    private Pair<Integer, List<Integer>> unprocessACValues(StringBuffer dataBuffer, int k, int i, List<Integer> zigZagValues) throws CompressorException {
        boolean endOfBlock = false;
        for (int j = 0; j < 63 && !endOfBlock; ++j) {
            StringBuffer workingBuffer = new StringBuffer();
            Pair<Integer, Integer> preZerosAndRow = new Pair<>(-1, -1);
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

                String columnBinary = dataBuffer.substring(k, k + preZerosAndRow.getValue());

                int ac = huffmanComponent.decodeCoefficient(preZerosAndRow.getValue(), Integer.parseInt(columnBinary, 2));

                zigZagValues.add(ac);
                k += preZerosAndRow.getValue();
            }
        }

        return new Pair<>(k, zigZagValues);
    }

    private JpegResponse readJpegFile(byte[] data) throws CompressorException {
        int magicNumber = Integer.parseInt(new BigInteger(new byte[]{data[0]}).toString(2), 2);
        String widthSizeBinary = new BigInteger(new byte[]{data[1]}).toString(2);
        int widthNumOfBytes = Integer.parseInt(widthSizeBinary, 2);
        String heightSizeBinary = new BigInteger(new byte[]{data[2]}).toString(2);
        int heightNumOfBytes = Integer.parseInt(heightSizeBinary, 2);

        byte[] widthBytes = Arrays.copyOfRange(data, 3, 3 + widthNumOfBytes);
        String widthBinary = new BigInteger(widthBytes).toString(2);
        byte[] heightBytes = Arrays.copyOfRange(data, 3 + widthNumOfBytes, 3 + widthNumOfBytes + heightNumOfBytes);
        String heightBinary = new BigInteger(heightBytes).toString(2);

        int width;
        int height;
        try {
            width = Integer.parseInt(widthBinary, 2);
            height = Integer.parseInt(heightBinary, 2);
        } catch (NumberFormatException e) {
            String message = "Failure to parse binary width or height to its integer value";
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.READ_JPEG_FAILURE);
        }

        return new JpegResponse(magicNumber, widthNumOfBytes, heightNumOfBytes, width, height);
    }

    private MacroBlockYCbCr undoDctProcess(List<Matrix<Integer>> blocksOf8x8) throws CompressorException {
        MacroBlockYCbCr macroBlockYCbCr = generateUndoDctLuminance(blocksOf8x8);
        macroBlockYCbCr = generateUndoDctBlueChrominance(blocksOf8x8, macroBlockYCbCr);
        return generateUndoDctRedChrominance(blocksOf8x8, macroBlockYCbCr);
    }

    private MacroBlockYCbCr generateUndoDctLuminance(List<Matrix<Integer>> blocksOf8x8) throws CompressorException {
        MacroBlockYCbCr macroBlockYCbCr = new MacroBlockYCbCr();
        for (int m = 0; m < 4; ++m) {
            macroBlockYCbCr.addYBlock(dctComponent.undoDCT(blocksOf8x8.get(m)));
        }
        return macroBlockYCbCr;
    }

    private MacroBlockYCbCr generateUndoDctBlueChrominance(List<Matrix<Integer>> blocksOf8x8, MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        macroBlockYCbCr.setCbBlock(dctComponent.undoDCT(blocksOf8x8.get(4)));
        return macroBlockYCbCr;
    }

    private MacroBlockYCbCr generateUndoDctRedChrominance(List<Matrix<Integer>> blocksOf8x8, MacroBlockYCbCr macroBlockYCbCr) throws CompressorException {
        macroBlockYCbCr.setCrBlock(dctComponent.undoDCT(blocksOf8x8.get(5)));
        return macroBlockYCbCr;
    }

    private List<Matrix<Integer>> dequantize(List<Matrix<Integer>> quantizedBlocks) throws CompressorException {
        List<Matrix<Integer>> blocksOf8x8 = new LinkedList<Matrix<Integer>>();
        for (int m = 0; m < quantizedBlocks.size(); ++m) {
            blocksOf8x8.add(quantizationComponent.dequantizeMatrix(quantizedBlocks.get(m)));
        }
        return blocksOf8x8;
    }

    private Matrix<Pixel> reconstructTotalMatrix(int width, int height, List<Matrix<Pixel>> blocksOfPixelMatrix16x16) {
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);
        int originS = 0;
        int originM = 0;
        int s;
        int m = 0;
        for (int n = 0; n < blocksOfPixelMatrix16x16.size(); ++n) {
            s = originS;
            for (int y = 0; (y < Math.min(16, height)) && (s < height); ++y, ++s) {
                m = originM;
                for (int x = 0; (x < Math.min(16, width)) && (m < width); ++x, ++m) {
                    yCbCrMatrix.setElementAt(blocksOfPixelMatrix16x16.get(n).getElementAt(y, x), s, m);
                }
            }

            if (m == width) {
                originM = 0;
                originS += 16;
            } else {
                originM += 16;
            }
        }
        return yCbCrMatrix;
    }
}

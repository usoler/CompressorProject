package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.components.*;
import domain.dataObjects.CoefficientEnum;
import domain.dataObjects.Pixel;
import domain.dataObjects.PpmFile;
import domain.dataObjects.PpmResponse;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// Compresion en carpetas
// Diseño en 3 capas: presentacion y datos
// Swing
// Arreglar JPEG
// Comprobar que funciona sin intellij/compilacion

// DONE: Javadoc
// DONE: P6 lectura
// TODO: P6 escritura
// TODO: Sustituir Pair !!!.

// DONE: Resolver bug snail. Fallo debido a tabla huffman
// DONE: Permitir lectura de ficheros ppm con comentarios. (Hacer un barrido inicial con regex desde # hasta \n replace por "")
// DONE: Bug star_field. Fallo debido a obtener mal width y height.
// DONE: Permitir lectura de cualquier tamaño de fichero. Duplicar ultima fila y ultima columna hasta ser multiplo de 16
// TODO: bug restructuring images not mod 16
// TODO: fix unit test in PPM component
// TODO: maybe bug with feep image ??.
// TODO: bug with sines image

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
        // ENCODING WITH JPEG
        LOGGER.info("Encoding file with JPEG algorithm");
        String file = new String(data, StandardCharsets.US_ASCII);

        float[] lastDC = new float[]{0, 0, 0}; // Y, Cb, Cr
        StringBuffer buffer = new StringBuffer();

        // 0. Read PPM file
        PpmResponse ppmResponse = ppmComponent.readPpmFile(data);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] height = BigInteger.valueOf(ppmResponse.getHeight()).toByteArray();
        byte[] width = BigInteger.valueOf(ppmResponse.getWidth()).toByteArray();
        byte heightSize = (byte) height.length;
        byte widthSize = (byte) width.length;
        //byteArrayOutputStream.write(heightSize);
        byteArrayOutputStream.write(widthSize);
        byteArrayOutputStream.write(heightSize);

        try {
            //byteArrayOutputStream.write(height);
            byteArrayOutputStream.write(width); // TODO: check if it works
            byteArrayOutputStream.write(height);
        } catch (IOException ex) {
            String message = "Error writting bytes into byte array output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }

        // 1. Color conversion
        Matrix<Pixel> yCbCrMatrix = conversorYCbCrComponent.convertFromRGB(ppmResponse.getMatrix());

        // 2. Downsampling
        int numOfMacroBlockByRow = yCbCrMatrix.getNumberOfColumns() / 16;
        int numOfMacroBlockByColumn = yCbCrMatrix.getNumberOfRows() / 16;

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

                MacroBlockYCbCr macroBlockYCbCr = samplingComponent.downsampling(matrix16x16);

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
                for (int m = 0; m < quantizedBlocks.size(); ++m) {
                    // 5.1 Zig Zag Vector
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

                    // 5.2 Huffman Encoding
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

        try {
            byteArrayOutputStream.write(new BigInteger(buffer.toString(), 2).toByteArray());
        } catch (IOException ex) {
            String message = "Error writting buffer into byte array output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }

        LOGGER.info("JPEG algorithm encode finished");
        return byteArrayOutputStream.toByteArray();

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
        // DECODING WITH JPEG
        LOGGER.info("Decoding file with JPEG algorithm");
        int[] lastDC = new int[]{0, 0, 0}; // Y, Cb, Cr
        List<Matrix<Pixel>> blocksOfPixelMatrix16x16 = new LinkedList<Matrix<Pixel>>();

        // 0. Read JPEG file
        byte[] widthSize = {data[0]};
        String widthSizeBinary = new BigInteger(widthSize).toString(2);
        int widthNumOfBytes = Integer.parseInt(widthSizeBinary, 2);
        byte[] heightSize = {data[1]};
        String heightSizeBinary = new BigInteger(heightSize).toString(2);
        int heightNumOfBytes = Integer.parseInt(heightSizeBinary, 2);

        byte[] widthBytes = Arrays.copyOfRange(data, 2, 2 + widthNumOfBytes);
        String widthBinary = new BigInteger(widthBytes).toString(2);
        byte[] heightBytes = Arrays.copyOfRange(data, 2 + widthNumOfBytes, 2 + widthNumOfBytes + heightNumOfBytes);
        String heightBinary = new BigInteger(heightBytes).toString(2);

        StringBuffer dataBuffer = new StringBuffer(new BigInteger(Arrays.copyOfRange(data, 2 + heightNumOfBytes + widthNumOfBytes, data.length)).toString(2));
        StringBuffer workingBuffer = new StringBuffer();

        // 1. Entropy decoding
        // 1.1 Huffman decoding
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

            if (numOfBits == 0) {
                columnBinary = dataBuffer.substring(k, k + 1);
            }
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
            if (numOfBits == 0) {
                ++k;
            } else {
                k += numOfBits;
            }

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

            // 1.2 Undo Zig Zag Vector
            quantizedBlocks.add(zigZagComponent.undoZigZag(zigZagValues));
            if (i == 5) {
                i = 0;

                // 2. Desquantization
                //LOGGER.debug("Undo desquantization");
                List<Matrix<Integer>> blocksOf8x8 = new LinkedList<Matrix<Integer>>();
                for (int m = 0; m < quantizedBlocks.size(); ++m) {
                    blocksOf8x8.add(quantizationComponent.dequantizeMatrix(quantizedBlocks.get(m)));
                }

                // 3. Undo DCT
                //LOGGER.debug("Undo DCT");
                // Y
                MacroBlockYCbCr macroBlockYCbCr = new MacroBlockYCbCr();
                for (int m = 0; m < 4; ++m) {
                    macroBlockYCbCr.addYBlock(dctComponent.undoDCT(blocksOf8x8.get(m)));
                }

                // Cb
                macroBlockYCbCr.setCbBlock(dctComponent.undoDCT(blocksOf8x8.get(4)));

                // Cr
                macroBlockYCbCr.setCrBlock(dctComponent.undoDCT(blocksOf8x8.get(5)));

                // 4. Undo downsampling
                Matrix<Pixel> yCbCrMatrix16x16 = samplingComponent.upsampling(macroBlockYCbCr);
                blocksOfPixelMatrix16x16.add(yCbCrMatrix16x16);

                quantizedBlocks = new LinkedList<Matrix<Integer>>();
            } else {
                ++i;
            }

            if (k == dataBuffer.length()) {
                finish = true;
            }
        }

        // 5. Reconstuct Total Matrix
        // TODO: check if it works
        int height = Integer.parseInt(heightBinary, 2);
        int width = Integer.parseInt(widthBinary, 2);
        Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);
        int originS = 0;
        int originM = 0;
        int s = 0;
        int m = 0;
        for (int n = 0; n < blocksOfPixelMatrix16x16.size(); ++n) {
            s = originS;
            for (int y = 0; y < Math.min(16, height); ++y, ++s) {
                m = originM;
                for (int x = 0; x < Math.min(16, width); ++x, ++m) {
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

        // 6. Undo Color Conversion
        Matrix<Pixel> rgbMatrix = conversorYCbCrComponent.convertToRGB(yCbCrMatrix);

        // 7. Write PPM file
        String response = ppmComponent.writePpmFile(Integer.parseInt(heightBinary, 2), Integer.parseInt(widthBinary, 2), rgbMatrix);

        LOGGER.info("JPEG algorithm decode finished");
        return response.getBytes();
    }
}

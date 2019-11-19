package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;
import domain.components.*;
import domain.dataObjects.CoefficientEnum;
import domain.dataObjects.Pixel;
import domain.dataStructure.MacroBlockYCbCr;
import domain.dataStructure.Matrix;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


// TODO: Javadoc
// TODO: Sustituir Pair ??.
// TODO: Refactor tablas huffman a arrays ??.

// DONE: Resolver bugs (Posible fallo ZRL...).
// DONE: Permitir lectura de ficheros ppm con comentarios. (Hacer un barrido inicial con regex desde # hasta \n replace por "")
// TODO: Bug star_field
// TODO: Permitir lectura de cualquier tamaño de fichero. Duplicar ultima fila y ultima columna hasta ser multiplo de 16

public class Jpeg implements AlgorithmInterface {
    private static final PpmComponent ppmComponent = new PpmComponent();
    private static final ConversorYCbCrComponent conversorYCbCrComponent = new ConversorYCbCrComponent();
    private static final DownsamplingComponent downsamplingComponent = new DownsamplingComponent();
    private static final DCTComponent dctComponent = new DCTComponent();
    private static final QuantizationComponent quantizationComponent = new QuantizationComponent();
    private static final ZigZagComponent zigZagComponent = new ZigZagComponent();
    private static final HuffmanComponent huffmanComponent = new HuffmanComponent();
    private static final Logger LOGGER = LoggerFactory.getLogger(Jpeg.class);

    @Override
    public byte[] encode(byte[] data) throws IOException {
        try {
            // ENCODING WITH JPEG
            System.out.println("Encoding file with JPEG");
            String file = new String(data, StandardCharsets.UTF_8);
            StringBuffer testBuffer = new StringBuffer();

            int cont = 0;
            float[] lastDC = new float[]{0, 0, 0}; // Y, Cb, Cr
            StringBuffer buffer = new StringBuffer();

            // 0. Read PPM file
            Matrix<Pixel> rgbMatrix = ppmComponent.readPpmFile(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] height = BigInteger.valueOf(rgbMatrix.getNumberOfColumns()).toByteArray();
            byte[] width = BigInteger.valueOf(rgbMatrix.getNumberOfRows()).toByteArray();
            byte heightSize = (byte) height.length;
            byte widthSize = (byte) width.length;
            byteArrayOutputStream.write(heightSize);
            byteArrayOutputStream.write(widthSize);
            byteArrayOutputStream.write(height);
            byteArrayOutputStream.write(width);

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
                        if (cont == 410) {
                            String stop = "stop";
                        }
                        float dpcmDC = zigZagValues[0] - lastDC[n];
                        lastDC[n] = zigZagValues[0];
                        ++cont;

                        buffer = huffmanComponent.encodeDC(Math.round(dpcmDC), buffer);
                        if (cont == 410 + 1) {
                            testBuffer = huffmanComponent.encodeDC(Math.round(dpcmDC), testBuffer);
                        }

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

                        int cont2 = 0;
                        if (cont == 410 + 1) {
                            cont2 = 1;
                        }
                        int numOfPreZeros = 0;
                        for (int w = 1; w < (zigZagValues.length - countZerosEOB); ++w) {
                            if (cont2 > 0) {
                                ++cont2;
                            }
                            if (cont2 == 32) {
                                String stop = "stop";
                            }
                            if (numOfPreZeros > 15) { // Catch ZRL case
                                LOGGER.debug("ZRL !!!");
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
                                    if (cont == 410 + 1) {
                                        testBuffer = huffmanComponent.encodeAC(Math.round(valueAC), numOfPreZeros, coefficientEnum, testBuffer);
                                    }
                                    numOfPreZeros = 0;
                                } else {
                                    ++numOfPreZeros;
                                }
                            }
                        }

                        // Set EOB
                        if (coefficientEnum.equals(CoefficientEnum.LUMINANCE)) {
                            if (cont == 410 + 1) {
                                testBuffer.append("1010");
                            }
                            buffer.append("1010");
                        } else {
                            buffer.append("00");
                        }
                    }

                    x += 16;
                }

                y += 16;
            }

            byteArrayOutputStream.write(new BigInteger(buffer.toString(), 2).toByteArray());
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] decode(byte[] data) throws UnsupportedEncodingException {
        try {
            // DECODING WITH JPEG
            System.out.println("Decoding file with JPEG");
            int cont = 0;
            int[] lastDC = new int[]{0, 0, 0}; // Y, Cb, Cr
            List<Matrix<Pixel>> blocksOfPixelMatrix16x16 = new LinkedList<Matrix<Pixel>>();
            StringBuffer testBuffer = new StringBuffer();

            // 0. Read JPEG file
            byte[] heightSize = {data[0]};
            String heightSizeBinary = new BigInteger(heightSize).toString(2);
            int heightNumOfBytes = Integer.parseInt(heightSizeBinary, 2);
            byte[] widthSize = {data[1]};
            String widthSizeBinary = new BigInteger(widthSize).toString(2);
            int widthNumOfBytes = Integer.parseInt(widthSizeBinary, 2);

            byte[] heightBytes = Arrays.copyOfRange(data, 2, 2 + heightNumOfBytes);
            String heightBinary = new BigInteger(heightBytes).toString(2);
            byte[] widthBytes = Arrays.copyOfRange(data, 2 + heightNumOfBytes, 2 + heightNumOfBytes + widthNumOfBytes);
            String widthBinary = new BigInteger(widthBytes).toString(2);

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
                LOGGER.debug("Contador: " + cont);
                if (cont == 410) {
                    String stop = "stop";
                }
                ++cont;
                List<Integer> zigZagValues = new LinkedList<Integer>();
                int numOfBits = -1;
                while (numOfBits == -1) {
                    workingBuffer.append(dataBuffer.charAt(k));
                    if (cont == 410 + 1) {
                        testBuffer.append(dataBuffer.charAt(k));
                    }
                    numOfBits = huffmanComponent.getNumOfBitsOfColumn(workingBuffer.toString());
                    ++k;
                }

                String columnBinary = dataBuffer.substring(k, k + numOfBits);
                if (cont == 410 + 1) {
                    testBuffer.append(dataBuffer.substring(k, k + numOfBits));
                }

                if (numOfBits == 0) {
                    columnBinary = dataBuffer.substring(k, k + 1);
                    if(cont == 410 + 1){
                        testBuffer.append(dataBuffer).substring(k, k + 1);
                    }
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
                int cont2 = 0;
                if (cont == 410 + 1) {
                    cont2 = 1;
                }
                boolean endOfBlock = false;
                for (int j = 0; j < 63 && !endOfBlock; ++j) {
                    if(cont2 == 32){
                        String stop = "stop";
                    }
                    if (cont2 > 0) {
                        LOGGER.debug("Cont2: " + (cont2 - 1));
                        ++cont2;
                    }
                    workingBuffer = new StringBuffer();
                    Pair<Integer, Integer> preZerosAndRow = new Pair<Integer, Integer>(-1, -1);
                    while (preZerosAndRow.getKey() == -1) {
                        workingBuffer.append(dataBuffer.charAt(k));
                        if(cont == 410 + 1){
                            testBuffer.append(dataBuffer.charAt(k));
                        }
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
                        if(cont == 410 + 1){
                            testBuffer.append(dataBuffer.substring(k, k + preZerosAndRow.getValue()));
                        }
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
                        blocksOf8x8.add(quantizationComponent.desquantizeMatrix(quantizedBlocks.get(m)));
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
                    Matrix<Pixel> yCbCrMatrix16x16 = downsamplingComponent.upsampling(macroBlockYCbCr);
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
            int height = Integer.parseInt(heightBinary, 2);
            int width = Integer.parseInt(widthBinary, 2);
            Matrix<Pixel> yCbCrMatrix = new Matrix<Pixel>(height, width, new Pixel[height][width]);
            int originS = 0;
            int originM = 0;
            int s = 0;
            int m = 0;
            for (int n = 0; n < blocksOfPixelMatrix16x16.size(); ++n) {
                s = originS;
                for (int y = 0; y < 16; ++y, ++s) {
                    m = originM;
                    for (int x = 0; x < 16; ++x, ++m) {
                        yCbCrMatrix.setElementAt(blocksOfPixelMatrix16x16.get(n).getElementAt(y, x), s, m); // Check if y and x are correct
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

            return response.getBytes();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new UnsupportedEncodingException(e.getMessage());
        }
    }
}

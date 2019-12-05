package domain.components;

import domain.dataObjects.Pixel;
import domain.dataObjects.PpmFile;
import domain.dataObjects.PpmResponse;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PpmComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PpmComponent.class);

    /**
     * Reads a PPM file given a byte array
     *
     * @param data the PPM file byte array
     * @return a {@link PpmResponse} with the ppm processed data
     * @throws CompressorException if any error occurs
     */
    public PpmResponse readPpmFile(byte[] data) throws CompressorException {
        checkData(data);
        PpmFile ppmFile = readPpmHeader(data);
        return processPpmFile(ppmFile, ppmFile.getWidth(), ppmFile.getHeight());
    }

    /**
     * Write a PPM file given a RGB Matrix
     *
     * @param magicNumber the magic number of the image
     * @param height      the height of the image
     * @param width       the width of the image
     * @param rgbMatrix   the RGB Matrix to write into the PPM file
     * @return the PPM file obtained by the RGB Matrix
     * @throws CompressorException if any error occurs
     */
    public byte[] writePpmFile(int magicNumber, int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        checkRgbMatrix(rgbMatrix);
        return generatePpmFile(magicNumber, height, width, rgbMatrix);
    }

    private byte[] generatePpmFile(int magicNumber, int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        switch (magicNumber) {
            case 3:
                return writePpmFileP3(height, width, rgbMatrix);
            case 6:
                return writePpmFileP6(height, width, rgbMatrix);
            default:
                String message = "PPM magic number is not supported: '{}'";
                LOGGER.error(message, magicNumber);
                throw new CompressorException(message, CompressorErrorCode.PPM_COMPATIBILITY_FAILURE);
        }
    }

    private PpmFile readPpmHeader(byte[] data) throws CompressorException {
        String[] values = new String[4];
        int k = 0;
        for (int i = 0; (i < 4) && (k < data.length); ++i) {
            StringBuffer workingBuffer = new StringBuffer();

            k = skipCommentLines(data, k, (char) data[k]);

            char character = (char) data[k];

            // Get value
            while ((character != '\n') && (character != ' ')) {
                workingBuffer.append(character);
                ++k;
                character = (char) data[k];
            }

            ++k;
            values[i] = workingBuffer.toString();
        }
        int width;
        int height;
        try {
            width = Integer.parseInt(values[1]);
            height = Integer.parseInt(values[2]);
        } catch (NumberFormatException e) {
            String message = "Failure to parse width or height to integer value";
            LOGGER.error(message, e);
            throw new CompressorException(message, e, CompressorErrorCode.PPM_PARSE_FAILURE);
        }

        return new PpmFile(values[0], width, height, getContentOfColorImage(data, k));
    }

    private int skipCommentLines(byte[] data, int k, char character) {
        if (character == '#') {
            while ((k < data.length) && ((character != '\n') || ((char) data[k + 1] == '#'))) {
                ++k;
                character = (char) data[k];
            }
            ++k;
        }
        return k;
    }

    private PpmResponse readPpmFileP3(String[] elements, int originalWidth, int originalHeight, Matrix<Pixel> pixels) {
        int width = pixels.getNumberOfColumns();
        int height = pixels.getNumberOfRows();

        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width * 3; j += 3) {
                if (i < originalHeight && j < originalWidth * 3) {
                    pixels.setElementAt(generatePixel(elements, k), i, j / 3);
                    k += 3;
                } else {
                    pixels.setElementAt(new Pixel(pixels.getElementAt(Math.min(i, originalHeight - 1), Math.min(j, originalWidth - 1))), i, j / 3);
                }
            }
        }

        return new PpmResponse(3, originalWidth, originalHeight, pixels);
    }

    private PpmResponse readPpmFileP6(byte[] data, int originalWidth, int originalHeight, Matrix<Pixel> pixels) {
        int width = pixels.getNumberOfColumns();
        int height = pixels.getNumberOfRows();

        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (i < originalHeight && j < originalWidth) {
                    pixels.setElementAt(generatePixel(data, k), i, j);
                    k += 3;
                } else {
                    pixels.setElementAt(new Pixel(pixels.getElementAt(Math.min(i, originalHeight - 1), Math.min(j, originalWidth - 1))), i, j);
                }
            }
        }

        return new PpmResponse(6, originalWidth, originalHeight, pixels);
    }

    private void checkData(byte[] data) throws CompressorException {
        if (Objects.isNull(data)) {
            String message = "Param data could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }
    }

    private byte[] writePpmFileP3(int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        String response = "P3\n" + width + " " + height + "\n255\n";
        StringBuffer buffer = new StringBuffer(response);
        int cont = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);
                checkPixel(i, j, pixel);
                String values = generateTriColorFrom(pixel);

                if (cont == 3) {
                    if ((i + 1 < height) || (j + 1 < width)) {
                        values = values + "\n";
                        cont = 0;
                    }
                } else {
                    values = values + " ";
                    ++cont;
                }

                buffer.append(values);
            }
        }
        buffer.append('\n');
        return buffer.toString().getBytes();
    }

    private byte[] writePpmFileP6(int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        String response = "P6\n" + width + " " + height + "\n255\n";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            buffer.write(response.getBytes());
        } catch (IOException ex) {
            String message = "Error writting buffer into byte array output stream";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);
                checkPixel(i, j, pixel);
                buffer = writeTriColorFrom(pixel, buffer);
            }
        }
        return buffer.toByteArray();
    }

    private void checkPixel(int i, int j, Pixel pixel) throws CompressorException {
        if (Objects.isNull(pixel)) {
            String message = String.format("Pixel from param RGB Matrix at position (%s,%s) could not be " +
                    "null, empty or blank", i, j);
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }
    }

    private void checkRgbMatrix(Matrix<Pixel> rgbMatrix) throws CompressorException {
        if (Objects.isNull(rgbMatrix)) {
            String message = "Param RGB Matrix could not be null";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.WRITE_PPM_FAILURE);
        }
    }

    private Pixel generatePixel(String[] elements, int k) {
        float red = Float.parseFloat(elements[k]);
        float green = Float.parseFloat(elements[k + 1]);
        float blue = Float.parseFloat(elements[k + 2]);

        return new Pixel(red, green, blue);
    }

    private Pixel generatePixel(byte[] data, int k) {
        float red = data[k] & 0xff;
        float green = data[k + 1] & 0xff;
        float blue = data[k + 2] & 0xff;

        return new Pixel(red, green, blue);
    }

    private String generateTriColorFrom(Pixel pixel) {
        int red = Math.round(pixel.getX());
        int green = Math.round(pixel.getY());
        int blue = Math.round(pixel.getZ());

        return red + " " + green + " " + blue;
    }

    private ByteArrayOutputStream writeTriColorFrom(Pixel pixel, ByteArrayOutputStream buffer) {
        int red = Math.round(pixel.getX());
        int green = Math.round(pixel.getY());
        int blue = Math.round(pixel.getZ());

        buffer.write(red);
        buffer.write(green);
        buffer.write(blue);

        return buffer;
    }

    private byte[] getContentOfColorImage(byte[] data, int k) {
        byte[] content = new byte[data.length - k];
        System.arraycopy(data, k, content, 0, data.length - k);
        return content;
    }

    private Matrix<Pixel> initPixelMatrix(int width, int height) {
        int upgradedWidth = upgradeToMod16(width);
        int upgradedHeight = upgradeToMod16(height);

        return new Matrix<Pixel>(upgradedHeight, upgradedWidth, new Pixel[upgradedHeight][upgradedWidth]);
    }

    private int upgradeToMod16(int value) {
        while (value % 16 != 0) {
            ++value;
        }
        return value;
    }

    private PpmResponse processPpmFile(PpmFile ppmFile, int width, int height) throws CompressorException {
        Matrix<Pixel> pixels = initPixelMatrix(width, height);

        if ("P3".equals(ppmFile.getMagicNumber())) {
            return readPpmFileP3(new String(ppmFile.getImageData(), StandardCharsets.US_ASCII).split("\\s+"), width, height, pixels);
        } else if ("P6".equals(ppmFile.getMagicNumber())) {
            return readPpmFileP6(ppmFile.getImageData(), width, height, pixels);
        } else {
            String message = "PPM magic number is not supported: '{}'";
            LOGGER.error(message, ppmFile.getMagicNumber());
            throw new CompressorException(message, CompressorErrorCode.PPM_COMPATIBILITY_FAILURE);
        }
    }
}

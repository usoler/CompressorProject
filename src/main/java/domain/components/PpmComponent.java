package domain.components;

import domain.dataObjects.Pixel;
import domain.dataObjects.PpmFile;
import domain.dataObjects.PpmResponse;
import domain.dataStructure.Matrix;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class PpmComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(PpmComponent.class);

    public PpmFile readPpmHeader(byte[] data) {
        PpmFile ppmFile = new PpmFile();

        String[] values = new String[4];
        int k = 0;
        for (int i = 0; (i < 4) && (k < data.length); ++i) {
            StringBuffer workingBuffer = new StringBuffer();
            char character = (char) data[k];

            // Skip comment lines
            if (character == '#') {
                while ((k < data.length) && ((character != '\n') || ((char) data[k + 1] == '#'))) {
                    ++k;
                    character = (char) data[k];
                }
                ++k;
            }

            // Get value
            while ((character != '\n') && (character != ' ')) {
                workingBuffer.append(character);
                ++k;
                character = (char) data[k];
            }

            ++k;
            values[i] = workingBuffer.toString();
        }
        byte[] content = new byte[data.length - k];
        System.arraycopy(data, k, content, 0, data.length - k);
        //return new PpmFile(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), Arrays.copyOfRange(data, k, data.length));
        return new PpmFile(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2]), content);
    }

    private PpmResponse readPpmFileP6(byte[] data, int originalWidth, int originalHeight, Matrix<Pixel> pixels) {
        //byte[] data = elements.getBytes();
        //StringBuffer dataBuffer = new StringBuffer(elements);

        int width = pixels.getNumberOfColumns();
        int height = pixels.getNumberOfRows();

        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                if (i < originalHeight && j < originalWidth) {
                    //float red = new Integer(dataBuffer.charAt(k)).floatValue();
                    //float green = new Integer(dataBuffer.charAt(k + 1)).floatValue();
                    //float blue = new Integer(dataBuffer.charAt(k + 2)).floatValue();
                    //int red = data[k] & 0xff;
                    //int red3 = data[k] + 256;
                    //float red2 = (float) data[k];
                    float red = data[k] & 0xff;
                    float green = data[k + 1] & 0xff;
                    float blue = data[k + 2] & 0xff;

                    Pixel pixel = new Pixel(red, green, blue);
                    pixels.setElementAt(pixel, i, j);

                    k += 3;
                } else {
                    pixels.setElementAt(new Pixel(pixels.getElementAt(Math.min(i, originalHeight - 1), Math.min(j, originalWidth - 1))), i, j);
                }
            }
        }

        return new PpmResponse(originalWidth, originalHeight, pixels);
    }

    // TODO: javadoc
    public PpmResponse readPpmFile(byte[] data) throws CompressorException {
        //checkData(data); // TODO: check data

        //String dataWithoutComments = removeComments(data);

        //String[] parts = dataWithoutComments.split("\\s+", 5);

        // ------------------------------------------
        // parts[0] -> magic number (P3/P6)
        // parts[1] -> image width
        // parts[2] -> image height
        // parts[3] -> color max value in a pixel       // ignored
        // parts[4] -> pixel information
        // ------------------------------------------

        PpmFile ppmFile = readPpmHeader(data);

        //int originalWidth = Integer.parseInt(parts[1]);
        //int originalHeight = Integer.parseInt(parts[2]);
        int originalWidth = ppmFile.getWidth();
        int originalHeight = ppmFile.getHeight();

        int width = originalWidth;
        int height = originalHeight;

        while (width % 16 != 0) {
            ++width;
        }

        while (height % 16 != 0) {
            ++height;
        }

        Matrix<Pixel> pixels = new Matrix<Pixel>(height, width, new Pixel[height][width]);

        if ("P3".equals(ppmFile.getMagicNumber())) {
            return readPpmFileP3(new String(ppmFile.getImageData(), StandardCharsets.US_ASCII).split("\\s+"), originalWidth, originalHeight, pixels);
        } else if ("P6".equals(ppmFile.getMagicNumber())) {
            //return readPpmFileP6(parts[4], originalWidth, originalHeight, pixels);
            return readPpmFileP6(ppmFile.getImageData(), originalWidth, originalHeight, pixels);
        } else {
            String message = "PPM magic number is not supported: '{}'";
            LOGGER.error(message, ppmFile.getMagicNumber());
            throw new CompressorException(message, CompressorErrorCode.PPM_COMPATIBILITY_FAILURE);
        }
    }

    private PpmResponse readPpmFileP3(String[] elements, int originalWidth, int originalHeight, Matrix<Pixel> pixels) {
        int width = pixels.getNumberOfColumns();
        int height = pixels.getNumberOfRows();

        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width * 3; j += 3) {
                if (i < originalHeight && j < originalWidth * 3) {
                    float red = Float.parseFloat(elements[k]);
                    float green = Float.parseFloat(elements[k + 1]);
                    float blue = Float.parseFloat(elements[k + 2]);

                    Pixel pixel = new Pixel(red, green, blue);
                    pixels.setElementAt(pixel, i, j / 3);

                    k += 3;
                } else {
                    pixels.setElementAt(new Pixel(pixels.getElementAt(Math.min(i, originalHeight - 1), Math.min(j, originalWidth - 1))), i, j / 3);
                }
            }
        }

        return new PpmResponse(originalWidth, originalHeight, pixels);
    }

    private void checkData(String data) throws CompressorException {
        if (Objects.isNull(data) || data.isEmpty() || data.trim().length() == 0) {
            String message = "Param data could not be null, empty or blank";
            LOGGER.error(message);
            throw new CompressorException(message, CompressorErrorCode.READ_PPM_FAILURE);
        }
    }

    private String removeComments(String data) {
        return data.replaceAll("#.*\n", "");
    }

    private String removeLines(String data) {
        return data.replaceAll("\t?\n?", "");
    }

    /**
     * Write a PPM file given a RGB Matrix
     *
     * @param height    the height of the image
     * @param width     the width of the image
     * @param rgbMatrix the RGB Matrix to write into the PPM file
     * @return the PPM file obtained by the RGB Matrix
     * @throws CompressorException if any error occurs
     */
    public String writePpmFile(int height, int width, Matrix<Pixel> rgbMatrix) throws CompressorException {
        checkRgbMatrix(rgbMatrix);

        String response = "P3\n" + width + " " + height + "\n255\n";
        StringBuffer buffer = new StringBuffer(response);
        int cont = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                Pixel pixel = rgbMatrix.getElementAt(i, j);

                checkPixel(i, j, pixel);

                int red = Math.round(pixel.getX());
                int green = Math.round(pixel.getY());
                int blue = Math.round(pixel.getZ());

                String values = red + " " + green + " " + blue;

                if (cont == 3) {
                    values = values + "\n";
                    cont = 0;
                } else {
                    values = values + " ";
                    ++cont;
                }

                buffer.append(values);
            }

            if (i < height - 1) {
                buffer.append("\n");
            }
        }
        return buffer.toString();
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
}

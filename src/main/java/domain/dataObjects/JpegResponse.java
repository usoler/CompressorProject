package domain.dataObjects;

import java.util.Objects;

public class JpegResponse {

    private int magicNumber;
    private int widthNumOfBytes;
    private int heightNumOfBytes;
    private int width;
    private int height;

    /**
     * Constructs a new {@link JpegResponse}
     *
     * @param magicNumber      the PPM magic number
     * @param widthNumOfBytes  the width of number of bytes
     * @param heightNumOfBytes the height of number of bytes
     * @param width            the image width
     * @param height           the image height
     */
    public JpegResponse(int magicNumber, int widthNumOfBytes, int heightNumOfBytes, int width, int height) {
        this.magicNumber = magicNumber;
        this.widthNumOfBytes = widthNumOfBytes;
        this.heightNumOfBytes = heightNumOfBytes;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the PPM magic number
     *
     * @return the ppm magic number
     */
    public int getMagicNumber() {
        return magicNumber;
    }

    /**
     * Gets the width of number of bytes
     *
     * @return the width of number of bytes
     */
    public int getWidthNumOfBytes() {
        return widthNumOfBytes;
    }

    /**
     * Gets the height of number of bytes
     *
     * @return the height of number of bytes
     */
    public int getHeightNumOfBytes() {
        return heightNumOfBytes;
    }

    /**
     * Gets the image width
     *
     * @return the image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the image height
     *
     * @return the image height
     */
    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpegResponse that = (JpegResponse) o;
        return magicNumber == that.magicNumber &&
                widthNumOfBytes == that.widthNumOfBytes &&
                heightNumOfBytes == that.heightNumOfBytes &&
                width == that.width &&
                height == that.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(magicNumber, widthNumOfBytes, heightNumOfBytes, width, height);
    }
}

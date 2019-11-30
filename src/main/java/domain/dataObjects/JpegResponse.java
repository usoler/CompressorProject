package domain.dataObjects;

import java.util.Objects;

public class JpegResponse {
    private int magicNumber;
    private int widthNumOfBytes;
    private int heightNumOfBytes;
    private int width;
    private int height;

    public JpegResponse(int magicNumber, int widthNumOfBytes, int heightNumOfBytes, int width, int height) {
        this.magicNumber = magicNumber;
        this.widthNumOfBytes = widthNumOfBytes;
        this.heightNumOfBytes = heightNumOfBytes;
        this.width = width;
        this.height = height;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public int getWidthNumOfBytes() {
        return widthNumOfBytes;
    }

    public int getHeightNumOfBytes() {
        return heightNumOfBytes;
    }

    public int getWidth() {
        return width;
    }

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

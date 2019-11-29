package domain.dataObjects;

public class PpmFile {
    private String magicNumber;
    private int width;
    private int height;
    private byte[] imageData;

    public PpmFile(String magicNumber, int width, int height, byte[] imageData) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.imageData = imageData;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getImageData() {
        return imageData;
    }
}

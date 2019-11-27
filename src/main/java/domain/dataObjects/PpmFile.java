package domain.dataObjects;

public class PpmFile {
    private String magicNumber;
    private int width;
    private int height;
    private byte[] imageData;

    public PpmFile() {
        // Empty
    }

    public PpmFile(String magicNumber, int width, int height, byte[] imageData) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.imageData = imageData;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}

package domain.dataObjects;

public class PpmFile {
    private String magicNumber;
    private int width;
    private int height;
    private byte[] imageData;

    /**
     * Constructs a new {@link PpmFile}
     *
     * @param magicNumber the PPM magic number
     * @param width       the PPM width
     * @param height      the PPM height
     * @param imageData   the PPM data bytes
     */
    public PpmFile(String magicNumber, int width, int height, byte[] imageData) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.imageData = imageData;
    }

    /**
     * Gets the PPM magic number
     *
     * @return the PPM magic number
     */
    public String getMagicNumber() {
        return magicNumber;
    }

    /**
     * Gets the PPM width
     *
     * @return the PPM width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the PPM height
     *
     * @return the PPM height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the PPM data bytes
     *
     * @return the PPM data bytes
     */
    public byte[] getImageData() {
        return imageData;
    }
}

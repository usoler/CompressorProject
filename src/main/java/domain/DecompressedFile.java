package domain;

public class DecompressedFile extends File {

    /**
     * Constructs a {@link DecompressedFile}
     *
     * @param data     the file data bytes
     * @param pathname the file pathname
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public DecompressedFile(byte[] data, String pathname, String name, int size, String format) {
        super(data, pathname, name, format, size);
    }
}

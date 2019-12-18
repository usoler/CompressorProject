package domain;

public class CompressedFile extends File {

    /**
     * Constructs a new {@link CompressedFile}
     *
     * @param data     the file data bytes
     * @param pathname the file pathname
     * @param name     the file name
     * @param size     the file size
     * @param format   the file format
     */
    public CompressedFile(byte[] data, String pathname, String name, int size, String format) {
        super(data, pathname, name, format, size);
    }
}

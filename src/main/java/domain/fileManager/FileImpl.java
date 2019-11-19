package domain.fileManager;

public class FileImpl {

    protected String pathname;
    private byte[] data;

    public FileImpl(byte[] i_data, String i_pathname) {
        data = i_data;
        pathname = i_pathname;
    }

    public byte[] getData() {
        return data;
    }

    public String getPathname() {
        return pathname;
    }
}

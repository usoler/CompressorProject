package domain.fileManager;

public class FileImpl {

    protected String pathname;
    private byte[] data;

    public FileImpl(byte[] data, String pathname) {
        this.data = data;
        this.pathname = pathname;
    }

    public byte[] getData() {
        return data;
    }

    public String getPathname() {
        return pathname;
    }
}

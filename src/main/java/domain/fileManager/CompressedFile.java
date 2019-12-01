package domain.fileManager;

import java.io.OutputStream;

public class CompressedFile extends FileImpl {
    private OutputStream outputStream;

    public CompressedFile(String pathname, OutputStream outputStream) {
        super(null, pathname);
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }
}

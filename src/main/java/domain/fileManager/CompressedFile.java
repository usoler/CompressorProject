package domain.fileManager;

import java.io.OutputStream;

public class CompressedFile extends FileImpl {

    private OutputStream outputStream;

    public CompressedFile( String i_pathname, OutputStream i_outputStream) {
        super(null, i_pathname);
        outputStream = i_outputStream;
    }

    public OutputStream getOutputStream()
    {
        return outputStream;
    }
}

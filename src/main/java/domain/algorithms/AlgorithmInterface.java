package domain.algorithms;

import java.io.*;

public interface AlgorithmInterface {
    public byte[] encode(byte[] file) throws IOException;

    public byte[] decode(byte[] file) throws UnsupportedEncodingException;
}

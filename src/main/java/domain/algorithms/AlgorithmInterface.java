package domain.algorithms;

import java.io.File;
import java.io.UnsupportedEncodingException;

public interface AlgorithmInterface {
    public byte[] encode(byte[] data) throws Exception;

    public String decode(String file) throws UnsupportedEncodingException;
}

package domain.algorithms;

import java.io.File;
import java.io.UnsupportedEncodingException;

public interface AlgorithmInterface {
    public String encode(String file) throws UnsupportedEncodingException;

    public String decode(String file) throws UnsupportedEncodingException;
}

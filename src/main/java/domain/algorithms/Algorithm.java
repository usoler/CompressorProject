package domain.algorithms;

import domain.algorithms.lossless.Lz78;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public String encodeFile(String file) throws UnsupportedEncodingException {
        return this.algorithmInterface.encode(file);
    }

    public String decodeFile(String file) throws UnsupportedEncodingException {
        return this.algorithmInterface.decode(file);
    }
}

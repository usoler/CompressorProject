package domain.algorithms;

import domain.algorithms.lossy.Jpeg;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Jpeg();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public byte[] encodeFile(byte[] file) throws Exception {
        return this.algorithmInterface.encode(file);
    }

    public byte[] decodeFile(byte[] file) throws Exception {
        return this.algorithmInterface.decode(file);
    }
}

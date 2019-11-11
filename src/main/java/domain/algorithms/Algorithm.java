package domain.algorithms;

import domain.algorithms.lossless.Lz78;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Algorithm {
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public byte[] encodeFile(byte[] file) throws IOException {
        return this.algorithmInterface.encode(file);
    }

    public byte[] decodeFile(byte[] file) throws UnsupportedEncodingException {
        return this.algorithmInterface.decode(file);
    }
}

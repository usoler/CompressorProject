package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Lzw extends Lz78 {

    public Lzw() {
        super();
    }

    @Override
    public byte[] encode(byte[] data) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
        return null;
    }

    @Override
    public byte[] decode(byte[] file) {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
        return null;
    }
}

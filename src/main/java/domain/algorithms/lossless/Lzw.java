package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

public class Lzw extends Lz78 {

    public Lzw() {
        super();
    }

    @Override
    public void encode(File file) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
    }

    @Override
    public void decode(File file) {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
    }
}

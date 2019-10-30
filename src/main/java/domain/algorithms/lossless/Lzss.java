package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

public class Lzss extends Lz {

    public Lzss() {
        // Empty
    }

    @Override
    public void encode(File file) {
        // ENCODING WITH LZSS
        System.out.println("Encoding file with LZSS");
    }

    @Override
    public void decode(File file) {
        // DECODING WITH LZSS
        System.out.println("Decoding file with LZSS");
    }
}

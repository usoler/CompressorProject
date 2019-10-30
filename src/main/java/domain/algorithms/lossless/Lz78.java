package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

public class Lz78 extends Lz {

    public Lz78() {
        // Empty
    }

    @Override
    public void encode(File file) {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
    }

    @Override
    public void decode(File file) {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
    }
}

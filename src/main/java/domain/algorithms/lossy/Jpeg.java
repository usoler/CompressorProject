package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

public class Jpeg implements AlgorithmInterface {

    @Override
    public void encode(String file) {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");
    }

    @Override
    public void decode(File file) {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");
    }
}

package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

public class Jpeg implements AlgorithmInterface {

    @Override
    public String encode(String file) {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");
        return null;
    }

    @Override
    public void decode(String file) {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");
    }
}

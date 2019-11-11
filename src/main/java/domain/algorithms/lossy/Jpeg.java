package domain.algorithms.lossy;

import domain.algorithms.AlgorithmInterface;

import java.io.FileOutputStream;

public class Jpeg implements AlgorithmInterface {

    @Override
    public byte[] encode(byte[] file) {
        // ENCODING WITH JPEG
        System.out.println("Encoding file with JPEG");
        return null;
    }

    @Override
    public byte[] decode(byte[] file) {
        // DECODING WITH JPEG
        System.out.println("Decoding file with JPEG");
        return null;
    }
}

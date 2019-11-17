package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Lzss extends Lz {

    public Lzss() {
        // Empty
    }

    @Override
    public byte[] encode(byte[] data) {
        // ENCODING WITH LZSS
        System.out.println("ENCODING FILE WITH LZSS");
        return null;
    }

    @Override
    public byte[] decode(byte[] data) {
        // DECODING WITH LZSS
        System.out.println("DECODING FILE WITH LZSS");
        return null;
    }
}

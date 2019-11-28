package domain.algorithms;

import domain.exception.CompressorException;

public class AlgortihmImplStub implements AlgorithmInterface {

    @Override
    public byte[] encode(byte[] file) throws CompressorException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[]{0, 1, 2, 3};
    }

    @Override
    public byte[] decode(byte[] file) throws CompressorException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[]{3, 2, 1, 0};
    }
}

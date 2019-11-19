package domain.algorithms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AlgortihmImplStub implements AlgorithmInterface {

    @Override
    public byte[] encode(byte[] file) throws IOException {
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[] {0, 1, 2, 3};
    }

    @Override
    public byte[] decode(byte[] file) throws UnsupportedEncodingException {
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new byte[] {3, 2, 1, 0};
    }
}

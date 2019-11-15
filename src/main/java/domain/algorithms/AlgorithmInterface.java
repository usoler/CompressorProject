package domain.algorithms;

public interface AlgorithmInterface {
    public byte[] encode(byte[] data) throws Exception;

    public byte[] decode(byte[] file) throws Exception;
}

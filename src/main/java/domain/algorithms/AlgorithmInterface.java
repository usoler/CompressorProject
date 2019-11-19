package domain.algorithms;

import domain.exception.CompressorException;

public interface AlgorithmInterface {
    public byte[] encode(byte[] file) throws CompressorException;

    public byte[] decode(byte[] file) throws CompressorException;
}

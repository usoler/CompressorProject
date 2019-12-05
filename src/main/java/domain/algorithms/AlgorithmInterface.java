package domain.algorithms;

import domain.exception.CompressorException;

public interface AlgorithmInterface {

    /**
     * Encodes the given file data
     *
     * @param data the file data to encode
     * @return the file data encoded
     * @throws CompressorException if any error occurs
     */
    byte[] encode(byte[] data) throws CompressorException;

    /**
     * Decodes the given file data
     *
     * @param data the file data to decode
     * @return the file data decoded
     * @throws CompressorException if any error occurs
     */
    byte[] decode(byte[] data) throws CompressorException;
}

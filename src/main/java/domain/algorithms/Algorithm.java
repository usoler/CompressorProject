package domain.algorithms;

import domain.algorithms.lossless.Lz78;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Algorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Algorithm.class);
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public byte[] encodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        LOGGER.info("Starts to encode");
        byte[] compressedFile = this.algorithmInterface.encode(data);
        LOGGER.info("Encode finished");
        return compressedFile;
    }

    public byte[] decodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        LOGGER.info("Starts to decode");
        byte[] decompressedFile = this.algorithmInterface.decode(data);
        LOGGER.info("Decode finished");
        return decompressedFile;
    }

    private void checkFile(byte[] file) throws CompressorException {
        if (Objects.isNull(file)) {
            String message = "Param file cannot be null";
            LOGGER.error(message);
            throw new CompressorException(message, new IllegalArgumentException(), CompressorErrorCode.ILLEGAL_NULL_FILE_FAILURE);
        }
    }
}

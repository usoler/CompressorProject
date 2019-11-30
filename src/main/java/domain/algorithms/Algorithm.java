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

    private static void printEncodeStatistics(long start, long end, float uncompressedSize, float compressedSize) {
        LOGGER.debug("Elapsed time: '{}'", end - start);
        showCompressionSpeed(start, end, uncompressedSize, compressedSize);
        showCompressionRatio(uncompressedSize, compressedSize);
        LOGGER.debug("Space savings: '{}'", (1.0f - compressedSize / uncompressedSize) * 100.0f);
    }

    private static void showCompressionSpeed(long start, long end, float uncompressedSize, float compressedSize) {
        if ((end - start) == 0 && (uncompressedSize - compressedSize) == 0) {
            LOGGER.debug("Compression speed: 0 bytes/ms");
        } else {
            LOGGER.debug("Compression speed: '{}'", (uncompressedSize - compressedSize) / (end - start));
        }
    }

    private static void showCompressionRatio(float uncompressedSize, float compressedSize) {
        if (uncompressedSize == 0 && compressedSize == 0) {
            LOGGER.debug("Compression ratio: 0");
        } else {
            LOGGER.debug("Compression ratio: '{}'", uncompressedSize / compressedSize);
        }
    }

    private static void printDecodeStatistics(long start, long end, float compressedSize, float decompressedSize) {
        System.out.printf("ELAPSED TIME: %d MS\n", end - start);
        if ((end - start) == 0 && (decompressedSize - compressedSize) == 0) {
            System.out.println("DECOMPRESSION SPEED: 0 BYTES/MS");
        } else {
            System.out.printf("DECOMPRESSION SPEED: %.2f BYTES/MS\n", (decompressedSize - compressedSize) / (end - start));
        }
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public byte[] encodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        int uncompressedSize = data.length;
        long start = System.currentTimeMillis();
        LOGGER.info("Starts to encode");
        byte[] compressedFile = this.algorithmInterface.encode(data);
        LOGGER.info("Encode finished");
        long end = System.currentTimeMillis();
        int compressedSize = compressedFile.length;
        LOGGER.debug("Showing the encode statistics:");
        printEncodeStatistics(start, end, uncompressedSize, compressedSize);
        return compressedFile;
    }

    public byte[] decodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        int compressedSize = data.length;
        long start = System.currentTimeMillis();
        LOGGER.info("Starts to decode");
        byte[] decompressedFile = this.algorithmInterface.decode(data);
        LOGGER.info("Decode finished");
        long end = System.currentTimeMillis();
        int decompressedSize = decompressedFile.length;
        LOGGER.debug("Showing the decode statistics:");
        printDecodeStatistics(start, end, compressedSize, decompressedSize);
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

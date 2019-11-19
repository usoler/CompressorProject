package domain.algorithms;

import domain.algorithms.lossless.Lz78;
import domain.exception.CompressorException;


public class Algorithm {
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    private static void printEncodeStatistics(long start, long end, float uncompressedSize, float compressedSize) {
        System.out.printf("ELAPSED TIME: %d MS\n", end - start);
        if ((end - start) == 0 && (uncompressedSize - compressedSize) == 0) {
            System.out.println("COMPRESSION SPEED: 0 BYTES/MS");
        } else {
            System.out.printf("COMPRESSION SPEED: %.2f BYTES/MS\n", (uncompressedSize - compressedSize) / (end - start));
        }
        if (uncompressedSize == 0 && compressedSize == 0) {
            System.out.println("COMPRESSION RATIO: 0");
        } else {
            System.out.printf("COMPRESSION RATIO: %.2f\n", uncompressedSize / compressedSize);
        }
        System.out.printf("SPACE SAVINGS: %.2f%%\n", (1.0f - compressedSize / uncompressedSize) * 100.0f);
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

    public byte[] encodeFile(byte[] file) throws CompressorException {
        int uncompressedSize = file.length;
        long start = System.currentTimeMillis();
        byte[] compressedFile = this.algorithmInterface.encode(file);
        long end = System.currentTimeMillis();
        int compressedSize = compressedFile.length;
        System.out.println("COMPRESSION ENDED");
        System.out.println("STATISTICS OF THE COMPRESSION:");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        printEncodeStatistics(start, end, uncompressedSize, compressedSize);
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        return compressedFile;
    }

    public byte[] decodeFile(byte[] file) throws CompressorException {
        int compressedSize = file.length;
        long start = System.currentTimeMillis();
        byte[] decompressedFile = this.algorithmInterface.decode(file);
        long end = System.currentTimeMillis();
        int decompressedSize = decompressedFile.length;
        printDecodeStatistics(start, end, compressedSize, decompressedSize);
        return decompressedFile;
    }
}

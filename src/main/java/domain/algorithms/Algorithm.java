package domain.algorithms;

import domain.algorithms.lossless.Lz78;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class Algorithm {
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public byte[] encodeFile(byte[] file) throws IOException {
        int uncompressedSize = file.length;
        long start = System.currentTimeMillis();
        byte[] compressedFile = this.algorithmInterface.encode(file);
        long end = System.currentTimeMillis();
        int compressedSize = compressedFile.length;
        printEncodeStatistics(start, end, uncompressedSize, compressedSize);
        return compressedFile;
    }

    public byte[] decodeFile(byte[] file) throws UnsupportedEncodingException {
        int compressedSize = file.length;
        long start = System.currentTimeMillis();
        byte[] decompressedFile = this.algorithmInterface.decode(file);
        long end = System.currentTimeMillis();
        int decompressedSize = decompressedFile.length;
        printDecodeStatistics(start, end, compressedSize, decompressedSize);
        return decompressedFile;
    }

    private static void printEncodeStatistics(long start, long end, float uncompressedSize, float compressedSize) {
        System.out.printf("start %d, end %d, unc %f, compress %f\n", start, end, uncompressedSize, compressedSize);
        System.out.printf("Elapsed time: %d ms\n", end-start);
        if ((end-start) == 0 && (uncompressedSize-compressedSize) == 0) {
            System.out.println("Compression speed: 0 bytes/ms");
        }
        else {
            System.out.printf("Compression speed: %.2f bytes/ms\n", (uncompressedSize-compressedSize)/(end-start));
        }
        if (uncompressedSize == 0 && compressedSize == 0) {
            System.out.println("Compression ratio: 0");
        }
        else {
            System.out.printf("Compress ratio: %.2f\n", uncompressedSize/compressedSize);
        }
        System.out.printf("Space savings: %.2f\n", 1.0f - compressedSize/uncompressedSize);
    }

    private static void printDecodeStatistics(long start, long end, float compressedSize, float decompressedSize) {
        System.out.printf("Elapsed time: %d ms\n", end-start);
        if ((end-start) == 0 && (decompressedSize-compressedSize) == 0) {
            System.out.println("Decompression speed: 0 bytes/ms");
        }
        else {
            System.out.printf("Decompression speed: %.2f bytes/ms\n", (decompressedSize-compressedSize)/(end-start));
        }
    }
}

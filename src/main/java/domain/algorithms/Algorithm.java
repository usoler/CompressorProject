package domain.algorithms;

import domain.DecompressedFile;
import domain.Folder;
import domain.IFile;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Objects;

public class Algorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Algorithm.class);
    private static final byte FOLDER_CODE = 0;
    private static final byte FILE_CODE = 1;
    private static final byte END_FOLDER_CODE = 2;
    private static final byte JPEG_CODE = 3;
    private static final byte LZ78_CODE = 4;
    private static final byte LZW_CODE = 5;
    private AlgorithmInterface algorithmInterface;
    private int headerIndex = 0;
    private int dataIndex = 0;

    public Algorithm() {
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    /**
     * Encodes the given data bytes
     *
     * @param data the data bytes to encode
     * @return the encoded data bytes
     * @throws CompressorException If any error occurs
     */
    public byte[] encodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        LOGGER.info("Starts to encode");
        byte[] compressedFile = this.algorithmInterface.encode(data);
        LOGGER.info("Encode finished");
        return compressedFile;
    }

    /**
     * Decodes the given data bytes
     *
     * @param data the data bytes to decode
     * @return the decoded data bytes
     * @throws CompressorException If any error occurs
     */
    public byte[] decodeFile(byte[] data) throws CompressorException {
        checkFile(data);
        LOGGER.info("Starts to decode");
        byte[] decompressedFile = this.algorithmInterface.decode(data);
        LOGGER.info("Decode finished");
        return decompressedFile;
    }

    /**
     * Encodes the given folder
     *
     * @param iFile         the folder to encode
     * @param textAlgorithm the text algorithm for to encode
     * @return the encoded data bytes
     * @throws CompressorException If any error occurs
     */
    public byte[] encodeFolder(Folder folder, AlgorithmInterface textAlgorithm) throws CompressorException {
        ArrayList<Byte> header = new ArrayList<>();
        ArrayList<Byte> data = new ArrayList<>();
        recursiveEncodeFolder(folder, header, data, textAlgorithm);
        addIntToByteArrayList(header.size(), 0, header);
        return mergeTwoBytesArrayList(header, data);
    }

    /**
     * Decodes the given folder data bytes
     *
     * @param fileData   the folder data bytes to decode
     * @param outputPath the output path to leave the decoded folder
     * @return the decoded folder
     * @throws CompressorException If any error occurs
     */
    public Folder decodeFolder(byte[] fileData, String outputPath) throws CompressorException {
        int headerLength = getNextIntInFile(fileData);
        headerIndex = 4;
        dataIndex = 4 + headerLength;
        return (Folder)recursiveDecodeFolder(fileData, outputPath);
    }

    private void checkFile(byte[] file) throws CompressorException {
        if (Objects.isNull(file)) {
            String message = "Param file cannot be null";
            LOGGER.error(message);
            throw new CompressorException(message, new IllegalArgumentException(), CompressorErrorCode.ILLEGAL_NULL_FILE_FAILURE);
        }
    }

    private void recursiveEncodeFolder(IFile iFile, ArrayList<Byte> header, ArrayList<Byte> data, AlgorithmInterface textAlgorithm) throws CompressorException {
        if (iFile instanceof Folder) {
            recursiveEncodeFolder((Folder) iFile, header, data, textAlgorithm);
        } else if (iFile instanceof domain.File) {
            recursiveEncodeFolder((domain.File) iFile, header, data, textAlgorithm);
        }
    }

    private void recursiveEncodeFolder(Folder folder, ArrayList<Byte> header, ArrayList<Byte> data, AlgorithmInterface textAlgorithm) throws CompressorException {
        header.add(FOLDER_CODE);
        addIntToByteArrayList(folder.getName().length(), header);
        addStringToByteArrayList(folder.getName(), header);
        for (IFile iFile : folder.getFiles()) {
            recursiveEncodeFolder(iFile, header, data, textAlgorithm);
        }
        header.add(END_FOLDER_CODE);
    }

    private void selectAlgorithmByExtension(String extension, AlgorithmInterface textAlgorithm) {
        if (extension.equals("txt")) {
            setAlgorithmInterface(textAlgorithm);
        } else if (extension.equals("ppm")) {
            setAlgorithmInterface(new Jpeg());
        }
    }

    private void addAlgorithmToHeader(ArrayList<Byte> header) {
        if (algorithmInterface instanceof Lzw) {
            header.add(LZW_CODE);
        } else if (algorithmInterface instanceof Lz78) {
            header.add(LZ78_CODE);
        } else if (algorithmInterface instanceof Jpeg) {
            header.add(JPEG_CODE);
        }
    }

    private void recursiveEncodeFolder(domain.File file, ArrayList<Byte> header, ArrayList<Byte> data, AlgorithmInterface textAlgorithm) throws CompressorException {
        header.add(FILE_CODE);
        addIntToByteArrayList(file.getName().length(), header);
        addStringToByteArrayList(file.getName(), header);
        selectAlgorithmByExtension(file.getFormat(), textAlgorithm);
        addAlgorithmToHeader(header);
        byte[] compressedFile = encodeFile(file.getData());
        for (byte b : compressedFile) {
            data.add(b);
        }
        addIntToByteArrayList(compressedFile.length, header);
    }

    private IFile recursiveDecodeFolder(byte[] fileData, String outputPath) throws CompressorException {
        byte code = fileData[headerIndex++];
        String name = getNameInFile(fileData);
        if (code == FOLDER_CODE) {
            String newOutputPath = outputPath + File.separator + name.toString();
            Folder folder = new Folder(name.toString(), "folder", newOutputPath);
            while (fileData[headerIndex] != END_FOLDER_CODE) {
                IFile iFile = recursiveDecodeFolder(fileData, newOutputPath);
                folder.addFile(iFile);
            }
            ++headerIndex;
            return folder;
        } else {
            byte algorithmCode = fileData[headerIndex++];
            selectAlgorithmByCode(algorithmCode);
            int compressedFileLength = getNextIntInFile(fileData);
            byte[] compressedFile = new byte[compressedFileLength];
            for (int i = 0; i < compressedFileLength; i++) {
                compressedFile[i] = fileData[dataIndex++];
            }
            byte[] decompressionResult = decodeFile(compressedFile);
            String[] splitName = name.toString().split("\\.");
            DecompressedFile file = new DecompressedFile(decompressionResult,
                    outputPath + File.separator + name.toString(),
                    name.toString(), decompressionResult.length, splitName[splitName.length - 1]);
            return file;
        }
    }

    private int getNextIntInFile(byte[] bytes) {
        return ByteBuffer.wrap(new byte[]{bytes[headerIndex++], bytes[headerIndex++], bytes[headerIndex++], bytes[headerIndex++]}).getInt();
    }

    private String getNameInFile(byte[] bytes) {
        int nameLength = getNextIntInFile(bytes);
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < nameLength; i++) {
            name.append((char) bytes[headerIndex++]);
        }
        return name.toString();
    }

    private void selectAlgorithmByCode(byte algorithmCode) {
        if (algorithmCode == LZ78_CODE) {
            setAlgorithmInterface(new Lz78());
        } else if (algorithmCode == LZW_CODE) {
            setAlgorithmInterface(new Lzw());
        } else if (algorithmCode == JPEG_CODE) {
            setAlgorithmInterface(new Jpeg());
        }
    }

    private static byte[] intToByteArray(int number) {
        return ByteBuffer.allocate(4).putInt(number).array();
    }

    public static void addIntToByteArrayList(int num, ArrayList<Byte> arrayList) {
//        byte[] number = intToByteArray(num, bytesNeeded - 1);
        byte[] number = intToByteArray(num);
        for (byte b : number) arrayList.add(b);
    }

    public static void addIntToByteArrayList(int num, int index, ArrayList<Byte> arrayList) {
        byte[] number = intToByteArray(num);
        for (byte b : number) arrayList.add(index++, b);
    }

    public static void addStringToByteArrayList(String string, ArrayList<Byte> arrayList) {
        for (int i = 0; i < string.length(); i++) {
            arrayList.add((byte) string.charAt(i));
        }
    }

    public static byte[] mergeTwoBytesArrayList(ArrayList<Byte> arrayList1, ArrayList<Byte> arrayList2) {
        byte[] byteArray = new byte[arrayList1.size() + arrayList2.size()];
        int i = 0;
        for (Byte value : arrayList1) {
            byteArray[i++] = value;
        }
        for (Byte value : arrayList2) {
            byteArray[i++] = value;
        }
        return byteArray;
    }


}

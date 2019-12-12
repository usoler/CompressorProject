package domain.algorithms;

import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.dataObjects.Pair;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

public class Algorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(Algorithm.class);
    private AlgorithmInterface algorithmInterface;
    private static byte FOLDER_CODE = 0;
    private static byte FILE_CODE = 1;
    private static byte END_FOLDER_CODE = 2;

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



    public byte[] encodeFolder(File folder) throws IOException, CompressorException {
        ArrayList<Byte> header = new ArrayList<>();
        ArrayList<Byte> data = new ArrayList<>();
        recursiveEncodeFolder(folder, header, data);
        addIntToByteArrayList(header.size(), 4, 0, header);
        byte[] encodedFolder = new byte[header.size() + data.size()];
        int i = 0;
        for (Byte aByte : header) {
            encodedFolder[i++] = aByte;
        }
        for (Byte datum : data) {
            encodedFolder[i++] = datum;
        }
        return encodedFolder;
    }

    private void recursiveEncodeFolder(File file, ArrayList<Byte> header, ArrayList<Byte> data) throws IOException, CompressorException {
        if (file.isDirectory()) {
            header.add(FOLDER_CODE);
            addIntToByteArrayList(file.getName().length(), 4, header);
            addStringToByteArrayList(file.getName(), header);
            for (File f: file.listFiles()) {
                recursiveEncodeFolder(f, header, data);
            }
            header.add(END_FOLDER_CODE);
        }
        else {
            header.add(FILE_CODE);
            addIntToByteArrayList(file.getName().length(), 4, header);
            addStringToByteArrayList(file.getName(), header);

            if (getExtension(file.getName()).equals("txt")) {
                setAlgorithmInterface(new Lzw());
            } else if (getExtension(file.getName()).equals("ppm")) {
                setAlgorithmInterface(new Jpeg());
            }
            byte[] encodedFile = encodeFile(Files.readAllBytes(file.toPath()));
            for (byte b:encodedFile) {
                data.add(b);
            }
            LOGGER.debug(String.format("------------- filename = %s; originalLength = %d; compressedLength = %d",
                    file.getName(), Files.readAllBytes(file.toPath()).length, encodedFile.length));
            //int n = file.length();

            addIntToByteArrayList(encodedFile.length, 4, header);
        }
    }

    public void decodeFolder(byte[] file, String path) throws CompressorException, IOException {
        int headerLength = getNextInt(file, 0).getValue();
        //int headerLength = ByteBuffer.wrap(new byte[]{file[0], file[1], file[2], file[3]}).getInt();
        LOGGER.debug("header length: " + headerLength);
        int headerIndex = 4;
        int dataIndex = 4 + headerLength;
        recursiveDecodeFolder(file, headerIndex, dataIndex, path);
    }

    private Pair<Integer, Integer> recursiveDecodeFolder(byte[] file, int headerIndex, int dataIndex, String path) throws CompressorException, IOException {
        byte code = file[headerIndex++];
        StringBuilder name = new StringBuilder();
        headerIndex = getName(file, headerIndex, name);

        if (code == FOLDER_CODE) {
            String newPath = path + File.separator + name;
            File folder = new File(newPath);
            folder.mkdir();
            while (file[headerIndex] != END_FOLDER_CODE) {
                //headerIndex = recursiveDecodeFolder(file, headerIndex, dataIndex, newPath);
                Pair<Integer, Integer> pair = recursiveDecodeFolder(file, headerIndex, dataIndex, newPath);
                headerIndex = pair.getKey();
                dataIndex = pair.getValue();
            }
            ++headerIndex;
        } else {
            File newFile = new File(path + File.separator + name);
            Pair<Integer, Integer> pair = getNextInt(file, headerIndex);
            headerIndex = pair.getKey();
            int fileLength = pair.getValue();

            byte[] encodedFile = new byte[fileLength];
            for (int i = 0; i < fileLength; i++) {
                encodedFile[i] = file[dataIndex++];
            }

            if (getExtension(name.toString()).equals("txt")) {
                setAlgorithmInterface(new Lzw());
            } else if (getExtension(name.toString()).equals("ppm")) {
                setAlgorithmInterface(new Jpeg());
            }

            byte[] decodedFile = decodeFile(encodedFile);
            LOGGER.debug(String.format("---------------- name = %s; compressedLength = %d, decompressedLength = %d",
                    newFile.getName(), fileLength, decodedFile.length));

            FileOutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(decodedFile);
            outputStream.close();
        }
        return new Pair<>(headerIndex, dataIndex);
    }


    private static Pair<Integer, Integer> getNextInt(byte[] bytes, int index) {
        int nextInt = ByteBuffer.wrap(new byte[]{bytes[index++], bytes[index++], bytes[index++], bytes[index++]}).getInt();
        return new Pair<>(index, nextInt);
    }

    private static int getName(byte[] file, int headerIndex, StringBuilder name) {
        Pair<Integer, Integer> pair = getNextInt(file, headerIndex);
        headerIndex = pair.getKey();
        int nameLength = pair.getValue();
        //int nameLength = ByteBuffer.wrap(new byte[]{file[headerIndex++], file[headerIndex++], file[headerIndex++], file[headerIndex++]}).getInt();
        for (int i = 0; i < nameLength; i++) {
            name.append((char)file[headerIndex++]);
        }
        return headerIndex;
    }


    private static String getExtension(String name) {
        if (name.length() < 3) {
            return "";
        }
        return name.substring(name.length()-3);
    }

    private static void addStringToByteArrayList(String s, ArrayList<Byte> arrayList) {
        for (int i = 0; i < s.length(); i++) {
            arrayList.add((byte)s.charAt(i));
        }
    }

    private static void addIntToByteArrayList(int num, int bytesNeeded, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(b);
    }

    private static void addIntToByteArrayList(int num, int bytesNeeded, int index, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(index++, b);
    }

    private static byte[] transformIntToByteArray(int index, int extraBytesNeeded) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(index).array();
        switch (extraBytesNeeded) {
            case 0:
                byte b1[] = {bytes[3]};
                return b1;
            case 1:
                byte b2[] = {bytes[2], bytes[3]};
                return b2;
            case 2:
                byte b3[] = {bytes[1], bytes[2], bytes[3]};
                return b3;
            default:
                return bytes;
        }
    }
}

package domain.algorithms;

import domain.Folder;
import domain.IFile;
import domain.algorithms.lossless.Lz;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.dataObjects.ByteArrayListUtils;
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
    private static final byte FOLDER_CODE = 0;
    private static final byte FILE_CODE = 1;
    private static final byte END_FOLDER_CODE = 2;
    private static final byte JPEG_CODE = 3;
    private static final byte LZ78_CODE = 4;
    private static final byte LZW_CODE = 5;

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



    public byte[] encodeFolder(File folder, String typeOfTextAlgorithm) throws CompressorException {
        ArrayList<Byte> header = new ArrayList<>();
        ArrayList<Byte> data = new ArrayList<>();
        recursiveEncodeFolder(folder, header, data, typeOfTextAlgorithm);
        ByteArrayListUtils.addIntToByteArrayList(header.size(), 4, 0, header);
        return ByteArrayListUtils.mergeTwoBytesArrayList(header, data);
    }

    private void recursiveEncodeFolder(File file, ArrayList<Byte> header, ArrayList<Byte> data, String typeOfTextAlgorithm) throws CompressorException {
        if (file.isDirectory()) {
            header.add(FOLDER_CODE);
            ByteArrayListUtils.addIntToByteArrayList(file.getName().length(), 4, header);
            ByteArrayListUtils.addStringToByteArrayList(file.getName(), header);
            for (File f: file.listFiles()) {
                recursiveEncodeFolder(f, header, data, typeOfTextAlgorithm);
            }
            header.add(END_FOLDER_CODE);
        }
        else {
            header.add(FILE_CODE);
            ByteArrayListUtils.addIntToByteArrayList(file.getName().length(), 4, header);
            ByteArrayListUtils.addStringToByteArrayList(file.getName(), header);
            selectAlgorithmByName(file.getName(), typeOfTextAlgorithm, header);

            byte[] encodedFile;
            try {
                encodedFile = encodeFile(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                String message = String.format("Failure to read all bytes in file from path '%s'", file.toPath());
                LOGGER.error(message, e);
                throw new CompressorException(message, e, CompressorErrorCode.READ_FILE_BYTES_FAILURE);
            }

            for (byte b:encodedFile) {
                data.add(b);
            }
            ByteArrayListUtils.addIntToByteArrayList(encodedFile.length, 4, header);
        }
    }

    public byte[] encodeFolderV2(domain.File folder) throws CompressorException {
        ArrayList<Byte> header = new ArrayList<>();
        ArrayList<Byte> data = new ArrayList<>();
        recursiveEncodeFolderV2(folder, header, data);
        ByteArrayListUtils.addIntToByteArrayList(header.size(), 4, 0, header);
        return  ByteArrayListUtils.mergeTwoBytesArrayList(header, data);
    }

    public void decodeFolder(byte[] file, String path) throws CompressorException, IOException {
        int headerLength = getNextInt(file, 0).getValue();
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
                Pair<Integer, Integer> pair = recursiveDecodeFolder(file, headerIndex, dataIndex, newPath);
                headerIndex = pair.getKey();
                dataIndex = pair.getValue();
            }
            ++headerIndex;
        } else {
            byte algorithmCode = file[headerIndex++];
            File newFile = new File(path + File.separator + name);
            Pair<Integer, Integer> pair = getNextInt(file, headerIndex);
            headerIndex = pair.getKey();
            int fileLength = pair.getValue();

            byte[] encodedFile = new byte[fileLength];
            for (int i = 0; i < fileLength; i++) {
                encodedFile[i] = file[dataIndex++];
            }
            selectAlgorithmByCode(algorithmCode);

            byte[] decodedFile = decodeFile(encodedFile);
            FileOutputStream outputStream = new FileOutputStream(newFile);
            outputStream.write(decodedFile);
            outputStream.close();
        }
        return new Pair<>(headerIndex, dataIndex);
    }


    private void recursiveEncodeFolderV2(IFile iFile, ArrayList<Byte> header, ArrayList<Byte> data) throws CompressorException {
        if (iFile instanceof Folder) recursiveEncodeFolderV2((Folder) iFile, header, data);
        else if (iFile instanceof domain.File) recursiveEncodeFolderV2((domain.File) iFile, header, data);
    }

    private void recursiveEncodeFolderV2(Folder folder, ArrayList<Byte> header, ArrayList<Byte> data) throws CompressorException {
        header.add(FOLDER_CODE);
        ByteArrayListUtils.addIntToByteArrayList(folder.getName().length(), 4, header);
        ByteArrayListUtils.addStringToByteArrayList(folder.getName(), header);
        for (IFile f : folder.getFiles()) {
            recursiveEncodeFolderV2(f, header, data);
        }
        header.add(END_FOLDER_CODE);
    }

    private void recursiveEncodeFolderV2(domain.File file, ArrayList<Byte> header, ArrayList<Byte> data) throws CompressorException {
        header.add(FILE_CODE);
        ByteArrayListUtils.addIntToByteArrayList(file.getName().length(), 4, header);
        ByteArrayListUtils.addStringToByteArrayList(file.getName(), header);
        if (file.getFormat().equals("txt")) {
            setAlgorithmInterface(new Lzw());
        } else if (file.getFormat().equals("ppm")) {
            setAlgorithmInterface(new Jpeg());
        }
        byte[] encodedFile = encodeFile(file.getData());
        for (byte b : encodedFile) {
            data.add(b);
        }
        LOGGER.debug(String.format("------------- filename = %s; originalLength = %d; compressedLength = %d",
                file.getName(), file.getData().length, encodedFile.length));
        ByteArrayListUtils.addIntToByteArrayList(encodedFile.length, 4, header);
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

    private void selectAlgorithmByName(String name, String typeOfTextAlgorithm, ArrayList<Byte> header) {
        if (getExtension(name).equals("txt")) {
            if (typeOfTextAlgorithm.equals("LZ78")) {
                setAlgorithmInterface(new Lz78());
                header.add(LZ78_CODE);
            } else if (typeOfTextAlgorithm.equals("LZW")) {
                setAlgorithmInterface(new Lzw());
                header.add(LZW_CODE);
            }
        } else if (getExtension(name).equals("ppm")) {
            setAlgorithmInterface(new Jpeg());
            header.add(JPEG_CODE);
        }
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

    /*private static void addStringToByteArrayList(String s, ArrayList<Byte> arrayList) {
        for (int i = 0; i < s.length(); i++) {
            arrayList.add((byte)s.charAt(i));
        }
    }*/

    /*private static void addIntToByteArrayList(int num, int bytesNeeded, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(b);
    }*/

    /*private static void addIntToByteArrayList(int num, int bytesNeeded, int index, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b : number) arrayList.add(index++, b);
    }*/

    /*private static byte[] transformIntToByteArray(int index, int extraBytesNeeded) {
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
    }*/
}

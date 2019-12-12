package domain.algorithms.lossless;

import domain.dataStructure.Trie;
import domain.dataStructure.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Lz78 extends Lz {
    private static final Logger LOGGER = LoggerFactory.getLogger(Lz78.class);

    protected static byte[] ArrayListByteToByteArray(ArrayList<Byte> listOfBytes) {
        byte[] byteArray = new byte[listOfBytes.size()];
        for (int j = 0; j < listOfBytes.size(); j++) {
            byteArray[j] = listOfBytes.get(j).byteValue();
        }
        return byteArray;
    }

    protected static byte[] transformIntToByteArray(int index, int extraBytesNeeded) {
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

    private static String bytesToBinary(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int k = 0; k < 8; k++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    public static final long unsignedIntToLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        l <<= 8;
        l |= b[3] & 0xFF;
        return l;
    }

    public static final short unsignedByteToShort(byte[] b) {
        short l = 0;
        l |= b[0] & 0xFF;
        return l;
    }

    public static final int unsignedShortToInt(byte[] b) {
        int l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        return l;
    }

    public static final long unsigned3bytesTo6bytesLong(byte[] b) {
        long l = 0;
        l |= b[0] & 0xFF;
        l <<= 8;
        l |= b[1] & 0xFF;
        l <<= 8;
        l |= b[2] & 0xFF;
        return l;
    }

    @Override
    public byte[] encode(byte[] data) {
        LOGGER.debug("Encoding file data with LZ78 algorithm");
        encodingDictionary = new Trie();
        String file = new String(data, StandardCharsets.UTF_8);
        int iterator = 0;
        int lengthOfFile = file.length();
        int extraBytesNeeded = 0;
        int mapIndex = 0;
        int firstExtraBytePosition = 0;
        int secondExtraBytePosition = 0;
        int thirdExtraBytePosition = 0;
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        byte[] byteArray = {};

        while (iterator < lengthOfFile) {
            int index = 0;
            int position = 0;
            char characterOfIterator = file.charAt(iterator);
            String word = Character.toString(characterOfIterator);
            TrieNode nodeContainingWord = encodingDictionary.contains(word);
            while (nodeContainingWord != null && iterator < lengthOfFile) {

                index = nodeContainingWord.getIndex();
                if (iterator + 1 < lengthOfFile) {
                    ++iterator;
                    characterOfIterator = file.charAt(iterator);
                    word += characterOfIterator;
                } else {
                    ++iterator;
                }
                ++position;
                if (iterator < lengthOfFile ) nodeContainingWord = encodingDictionary.contains(word, nodeContainingWord, position);
            }
            if (nodeContainingWord == null) {
                ++mapIndex;
                encodingDictionary.insert(word);
                ++iterator;
                switch (extraBytesNeeded) {
                    case 0:
                        if (index > 255) //16^2-1
                        {
                            extraBytesNeeded = 1;
                            firstExtraBytePosition = mapIndex;
                            break;
                        }
                    case 1:
                        if (index > 65535) //16^4-1
                        {
                            extraBytesNeeded = 2;
                            secondExtraBytePosition = mapIndex;
                            break;
                        }
                    case 2:
                        if (index > 16777215) //16^6-1
                        {
                            extraBytesNeeded = 3;
                            thirdExtraBytePosition = mapIndex;
                            break;
                        }
                    default:
                        break;
                }
            }

            byteArray = transformIntToByteArray(index, extraBytesNeeded);
            for (byte byteOfArray : byteArray) bytes.add(byteOfArray);

            if (nodeContainingWord == null) {
                byte character = (byte) characterOfIterator;
                bytes.add(character);
            } else {
                byte character = 0;
                bytes.add(character);
            }
        }
        encodingDictionary = null;

        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(thirdExtraBytePosition).array();
        for (byte b : byteArray) bytes.add(0, b);
        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(secondExtraBytePosition).array();
        for (byte b : byteArray) bytes.add(0, b);
        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(firstExtraBytePosition).array();
        for (byte b : byteArray) bytes.add(0, b);

        byteArray = ArrayListByteToByteArray(bytes);
        return byteArray;
    }

    @Override
    public byte[] decode(byte[] file) {
        LOGGER.debug("Decoding file data with LZ78");
        decodingDictionary = new HashMap<>();
        byte[] bytes = file;
        byte[] firstInt = {bytes[0], bytes[1], bytes[2], bytes[3]};
        byte[] secondInt = {bytes[4], bytes[5], bytes[6], bytes[7]};
        byte[] thirdInt = {bytes[8], bytes[9], bytes[10], bytes[11]};

        int firstChange = ByteBuffer.wrap(firstInt).getInt();
        int secondChange = ByteBuffer.wrap(secondInt).getInt();
        int thirdChange = ByteBuffer.wrap(thirdInt).getInt();

        int extraBytes = 0;

        int iterator = 12;
        int lengthOfEncoding = bytes.length;
        int mapIndex = 1;
        byte[] indexByte = {};
        byte byteCharacterAtIterator = 0;
        char charCharacterAtIterator = 0;
        long longIndex = 0;
        int intIndex = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while (iterator < lengthOfEncoding) {
            if (mapIndex == firstChange) {
                extraBytes = 1;
            } else if (mapIndex == secondChange) {
                extraBytes = 2;
            } else if (mapIndex == thirdChange) {
                extraBytes = 3;
            }
            switch (extraBytes) {
                case 0:
                    indexByte = new byte[]{bytes[iterator]};
                    ++iterator;
                    longIndex = unsignedByteToShort(indexByte);
                    break;
                case 1:
                    indexByte = new byte[]{bytes[iterator], bytes[iterator + 1]};
                    iterator += 2;
                    longIndex = unsignedShortToInt(indexByte);
                    break;
                case 2:
                    indexByte = new byte[]{bytes[iterator], bytes[iterator + 1], bytes[iterator + 2]};
                    iterator += 3;
                    longIndex = unsigned3bytesTo6bytesLong(indexByte);
                    break;
                case 3:
                    indexByte = new byte[]{bytes[iterator], bytes[iterator + 1], bytes[iterator + 2], bytes[iterator + 3]};
                    iterator += 4;
                    longIndex = unsignedIntToLong(indexByte);
                    break;
                default:
                    indexByte = new byte[]{bytes[iterator], bytes[iterator + 1], bytes[iterator + 2], bytes[iterator + 3]};
                    iterator += 4;
                    break;
            }

            intIndex = (int) longIndex;
            if (iterator < lengthOfEncoding) {
                byteCharacterAtIterator = bytes[iterator];
                charCharacterAtIterator = (char) (0xff & byteCharacterAtIterator);
                String word;
                if (longIndex != 0) {
                    word = decodingDictionary.get(intIndex);
                    if ( !((iterator + 1 == lengthOfEncoding) && (byteCharacterAtIterator == 0))) {
                        word += charCharacterAtIterator;
                    }
                } else {
                    word = Character.toString(charCharacterAtIterator);
                }
                stringBuffer.append(word);
                decodingDictionary.put(mapIndex, word);
                ++mapIndex;
                ++iterator;
                longIndex = -1;
            }
        }
        decodingDictionary = null;

        return stringBuffer.toString().getBytes();
    }
}

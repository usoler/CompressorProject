package domain.algorithms.lossless;



import java.io.*;
import java.math.BigInteger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


import domain.dataStructure.Trie;
import domain.dataStructure.TrieNode;


public class Lz78 extends Lz {

    @Override
    public byte[] encode(byte[] data) {
        // ENCODING WITH LZ78
        System.out.println("ENCODING FILE WITH LZ78");
        encodingDictionary = new Trie();
        String file = new String(data, StandardCharsets.UTF_8);
        int i = 0;
        int length = file.length();
        int extraBytesNeeded = 0;
        int mapIndex = 0;
        int firstExtraBytePosition = 0;
        int secondExtraBytePosition = 0;
        int thirdExtraBytePosition = 0;
        boolean first = true;
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        byte[] byteArray = {};

        while (i < length)
        {
            int index = 0;
            int position = 0;
            char c = file.charAt(i);
            String word = Character.toString(c);
            TrieNode aux = encodingDictionary.contains(word);
            while (aux != null && i < length) {

                index = aux.getIndex();
                if (i + 1 < length) {
                    ++i;
                    c = file.charAt(i);
                    word += c;
                } else {
                    ++i;
                }
                ++position;
                aux = encodingDictionary.contains(word, aux, position);
            }
            if (aux == null) {
                ++mapIndex;
                encodingDictionary.insert(word);
                ++i;
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




            byteArray = transformIntToByteArray(index,extraBytesNeeded);
            for (byte b: byteArray) bytes.add(b);

            if (aux==null)
            {
                byte b = (byte)c;
                bytes.add(b);
            }
            else
            {
                byte b = 0;
                bytes.add(b);
            }

        }
        encodingDictionary = null;

        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(thirdExtraBytePosition).array();
        for (byte b: byteArray) bytes.add(0,b);
        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(secondExtraBytePosition).array();
        for (byte b: byteArray) bytes.add(0,b);
        byteArray = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(firstExtraBytePosition).array();
        for (byte b: byteArray) bytes.add(0,b);


        byteArray = ArrayListBytetoByteArray(bytes);
        return byteArray;
    }

    static protected byte[] ArrayListBytetoByteArray(ArrayList<Byte> bytes)
    {
        byte[] result = new byte[bytes.size()];
        for(int j = 0; j < bytes.size(); j++) {
            result[j] = bytes.get(j).byteValue();
        }
        return result;
    }

    static protected byte[] transformIntToByteArray(int index, int extraBytesNeeded)
    {
        byte[] bytes = ByteBuffer.allocate(4).putInt(index).array();
        switch (extraBytesNeeded)
        {
            case 0:
                byte b1[] = {bytes[3]};
                return b1;

            case 1:
                byte b2[] = {bytes[2],bytes[3]};
                return b2;


            case 2:
                byte b3[] = {bytes[1],bytes[2],bytes[3]};
                return b3;

            case 3: return bytes;

            default : return bytes;
        }
    }


    @Override
    public byte[] decode(byte[] file) {
        // DECODING WITH LZ78
        System.out.println("DECODING FILE WITH LZ78");
        decodingDictionary = new HashMap<>();
        byte[] bytes = file;
        byte[] firstInt = {bytes[0],bytes[1],bytes[2],bytes[3]};
        byte[] secondInt = {bytes[4],bytes[5],bytes[6],bytes[7]};
        byte[] thirdInt = {bytes[8],bytes[9],bytes[10],bytes[11]};

        int firstChange = ByteBuffer.wrap(firstInt).getInt();;
        int secondChange = ByteBuffer.wrap(secondInt).getInt();
        int thirdChange = ByteBuffer.wrap(thirdInt).getInt();

        int extraBytes = 0;

        int i = 12;
        int length = bytes.length;
        int mapIndex = 1;
        byte[] indexByte = {};
        byte character = 0;
        char c = 0;
        long index = 0;
        int index2 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while (i < length)
        {

            if (mapIndex==firstChange)
            {
                extraBytes = 1;
            }
            else if (mapIndex == secondChange)
            {
                extraBytes = 2;
            }
            else if (mapIndex == thirdChange)
            {
                extraBytes = 3;
            }
            switch(extraBytes) {
                case 0:
                    indexByte = new byte[]{bytes[i]};
                    ++i;
                    index = unsignedByteToShort(indexByte);
                    break;
                case 1:
                    indexByte = new byte[]{bytes[i], bytes[i + 1]};
                    i += 2;
                    index = unsignedShortToInt(indexByte);
                    break;
                case 2:
                    indexByte = new byte[]{bytes[i], bytes[i + 1], bytes[i + 2]};
                    i += 3;
                    index = unsigned3bytesTo6bytesLong(indexByte);
                    break;
                case 3:
                    indexByte = new byte[]{bytes[i], bytes[i + 1], bytes[i + 2], bytes[i + 3]};
                    i += 4;
                    index = unsignedIntToLong(indexByte);
                    break;
                default:
                    indexByte = new byte[]{bytes[i], bytes[i + 1], bytes[i + 2], bytes[i + 3]};
                    i += 4;
                    break;
            }

            index2 = (int)index;
            if (i < length)
            {
                character = bytes[i];
                c = (char)(0xff & character);
                String word;
                if (index != 0)
                {
                    word = decodingDictionary.get(index2);
                    word+=c;
                }
                else
                {
                    word= Character.toString(c);
                }
                stringBuffer.append(word);
                decodingDictionary.put(mapIndex, word);
                ++mapIndex;
                ++i;
                index = -1;
            }

        }
        decodingDictionary = null;

        return stringBuffer.toString().getBytes();
    }

    static private String bytesToBinary(byte[] bytes)
    {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int k = 0; k < 8; k++)
            {
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
}

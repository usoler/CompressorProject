package domain.algorithms.lossless;



import java.io.*;
import java.math.BigInteger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


import domain.dataStructure.Trie;
import domain.dataStructure.TrieNode;


public class Lz78 extends Lz {

    private long concatenateTime = 0;


    @Override
    public byte[] encode(byte[] data) throws IOException {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
        encodingDictionary = new Trie();
        String file = new String(data,"UTF-8");
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
                            System.out.println(c);
                            break;
                        }
                    case 1:
                        if (index > 65535) //16^4-1
                        {
                            extraBytesNeeded = 2;
                            secondExtraBytePosition = mapIndex;
                            System.out.println(c);
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
                //byteArray = ByteBuffer.allocate(2).putChar(c).array();
                //for (byte b: byteArray) bytes.add(b);
                byte b = (byte)c;
                bytes.add(b);
            }
            else
            {
                //char zero = 0;
                //byteArray = ByteBuffer.allocate(2).putChar(zero).array();

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
    public byte[] decode(byte[] file) throws UnsupportedEncodingException {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
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


















































    /*
    @Override
    public String encode(String file) throws UnsupportedEncodingException {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
        encodingDictionary = new Trie();
        int length = file.length();
        int i = 0;
        int mapIndex = 0;
        int extraBytesNeeded = 0;
        boolean first = true;
        byte[] byteArray = {};
        int firstExtraBytePosition = 0;
        int secondExtraBytePosition = 0;
        int thirdExtraBytePosition = 0;
        StringBuffer result = new StringBuffer("");
        long time = 0;
        long startTime = System.currentTimeMillis();


        while (i < length)
        {
            int index = 0;
            int position = 0;
            char c = file.charAt(i);
            String word = Character.toString(c);
            TrieNode aux = encodingDictionary.contains(word);
            while (aux != null && i < length)
            {

                index = aux.getIndex();
                if (i+1 < length)
                {
                    ++i;
                    c = file.charAt(i);
                    word+=c;
                }
                else
                {
                    ++i;
                }
                ++position;
                aux = encodingDictionary.contains(word,aux,position);
            }

            if (aux == null) {
                ++mapIndex;
                encodingDictionary.insert(word);
                ++i;
                switch (extraBytesNeeded)
                {
                    case 0: if (index > 255) //16^2-1
                    {
                        extraBytesNeeded = 1;
                        firstExtraBytePosition = mapIndex;
                        break;
                    }
                    case 1: if (index > 65535) //16^4-1
                    {
                        extraBytesNeeded = 2;
                        secondExtraBytePosition = mapIndex;
                        break;
                    }
                    case 2: if (index > 16777215) //16^6-1
                    {
                        extraBytesNeeded = 3;
                        thirdExtraBytePosition = mapIndex;
                        break;
                    }
                    default: break;
                }
            }









            if (first)
            {
                first = false;
                //byteArray = concatenateByteArray(byteArray,intTo4Bytes(firstExtraBytePosition));
                //byteArray = concatenateByteArray(byteArray,intTo4Bytes(secondExtraBytePosition));
                //byteArray = concatenateByteArray(byteArray,intTo4Bytes(thirdExtraBytePosition));


                result = new StringBuffer(new String (intTo4Bytes(firstExtraBytePosition),"UTF-8"));
                result.append(new String (intTo4Bytes(secondExtraBytePosition),"UTF-8"));
                result.append(new String (intTo4Bytes(thirdExtraBytePosition),"UTF-8"));

            }
            result.append(new String(transformIntToByteArray(index, extraBytesNeeded),"UTF-8"));
            //byteArray = concatenateByteArray(byteArray,transformIntToByteArray(index, extraBytesNeeded));

            if (aux==null)
            {
                result.append(c) ;
                //byteArray = concatenateByteArray(byteArray,Character.toString(c).getBytes());
            }
            else
            {
                result.append(Character.toString(0));
                //byteArray = concatenateByteArray(byteArray,Character.toString(0).getBytes());
            }


        }


        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

        byteArray = result.toString().getBytes("UTF-8");
        return new String(byteArray,"UTF-32");
        //return result.toString();
        //return new String (byteArray,"UTF-32");
    }

    /*
    @Override
    public String decode(String file) {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
        decodingDictionary = new HashMap<>();
        int length = file.length();
        int i = 0;
        int index = -1;
        int mapIndex = 1;
        while (i < length)
        {
            char c = file.charAt(i);
            String integer = Character.toString(c);
            ++i;
            while (file.charAt(i) != ';')
            {
                c = file.charAt(i);
                integer +=c;
                ++i;
            }
            index = Integer.parseInt(integer);
            ++i;
            if (i < length) {
                c = file.charAt(i);
                String word;
                if (index != 0) {
                    word = decodingDictionary.get(index);
                    word += c;
                } else {
                    word = Character.toString(c);
                }
                decodingDictionary.put(mapIndex, word);
                ++mapIndex;
                ++i;
                index = -1;
            }
        }
        String newText = decodingDictionary.get(1);
        for (int j = 2; j < mapIndex; ++j)
        {
            newText += decodingDictionary.get(j);
        }
        if (index != -1)
        {
            newText += decodingDictionary.get(index);
        }
        return newText;
    }


    @Override
    public String decode(String file) throws UnsupportedEncodingException {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
        decodingDictionary = new HashMap<>();
        byte[] bytes = file.getBytes("UTF-32");
        byte[] firstInt = {bytes[0],bytes[1],bytes[2],bytes[3]};
        byte[] secondInt = {bytes[4],bytes[5],bytes[6],bytes[7]};
        byte[] thirdInt = {bytes[8],bytes[9],bytes[10],bytes[11]};

        int firstChange = ByteBuffer.wrap(firstInt).getInt();
        System.out.println(firstChange);
        int secondChange = ByteBuffer.wrap(secondInt).getInt();
        System.out.println(secondChange);
        int thirdChange = ByteBuffer.wrap(thirdInt).getInt();
        System.out.println(thirdChange);

        int mapIndex = 0;
        int i = 12;
        int length = bytes.length;
        int index = 0;
        while (i < length)
        {
            if (mapIndex < firstChange)
            {
                index = bytes[i];
                ++i;
                //char c = bytes[i];

            }
        }
        return "";
    }
private ArrayList<Byte> transformIntToByteArray(int index, int extraBytesNeeded)
    {
        byte[] bytes =  BigInteger.valueOf(index).toByteArray();
        byte zero = 0x00;
        //ArrayList<Byte> bytes = new ArrayList<Byte>();
        switch (extraBytesNeeded)
        {
            case 0: return bytes;

            case 1: if (index < 256)
            {
                byte[] zeros = {zero};
                return concatenateByteArray(zeros,bytes);
            }
            else
            {
                return bytes;
            }


            case 2: if (index < 256)
            {
                byte[] zeros = {zero,zero};
                return concatenateByteArray(zeros,bytes);
            }
            else if (index < 65536)
            {
                byte[] zeros = {zero};
                return concatenateByteArray(zeros,bytes);
            }
            else
            {
                return bytes;
            }



            case 3: if (index < 256)
            {
                byte[] zeros = {zero,zero,zero};
                return concatenateByteArray(zeros,bytes);
            }
            else if (index < 65536)
            {
                byte[] zeros = {zero,zero};
                return concatenateByteArray(zeros,bytes);
            }
            else if (index < 16777216)
            {
                byte[] zeros = {zero};
                return concatenateByteArray(zeros,bytes);
            }
            else
            {
                return bytes;
            }

            default : return bytes;
        }
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

    private byte[] concatenateByteArray(byte[] a, byte[] b)
    {
        long concatenateStartTime = System.currentTimeMillis();
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        long concatenateEndTime = System.currentTimeMillis();
        concatenateTime+=(concatenateEndTime-concatenateStartTime);
        return c;
    }



    public static final byte[] intTo4Bytes(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
    */
}

package domain.algorithms.lossless;



import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import java.util.HashMap;


import domain.dataStructure.Trie;


public class Lz78 extends Lz {

    @Override
    public byte[] encode(String file) throws UnsupportedEncodingException {
//        // ENCODING WITH LZ78
//        System.out.println("Encoding file with LZ78");
//        encodingDictionary = new Trie();
//        int length = file.length();
//        int i = 0;
//        int mapIndex = 0;
//        int extraBytesNeeded = 0;
//        byte[] byteArray = new byte[0];
//        boolean first = true;
//        int firstExtraBytePosition = 0;
//        int secondExtraBytePosition = 0;
//        int thirdExtraBytePosition = 0;
//        while (i < length)
//        {
//            int index = 0;
//            char c = file.charAt(i);
//            String word = Character.toString(c);
//            while (encodingDictionary.contains(word) && i < length)
//            {
//
//                index = encodingDictionary.getIndexOf(word);
//                if (i+1 < length)
//                {
//                    ++i;
//                    c = file.charAt(i);
//                    word+=c;
//                }
//                else
//                {
//                    ++i;
//                }
//            }
//
//            boolean contains = encodingDictionary.contains(word);
//            if (!contains) {
//                ++mapIndex;
//                encodingDictionary.insert(word);
//                ++i;
//                switch (extraBytesNeeded)
//                {
//                    case 0: if (index > 255) //16^2-1
//                    {
//                        extraBytesNeeded = 1;
//                        firstExtraBytePosition = mapIndex;
//                        break;
//                    }
//                    case 1: if (index > 65535) //16^4-1
//                    {
//                        extraBytesNeeded = 2;
//                        secondExtraBytePosition = mapIndex;
//                        break;
//                    }
//                    case 2: if (index > 16777215) //16^6-1
//                    {
//                        extraBytesNeeded = 3;
//                        thirdExtraBytePosition = mapIndex;
//                        break;
//                    }
//                    default: break;
//                }
//            }
//            if (first)
//            {
//                first = false;
//                byteArray = concatenateByteArray(byteArray,intTo4Bytes(firstExtraBytePosition));
//                byteArray = concatenateByteArray(byteArray,intTo4Bytes(firstExtraBytePosition));
//                byteArray = concatenateByteArray(byteArray,intTo4Bytes(firstExtraBytePosition));
//                //newText= Integer.toString(index);
//            }
//
//            byteArray = concatenateByteArray(byteArray, transformIntToByteArray(index, extraBytesNeeded));
//            if (!contains)
//            {
//                byteArray = concatenateByteArray(byteArray,Character.toString(c).getBytes());
//            }
//            else
//            {
//                byteArray = concatenateByteArray(byteArray,Character.toString(0).getBytes());
//            }
//
//
//        }
        return null;
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

     */
    @Override
    public String decode(String file) throws UnsupportedEncodingException {
        // DECODING WITH LZ78
//        System.out.println("Decoding file with LZ78");
////        decodingDictionary = new HashMap<>();
//        byte[] bytes = file.getBytes("UTF-8");
        return "";
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

    static private byte[] concatenateByteArray(byte[] a, byte[] b)
    {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    static private byte[] transformIntToByteArray(int index, int extraBytesNeeded)
    {
        byte[] bytes =  BigInteger.valueOf(index).toByteArray();
        byte zero = 0x00;
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

    public static final byte[] intTo4Bytes(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}

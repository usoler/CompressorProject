package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;
import domain.dataStructure.Trie;
import domain.dataStructure.TrieNode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Lzw extends Lz78 {

    private static final int ASCII_LENGTH = 256;

    private void initializeDecodingDictionary() {
        decodingDictionary = new HashMap<>();
        for (int i = 0; i < ASCII_LENGTH; i++) {
            decodingDictionary.put(i, Character.toString((char)i));
        }
    }

    private void initializeEncodingDictionary() {
        encodingDictionary = new Trie();
        for (int i = 0; i < ASCII_LENGTH; i++) {
            encodingDictionary.insert(Character.toString((char)i));
        }
    }

    @Override
    public byte[] encode(byte[] data) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
        initializeEncodingDictionary();
        String file = new String(data, StandardCharsets.UTF_8);
        ArrayList<Integer> codes = new ArrayList<>();
        int oneByteNumbers = 0;
        int twoBytesNumbers = 0;
        int threeBytesNumbers = 0;
        int maxCodeAdded = 0;
        int i = 0;
        while (i < file.length()) {
            StringBuilder word = new StringBuilder();
            TrieNode node = encodingDictionary.contains(word.append(file.charAt(i)).toString());
            int position = 1;
            while (i + 1 < file.length() &&
                    (node = encodingDictionary.contains(word.append(file.charAt(i+1)).toString(), node, position)) != null) {
                ++i;
                ++position;
            }
            if (node == null) {
                encodingDictionary.insert(word.toString());
                codes.add(encodingDictionary.getIndexOf(word.deleteCharAt(word.length()-1).toString())-1);
            }
            else {
                codes.add(encodingDictionary.getIndexOf(word.toString())-1);
            }

            maxCodeAdded = Math.max(maxCodeAdded, codes.get(codes.size()-1));
            if (maxCodeAdded < 256) ++oneByteNumbers;
            else if (maxCodeAdded < 65536) ++twoBytesNumbers;
            else if (maxCodeAdded < 16777216) ++threeBytesNumbers;

            ++i;
        }
        encodingDictionary = null;
        return codesToData(codes, oneByteNumbers, twoBytesNumbers, threeBytesNumbers);
    }

    @Override
    public byte[] decode(byte[] file) {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
        initializeDecodingDictionary();
        StringBuilder newText = new StringBuilder();
        ArrayList<Integer> codes = dataToCodesArrayList(file);

        if (codes.size() > 0) {
            newText.append(decodingDictionary.get(codes.get(0)));
        }
        for (int i = 1; i < codes.size(); i++) {
            StringBuilder word = new StringBuilder();
            if (decodingDictionary.containsKey(codes.get(i))) {
                word.append(decodingDictionary.get(codes.get(i)));
            }
            else {
                word.append(decodingDictionary.get(codes.get(i-1)));
                word.append(word.charAt(0));
            }
            newText.append(word);
            decodingDictionary.put(decodingDictionary.size(), decodingDictionary.get(codes.get(i-1)) + word.charAt(0));
        }
        decodingDictionary = null;
        return newText.toString().getBytes();
    }

    private static ArrayList<Integer> dataToCodesArrayList(byte[] bytes) {
        ArrayList<Integer> codes = new ArrayList<>();
        int firstExtraBytePosition = ByteBuffer.wrap(new byte[] {bytes[0],bytes[1],bytes[2],bytes[3]}).getInt();
        int secondExtraBytePosition = ByteBuffer.wrap(new byte[] {bytes[4],bytes[5],bytes[6],bytes[7]}).getInt();
        int thirdExtraBytePosition = ByteBuffer.wrap(new byte[] {bytes[8],bytes[9],bytes[10],bytes[11]}).getInt();
        int i = 12;
        while (i < bytes.length) {
            if (codes.size() < firstExtraBytePosition) {
                codes.add(ByteBuffer.wrap(new byte[] {0, 0, 0, bytes[i]}).getInt());
                ++i;
            }
            else if (codes.size() < secondExtraBytePosition) {
                codes.add(ByteBuffer.wrap(new byte[] {0, 0, bytes[i], bytes[i+1]}).getInt());
                i += 2;
            }
            else if (codes.size() < thirdExtraBytePosition) {
                codes.add(ByteBuffer.wrap(new byte[] {0, bytes[i], bytes[i+1], bytes[i+2]}).getInt());
                i += 3;
            }
            else {
                codes.add(ByteBuffer.wrap(new byte[] {bytes[i], bytes[i+1], bytes[i+2], bytes[i+3]}).getInt());
                i += 4;
            }
        }
        return codes;
    }

    private static byte[] codesToData(ArrayList<Integer> codes, int oneByteNums, int twoByteNums, int threeByteNums) {
        ArrayList<Byte> arrayList = new ArrayList<>();
        addIntToByteArrayList(oneByteNums, 4, arrayList);
        addIntToByteArrayList(oneByteNums + twoByteNums, 4, arrayList);
        addIntToByteArrayList(oneByteNums + twoByteNums + threeByteNums, 4, arrayList);
        int i = 0;
        for (; i < oneByteNums; ++i) {
            addIntToByteArrayList(codes.get(i), 1, arrayList);
        }
        for (; i < oneByteNums + twoByteNums; ++i) {
            addIntToByteArrayList(codes.get(i), 2, arrayList);
        }
        for (; i < oneByteNums + twoByteNums + threeByteNums; ++i) {
            addIntToByteArrayList(codes.get(i), 3, arrayList);
        }
        for (; i < codes.size(); ++i) {
            addIntToByteArrayList(codes.get(i), 1, arrayList);
        }
        return ArrayListBytetoByteArray(arrayList);
    }

    private static void addIntToByteArrayList(int num, int bytesNeeded, ArrayList<Byte> arrayList) {
        byte[] number = transformIntToByteArray(num, bytesNeeded - 1);
        for (byte b: number) arrayList.add(b);
    }
}

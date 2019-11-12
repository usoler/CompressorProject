package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;
import domain.dataStructure.Trie;
import domain.dataStructure.TrieNode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Lzw extends Lz78 {

    private static final int ASCII_LENGTH = 256;

    public Lzw() {
        super();
    }

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
    public byte[] encode(byte[] data) throws IOException {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
        initializeEncodingDictionary();
        String file = new String(data, StandardCharsets.UTF_8);
        ArrayList<Integer> codes = new ArrayList<>();

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
            ++i;
        }

        return intArrayToByteArray(codes);
    }

    @Override
    public byte[] decode(byte[] file) throws UnsupportedEncodingException {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
        initializeDecodingDictionary();
        StringBuilder newText = new StringBuilder();
        int[] codes = byteArrayToIntArray(file);

        if (codes.length > 0) {
            newText.append(decodingDictionary.get(codes[0]));
        }

        for (int i = 1; i < codes.length; i++) {
            StringBuilder word = new StringBuilder();
            if (decodingDictionary.containsKey(codes[i])) {
                word.append(decodingDictionary.get(codes[i]));
            }
            else {
                word.append(decodingDictionary.get(codes[i-1]));
                word.append(word.charAt(0));
            }
            newText.append(word);
            decodingDictionary.put(decodingDictionary.size(), decodingDictionary.get(codes[i-1]) + word.charAt(0));
        }

        return newText.toString().getBytes();
    }

    private static int[] byteArrayToIntArray(byte[] bytes) {
        int[] numbers = new int[bytes.length/4];
        for (int i = 0; i < bytes.length; i += 4) {
            ByteBuffer wrapped = ByteBuffer.wrap(new byte[] {bytes[i], bytes[i+1], bytes[i+2], bytes[i+3]});
            numbers[i/4] = wrapped.getInt();
        }
        return numbers;
    }

    private static byte[] intArrayToByteArray(ArrayList<Integer> code) {
        byte[] byteArray = new byte[code.size()*4];
        for (int i = 0; i < code.size(); i++) {
            byte[] number = ByteBuffer.allocate(4).putInt(code.get(i)).array();
            byteArray[i*4] = number[0];
            byteArray[i*4 + 1] = number[1];
            byteArray[i*4 + 2] = number[2];
            byteArray[i*4 + 3] = number[3];
        }
        return byteArray;
    }

}

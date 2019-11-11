package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;
import domain.dataStructure.Trie;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Lzw extends Lz78 {

    private static final int ASCII_LENGTH = 256;

    public Lzw() {
        super();
    }

    private void initializeDecodingDictionary(Map<Integer, String > decodingDictionary) {
        for (int i = 0; i < ASCII_LENGTH; i++) {
            decodingDictionary.put(i, Character.toString((char)i));
        }
    }

    private void initializeEncodingDictionary(Trie encodingDictionary) {
        for (int i = 0; i < ASCII_LENGTH; i++) {
            encodingDictionary.insert(Character.toString((char)i));
        }
    }

    @Override
    public String encode(String file) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
        encodingDictionary = new Trie();
        initializeEncodingDictionary(encodingDictionary);

        StringBuilder newText = new StringBuilder();
        int i = 0;
        while (i < file.length()) {
            StringBuilder word = new StringBuilder();
            boolean found = encodingDictionary.contains(word.append(file.charAt(i)).toString());
            while (i + 1 < file.length() && (found = encodingDictionary.contains(word.append(file.charAt(i+1)).toString()))) {
                ++i;
            }
            if (!found) {
                encodingDictionary.insert(word.toString());
                newText.append(encodingDictionary.getIndexOf(word.deleteCharAt(word.length()-1).toString())-1);
                newText.append(';');
            }
            else {
                newText.append(encodingDictionary.getIndexOf(word.toString())-1);
                newText.append(';');
            }
            ++i;
        }

        return newText.toString();
    }

    @Override
    public String decode(String file) {
        // ENCODING WITH LZW
        System.out.println("Decoding file with LZW");
        if (file.length() == 0) {
            return "";
        }
        HashMap<Integer, String> decodingDictionary = new HashMap<>();
        initializeDecodingDictionary(decodingDictionary);
        StringBuilder newText = new StringBuilder();
        char firstCharacter;
        int[] codes = Arrays.stream(file.split(";")).mapToInt(Integer::parseInt).toArray();
        newText.append(decodingDictionary.get(codes[0]));
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

        return newText.toString();
        /*System.out.println("Decoding file with LZW");
        if (file.length() <= 0) {
            return "";
        }
        HashMap<Integer, String> decodingDictionary = new HashMap<>();
        initializeDecodingDictionary(decodingDictionary);
        int[] codes = Arrays.stream(file.split(";")).mapToInt(Integer::parseInt).toArray();
        String newText = "";
        int oldCode = codes[0];
        String character = decodingDictionary.get(oldCode);
        newText += character;
        int i = 1;
        while (i < codes.length) {
            int newCode = codes[i];
            String word = "";
            if (decodingDictionary.containsKey(newCode)) {
                word = decodingDictionary.get(newCode);
            }
            else {
                word = decodingDictionary.get(oldCode);
                word += character;
            }
            newText += word;
            character = Character.toString(word.charAt(0));
            decodingDictionary.put(decodingDictionary.size(), decodingDictionary.get(oldCode) + character);
            oldCode = newCode;
            ++i;
        }
        return newText;*/
    }
}

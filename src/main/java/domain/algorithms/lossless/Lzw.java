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

    @Override
    public String encode(String file) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZW");
        encodingDictionary = new Trie();
        int i = 0;
        String prefix = "";
        String newText = "";
        while (i < file.length()) {
            String nextCharacter = Character.toString(file.charAt(i));
            if (encodingDictionary.contains(prefix + nextCharacter) || ((prefix+nextCharacter).length() == 1 && (prefix+nextCharacter).charAt(0) < ASCII_LENGTH)) {
                prefix += nextCharacter;
            }
            else {
                encodingDictionary.insert(prefix + nextCharacter);
                if (prefix.length() == 1 && prefix.charAt(0) < ASCII_LENGTH) {
                    newText += (int)prefix.charAt(0) + ";";
                }
                else {
                    newText += Integer.toString(encodingDictionary.getIndexOf(prefix) + ASCII_LENGTH - 1) + ";";
                }
                prefix = nextCharacter;
            }
            ++i;
        }
        if (prefix.length() == 1 && prefix.charAt(0) < ASCII_LENGTH) {
            newText += (int)prefix.charAt(0) + ";";
        }
        else {
            newText += Integer.toString(encodingDictionary.getIndexOf(prefix) + ASCII_LENGTH - 1) + ";";
        }
        return newText;
    }

    @Override
    public String decode(String file) {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
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
        return newText;

    }
}

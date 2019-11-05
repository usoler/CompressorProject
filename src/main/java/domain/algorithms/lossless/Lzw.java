package domain.algorithms.lossless;

import domain.dataStructure.Trie;

import java.io.File;

public class Lzw extends Lz78 {

    private static final int ASCII_LENGTH = 256;

    private void iniDictionary(Trie encodingDictionary) {
        for (int i = 0; i < ASCII_LENGTH; i++) {
            encodingDictionary.insert(Character.toString((char)i));
        }
    }

    @Override
    public void encode(String file) {
        // ENCODING WITH LZW
        System.out.println("Encoding file with LZ78");
        encodingDictionary = new Trie();
        iniDictionary(encodingDictionary);
        // super.encode(file);
    }

    @Override
    public void decode(File file) {
        // DECODING WITH LZW
        System.out.println("Decoding file with LZW");
        // super.decode(file);
    }
}

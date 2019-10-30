package domain.algorithms.lossless;

import domain.dataStructure.Trie;

public class Lzss extends Lz {

    public Lzss() {
        this.dictionary = new Trie();
    }

    @Override
    public void encode(String text) {
        // ENCODING WITH LZSS
        System.out.println("Encoding file with LZSS");
    }

    @Override
    public void decode(String text) {
        // DECODING WITH LZSS
        System.out.println("Decoding file with LZSS");
    }
}

package domain.algorithms.lossless;

import domain.dataStructure.Trie;

public class Lz78 extends Lz {

    public Lz78() {
        this.dictionary = new Trie();
    }

    @Override
    public void encode(String text) {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
    }

    @Override
    public void decode(String text) {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
    }
}

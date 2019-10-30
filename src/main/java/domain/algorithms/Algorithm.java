package domain.algorithms;

import domain.algorithms.lossless.Lz78;
import domain.dataStructure.Trie;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Algorithm {
    private Trie encodingDictionary;
    private Map<Integer, String> decodingDictionary;
    private AlgorithmInterface algorithmInterface;

    public Algorithm() {
        this.encodingDictionary = new Trie();
        this.decodingDictionary = new HashMap<>();
        this.algorithmInterface = new Lz78();
    }

    public void setAlgorithmInterface(AlgorithmInterface algorithmInterface) {
        this.algorithmInterface = algorithmInterface;
    }

    public void encodeFile(File file) {
        this.algorithmInterface.encode(file);
    }

    public void decodeFile(File file) {
        this.algorithmInterface.decode(file);
    }
}

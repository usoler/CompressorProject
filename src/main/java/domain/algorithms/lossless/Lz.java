package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;
import domain.dataStructure.Trie;

import java.util.Map;

public abstract class Lz implements AlgorithmInterface {
    protected Trie encodingDictionary;
    protected Map<Integer, String> decodingDictionary;


}

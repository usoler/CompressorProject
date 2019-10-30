package domain.algorithms.lossless;

import domain.algorithms.AlgorithmInterface;

import java.io.File;

import domain.dataStructure.Trie;

public class Lz78 extends Lz {
    Trie dictionary;


    public Lz78 ()
    {
        dictionary = new Trie();
    }

    @Override
        // ENCODING WITH LZ78
    public void encode(File file) {
        System.out.println("Encoding file with LZ78");
    }
    public void decode(File file) {
    @Override

        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
    }
    public void createDictionary(String text)
    {
        int length = text.length();
        int i = 0;
        int index = 0;
        while (i < length)
        {
            char c = text.charAt(i);
            String word = Character.toString(c);
            while (dictionary.contains(word))
            {
                word+=text.charAt(i);
                ++i;
            }
            dictionary.insert(word);
            ++i;
            ++index;
        }
    }

    {
    public void printDictionary()
        dictionary.printTrie();
    }
}

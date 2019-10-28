package domain.algorithms.lossless;

import domain.dataStructure.Trie;

public class Lz78 extends Lz {
    Trie dictionary;


    public Lz78 ()
    {
        dictionary = new Trie();
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
                ++i;
                word+=text.charAt(i);
            }
            dictionary.insert(word);
            ++i;
            ++index;
        }
    }

    public void printDictionary()
    {
        dictionary.printTrie();
    }
}

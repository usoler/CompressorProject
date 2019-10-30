package domain.algorithms.lossless;

import java.io.File;

import domain.dataStructure.Trie;

public class Lz78 extends Lz {

    @Override
    public void encode(String file) {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
        encodingDictionary = new Trie();
        int length = file.length();
        int i = 0;

        //int index = 0;
        while (i < length)
        {
            int index = 0;
            char c = file.charAt(i);
            String word = Character.toString(c);
            while (encodingDictionary.contains(word) && i < length)
            {
                if (index == 0)
                {
                    index = encodingDictionary.getIndexOf(word);
                }
                ++i;
                word+=file.charAt(i);

            }
            if (i <  length) {
                encodingDictionary.insert(word);
                ++i;
                //++index;
            }
            System.out.println("<"+index + "," + c + ">");
        }
        System.out.println("Encoded file with LZ78");
        System.out.println(encodingDictionary.getIndexOf("a"));
        System.out.println( encodingDictionary.getIndexOf("b"));
        System.out.println( encodingDictionary.getIndexOf("r"));
        System.out.println(encodingDictionary.getIndexOf("ac"));
        System.out.println(encodingDictionary.getIndexOf("ad"));
        System.out.println(encodingDictionary.getIndexOf("ab"));
        System.out.println( encodingDictionary.getIndexOf("ra"));
    }

    @Override
    public void decode(File file) {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
    }


    /*


    public void printDictionary() {
        dictionary.printTrie();
    }

    */
}

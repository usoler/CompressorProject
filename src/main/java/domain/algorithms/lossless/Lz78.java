package domain.algorithms.lossless;


import java.io.FileWriter;
import java.util.HashMap;

import domain.dataStructure.Trie;
import domain.fileManager.FileManager;

public class Lz78 extends Lz {

    @Override
    public String encode(String file) {
        // ENCODING WITH LZ78
        System.out.println("Encoding file with LZ78");
        encodingDictionary = new Trie();
        int length = file.length();
        int i = 0;
        String newText = "";
        boolean first = true;
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
                c = file.charAt(i);
                word+=c;

            }
            if (i <  length) {
                encodingDictionary.insert(word);
                ++i;
                //++index;
            }
            System.out.println("<"+index + "," + c + ">");
            if (first)
            {
                first = false;
                newText= Integer.toString(index);
            }
            else
            {
                newText+=Integer.toString(index);
            }
            newText+=';';
            newText+=c;
        }
        return newText;
    }

    @Override
    public String decode(String file) {
        // DECODING WITH LZ78
        System.out.println("Decoding file with LZ78");
        decodingDictionary = new HashMap<>();
        int length = file.length();
        int i = 0;
        int mapIndex = 1;
        while (i < length)
        {
            char c = file.charAt(i);
            String integer = Character.toString(c);
            ++i;
            while (file.charAt(i) != ';')
            {
                c = file.charAt(i);
                integer +=c;
                ++i;
            }
            int index = Integer.parseInt(integer);
            ++i;
            c = file.charAt(i);
            String word;
            if (index != 0)
            {
                word = decodingDictionary.get(index);
                word +=c;
            }
            else
            {
                word = Character.toString(c);
            }
            decodingDictionary.put(mapIndex,word);
            ++mapIndex;
            ++i;
        }
        String newText = decodingDictionary.get(1);
        for (int j = 2; j < mapIndex; ++j)
        {
            newText += decodingDictionary.get(j);
        }
        return newText;
    }



}

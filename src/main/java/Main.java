import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzss;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import java.io.IOException;
import java.util.List;
import domain.fileManager.FileImpl;

import domain.fileManager.FileManager;
import domain.dataStructure.Trie;

public class Main {

    static private void trieTest()
    {
        // Example Trie Data Structure
        Trie trie = new Trie();
        System.out.println(String.format("Inserted word 'an' with index %s", trie.insert("an")));
        System.out.println(String.format("Inserted word 'and' with index %s", trie.insert("and")));
        trie.insert("hol");
        System.out.println(String.format("Inserted word 'hol' with index %s", trie.getIndexOf("hol")));
        trie.insert("hola");
        System.out.println(String.format("Inserted word 'hola' with index %s", trie.getIndexOf("hola")));

        trie.printTrie();

        System.out.println(String.format("Index of 'an': %s", trie.getIndexOf("an")));
        System.out.println(String.format("Index of 'and': %s", trie.getIndexOf("and")));
        System.out.println(String.format("Index of 'hol': %s", trie.getIndexOf("hol")));
        System.out.println(String.format("Index of 'hola': %s", trie.getIndexOf("hola")));


        Algorithm algorithm = new Algorithm();
        algorithm.setAlgorithmInterface(new Lz78());
        algorithm.encodeFile(null);

        algorithm.setAlgorithmInterface(new Lzss());
        algorithm.decodeFile(null);

        algorithm.setAlgorithmInterface(new Lzw());
        algorithm.encodeFile(null);

        algorithm.setAlgorithmInterface(new Jpeg());
        algorithm.decodeFile(null);
    }


    static public void prueba(int ogt)
    {
        ogt = 3;
    }


    static public void LZ78Test()
    {
        Lz78 algorithm = new Lz78();
        algorithm.createDictionary("abracadabra");
        algorithm.printDictionary();
    }

    public static void main(String[] args) throws IOException {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();

        FileManager fileManager = new FileManager();

        fileManager.readFolder("input");
        List<FileImpl> filesRead = null;
        filesRead = fileManager.getListOfFiles();

        if (filesRead.get(0) != null)
        {
            System.out.println("Va a escribir archivo");
            fileManager.writeFile(filesRead.get(0),true);
            //TODO BETTER VERSION FOR WRITING
        }
        int ogt= 2;
        System.out.println(ogt);
        prueba(ogt);
        System.out.println(ogt);
        LZ78Test();
        //trieTest();
    }
}

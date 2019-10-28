import domain.algorithms.lossless.Lz78;
import domain.fileManager.FileImpl;
import domain.fileManager.FileManager;

import java.io.IOException;
import java.util.List;

import domain.dataStructure.Trie;

public class Main {

    static private void trieTest()
    {
        // Example Trie Data Structure
        Trie trie = new Trie();
        trie.insert("ex");
        trie.insert("exam");
        trie.insert("example");
        trie.insert("coco");

        trie.printTrie();

        trie.remove("exam");
        trie.remove("example");

        trie.printTrie();
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

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

/*
    static public void LZ78Test()
    {
        Lz78 algorithm = new Lz78();
        algorithm.createDictionary("abracadabra");
        algorithm.printDictionary();
    }
    */

    static public void testLZ78()
    {

    }

    public static void main(String[] args) throws IOException {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        abracadabraLZ78Test();
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

        //Algorithm algorithm = new Algorithm();
        //String result = algorithm.encodeFile("abracadabra");
        //fileManager.createFile(result,"output/LZ78Test/abracadabra");
        //fileManager.writeFile("output/LZ78Test/abracadabra",true);
        //algorithm.decodeFile("0;a0;b0;r1;c1;d1;b3;a");
        //LZ78Test();
        //trieTest();
    }

    public static void abracadabraLZ78Test()throws IOException{
        FileManager fileManager = new FileManager();
        Algorithm algorithm = new Algorithm();
        String result = algorithm.encodeFile("abracadabra");
        String pathname = "output/LZ78Test/Enconded(abracadabra)";
        fileManager.createFile(result,pathname);
        fileManager.writeFile(pathname,false);
    }
}

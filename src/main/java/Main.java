import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzss;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import domain.fileManager.FileImpl;

import domain.fileManager.FileManager;
import domain.dataStructure.Trie;

public class Main {

    static private void trieTest() throws UnsupportedEncodingException {
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
        //abracadabraLZ78Test();
        test2LZ78Test();
        logger.showLogs();
        byte[] bytes = {1,1,1,0};
        String s = new String(bytes,"UTF-32");
        System.out.println(s);
        byte[] bytes1 = s.getBytes("UTF-8");
        byte[] bytes2 = s.getBytes("UTF-16");
        byte[] bytes3 = s.getBytes("UTF-32");
        byte[] bytes4 = s.getBytes();

        System.out.println(bytesToBinary(bytes1));
        System.out.println(bytesToBinary(bytes2));
        System.out.println(bytesToBinary(bytes3));
        System.out.println(bytesToBinary(bytes4));


/*
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
        */

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

        String resultEncoding = algorithm.encodeFile("abracadabra");
        String pathnameEncoded = "output/LZ78Test/Encoded(abracadabra)";
        fileManager.createFile(resultEncoding,pathnameEncoded);
        fileManager.writeFile(pathnameEncoded,false);

        FileImpl file = fileManager.getFile(pathnameEncoded);

        String resultDecoding = algorithm.decodeFile(file.GetData());
        String pathnameDecoded = "output/LZ78Test/Decoded(abracadabra)";
        fileManager.createFile(resultDecoding,pathnameDecoded);
        fileManager.writeFile(pathnameDecoded,false);
    }

    static private String bytesToBinary(byte[] bytes)
    {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int k = 0; k < 8; k++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    public static void test2LZ78Test() throws IOException{
        String pathnameFolder = "input";
        String filename = "2500.txt";
        FileManager fileManager = new FileManager();
        fileManager.readFolder(pathnameFolder);
        System.out.println(fileManager.getListOfFiles().get(1).GetPathname());
        Algorithm algorithm = new Algorithm();

        FileImpl fileTest2 = fileManager.getFile("input/"+filename);

        String resultEncoding = algorithm.encodeFile(fileTest2.GetData());
        String pathnameEncoded = "output/LZ78Test/Encoded("+filename+")";
        fileManager.createFile(resultEncoding,pathnameEncoded);
        fileManager.writeFile(pathnameEncoded,false);

        FileImpl file = fileManager.getFile(pathnameEncoded);

        String resultDecoding = algorithm.decodeFile(file.GetData());
        String pathnameDecoded = "output/LZ78Test/Decoded("+filename+")";
        fileManager.createFile(resultDecoding,pathnameDecoded);
        fileManager.writeFile(pathnameDecoded,false);
    }
}

import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzss;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

import domain.fileManager.CompressedFile;
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

/*

        Algorithm algorithm = new Algorithm();
        algorithm.setAlgorithmInterface(new Lz78());
        algorithm.encodeFile(null);

        algorithm.setAlgorithmInterface(new Lzss());
        algorithm.decodeFile(null);

        algorithm.setAlgorithmInterface(new Lzw());
        algorithm.encodeFile(null);

        algorithm.setAlgorithmInterface(new Jpeg());
        algorithm.decodeFile(null);

 */
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

        char c =234;
        byte b = (byte)c;
        System.out.println(c);
        System.out.println(b);



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

        /*
        String resultEncoding = algorithm.encodeFile("abracadabra");
        String pathnameEncoded = "output/LZ78Test/Encoded(abracadabra)";
        fileManager.createFile(resultEncoding,pathnameEncoded);
        fileManager.writeFile(pathnameEncoded,false);

        FileImpl file = fileManager.getFile(pathnameEncoded);

        String resultDecoding = algorithm.decodeFile(file.GetData());
        String pathnameDecoded = "output/LZ78Test/Decoded(abracadabra)";
        fileManager.createFile(resultDecoding,pathnameDecoded);
        fileManager.writeFile(pathnameDecoded,false);

         */
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
        String filename = "verybig.txt";

        FileManager fileManager = new FileManager();
        fileManager.readFolder(pathnameFolder);
        Algorithm algorithm = new Algorithm();

        FileImpl fileTest = fileManager.getFile("input/"+filename);

        String pathnameEncoded = "output/LZ78Test/"+filename+".LZ78";

        byte[] result = algorithm.encodeFile(fileTest.getData());
        fileManager.createFile(result,pathnameEncoded);
        fileManager.writeFile(pathnameEncoded,false);

        FileImpl file = fileManager.getFile(pathnameEncoded);
        byte[] resultDecoded = algorithm.decodeFile(file.getData());


        String pathnameDecoded = "output/LZ78Test/(Decoded)"+filename;
        fileManager.createFile(resultDecoded,pathnameDecoded);
        fileManager.writeFile(pathnameDecoded,false);






        /*
        String pathnameFolder = "input";
        String filename = "2500.txt";
        FileManager fileManager = new FileManager();
        fileManager.readFolder(pathnameFolder);
        System.out.println(fileManager.getListOfFiles().get(1).getPathname());
        Algorithm algorithm = new Algorithm();

        FileImpl fileTest2 = fileManager.getFile("input/"+filename);

        String pathnameEncoded = "output/LZ78Test/Encoded("+filename+")";
        algorithm.encodeFile(fileTest2.getData(),pathnameEncoded);

        //fileManager.createCompressedFile(resultEncoding,pathnameEncoded);
        //fileManager.writeCompressedFile(pathnameEncoded,false);

        //FileImpl file = fileManager.getFile(pathnameEncoded);
        fileManager.readFolder("output");
        //CompressedFile fileTest2Compressed = fileManager.getCompressedFile("output/"+filename);
/*

        String resultDecoding = algorithm.decodeFile(fileTest2Compressed);
        String pathnameDecoded = "output/LZ78Test/Decoded("+filename+")";
        fileManager.createFile(resultDecoding,pathnameDecoded);
        fileManager.writeFile(pathnameDecoded,false);


         */


    }
}

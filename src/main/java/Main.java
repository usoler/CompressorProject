import domain.algorithms.Algorithm;
import domain.algorithms.lossless.Lz78;
import domain.algorithms.lossless.Lzss;
import domain.algorithms.lossless.Lzw;
import domain.algorithms.lossy.Jpeg;
import domain.dataStructure.Trie;

public class Main {

    public static void main(String[] args) {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();

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
}

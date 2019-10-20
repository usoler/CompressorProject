import domain.dataStructure.Trie;

public class Main {

    public static void main(String[] args) {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();

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
}

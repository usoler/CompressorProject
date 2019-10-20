import domain.dataStructure.Trie;

public class Main {

    public static void main(String[] args) {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();

        // Example Trie Data Structure
        Trie trie = new Trie();
        trie.insert("example");
        trie.insert("exam");
        trie.insert("an");
        trie.insert("analphabet");
        trie.insert("alpha");
        trie.insert("alphabet");

        trie.printTrie();

        if (trie.contains("bet")) {
            System.out.println("True");
        } else {
            System.out.println("False");
        }
    }
}

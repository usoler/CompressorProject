package domain.dataStructure;

import java.util.Map;
import java.util.Objects;

public class Trie {
    private TrieNode root;
    private int size;
    public Trie() {
        this.root = new TrieNode((char) 0);
        this.size = 0;
    }

    public boolean insert(String word) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty()) {
            return false;
        }

        if (this.root.insert(word, 0)) {
            ++this.size;
        }

        return true;
    }

    public boolean contains(String word) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty()) {
            return false;
        }

        return Objects.nonNull(this.root.lookup(word, 0));
    }

    public boolean remove(String word) {
        if (Objects.isNull(word)) {
            return false;
        }

        if (this.root.remove(word, 0)) {
            --this.size;
            return true;
        }

        return false;
    }

    public void printTrie() { // TODO: eliminar, es solo para testear
        System.out.println("Root");
        Map<Character, TrieNode> children = this.root.getChildren();

        for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
            System.out.println(String.format("%s : %s", entry.getKey(), entry.getValue().getOccurances()));
            printRecursive(entry.getValue());
        }
    }

    private void printRecursive(TrieNode node) { // TODO: eliminar, es solo para testear
        Map<Character, TrieNode> children = node.getChildren();
        if (Objects.nonNull(children)) {
            for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
                System.out.println(String.format("%s : %s", entry.getKey(), entry.getValue().getOccurances()));
                printRecursive(entry.getValue());
            }
        }
    }
}

package domain.dataStructure;

import java.util.Map;
import java.util.Objects;

public class Trie {
    // *****************************************************************
    //  State
    // *****************************************************************
    private TrieNode root;
    private int size;

    // *****************************************************************
    //  Constructor
    // *****************************************************************
    public Trie() {
        this.root = new TrieNode((char) 0);
        this.size = 0;
    }

    // *****************************************************************
    //  Getters
    // *****************************************************************
    public TrieNode getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }

    // *****************************************************************
    //  Public methods
    // *****************************************************************
    public int insert(String word) {
        if (word == null) {
            return -1;
        }

        int nextIndex = this.size + 1;

        if (this.root.insert(word, 0, nextIndex) > 0) {
            ++this.size;
        }

        return nextIndex;
    }

    public TrieNode contains(String word) {
        if (word == null) {
            return null;
        }

        return contains(word, this.root,0);
    }

    public TrieNode contains(String word, TrieNode node, int position ) {
        if (word == null) {
            return null;
        }

        return node.lookup(word, position);
    }

    public void printTrie() { // TODO: eliminar, es solo para testear
        System.out.println("Root");
        Map<Character, TrieNode> children = this.root.getChildren();

        for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
            System.out.println(String.format("Key '%s' : '%s' occurances", entry.getKey(), entry.getValue().getOccurances()));
            printRecursive(entry.getValue());
        }
    }

    public int getIndexOf(String word) {
        if (word == null) {
            return -1;
        }

        return getIndexOf(word,this.root,0);
    }

    public int getIndexOf(String word, TrieNode node, int position) {
        if (word == null) {
            return -1;
        }

        return node.lookup(word, position).getIndex();
    }

    // *****************************************************************
    //  Private methods
    // *****************************************************************
    private void printRecursive(TrieNode node) { // TODO: eliminar, es solo para testear
        Map<Character, TrieNode> children = node.getChildren();
        if (Objects.nonNull(children)) {
            for (Map.Entry<Character, TrieNode> entry : children.entrySet()) {
                System.out.println(String.format("Key '%s' : '%s' occurances", entry.getKey(), entry.getValue().getOccurances()));
                printRecursive(entry.getValue());
            }
        }
    }
}

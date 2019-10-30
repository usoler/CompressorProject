package domain.dataStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class Trie {
    private static final Logger LOGGER = LoggerFactory.getLogger(Trie.class);

    // *****************************************************************
    //  State
    // *****************************************************************
    private TrieNode root;
    private int size;

    // *****************************************************************
    //  Constructor
    // *****************************************************************

    /**
     * Constructs an empty Hash Trie with a default root
     */
    public Trie() {
        this.root = new TrieNode((char) 0); // NULL
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

    /**
     * Inserts a given word in the Hash Trie
     *
     * @param word word to insert in the dictionary
     * @return the index of the inserted word
     */
    public int insert(String word) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty()) {
            LOGGER.warn(String.format("The word '%s' could not be inserted in the trie: is null or blank"));
            return -1;
        }

        int nextIndex = this.size + 1;

        LOGGER.debug(String.format("Inserting word '%s' with index '%s'", word, nextIndex));
        if (this.root.insert(word, 0, nextIndex) > 0) {
            LOGGER.debug("Word inserted successfully");
            ++this.size;
        } else {
            LOGGER.warn("The word could not be inserted in the trie");
        }

        return nextIndex;
    }

    /**
     * Check if the Hash Trie contains a given word
     *
     * @param word word to search in the dictionary
     * @return true if the trie contains the word, otherwise returns false
     */
    public boolean contains(String word) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty()) {
            LOGGER.warn(String.format("The word '%s' could not be searched: is null or blank", word));
            return false;
        }

        LOGGER.debug("Searching word in the trie");
        if (Objects.nonNull(this.root.lookup(word, 0))) {
            LOGGER.debug("Word searched successfully");
            return true;
        } else {
            LOGGER.debug("The word is not in the dictionary");
            return false;
        }
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
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty()) {
            return -1;
        }

        return this.root.lookup(word, 0).getIndex();
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

package domain.dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrieNode {

    private int index;
    private Map<Character, TrieNode> children;

    /**
     * Constructs an empty Hash Trie Node with a given character key
     *
     * @param character character key of the new node
     */
    TrieNode(char character) {
        this.index = -1; // NULL
        this.children = null;
        this.index = 0;
    }

    /**
     * Gets the node position index
     *
     * @return the node position index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Inserts a given word in the dictionary character by character
     *
     * @param word     word to insert in the dictionary
     * @param position character position to insert in the dictionary
     * @param index    index of the new dictionary word entry
     * @return the index of the inserted word
     */
    public int insert(String word, int position, int index) {
        if (word == null || position >= word.length() || position < 0) {
            return -1;
        }
        if (this.children == null) {
            this.children = new HashMap<>();
        }
        char currentCharacter = word.charAt(position);
        TrieNode node = this.children.get(currentCharacter);
        if (Objects.isNull(node)) {
            node = new TrieNode(currentCharacter);
            children.put(currentCharacter, node);
        }
        if (position == word.length() - 1) { // Last char
            node.index = index;
            return node.index;
        } else {
            return node.insert(word, position + 1, index);
        }
    }

    /**
     * Lookup for a given word in the dictionary character by character
     *
     * @param word     word to lookup in the dictionary
     * @param position character position to lookup in the dictionary
     * @return the trie node of the last character of the word
     */
    public TrieNode lookup(String word, int position) {
        if (word == null || position >= word.length() || position < 0 || Objects.isNull(this.children)) {
            return null;
        }
        if (position == word.length() - 1) {
            return this.children.get(word.charAt(position));
        } else {
            TrieNode node = this.children.get(word.charAt(position));
            if (Objects.isNull(node)) {
                return null;
            } else {
                return node.lookup(word, position + 1);
            }
        }
    }
}
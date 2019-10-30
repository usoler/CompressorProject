package domain.dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrieNode {
    // *****************************************************************
    //  State
    // *****************************************************************
    private char character;
    private int index;
    private int occurances;
    private int index;
    private Map<Character, TrieNode> children;

    // *****************************************************************
    //  Constructor
    // *****************************************************************
    TrieNode(char character) {
        this.character = character;
        this.index = -1; // NULL
        this.occurances = 0;
        this.children = null;
        this.index = 0;
    }

    // *****************************************************************
    //  Getters
    // *****************************************************************
    public char getCharacter() {
        return this.character;
    }

    public int getIndex() {
        return this.index;
    }

    public int getOccurances() {
        return this.occurances;
    }

    public Map<Character, TrieNode> getChildren() {
        return this.children;
    }

    // *****************************************************************
    //  Public methods
    // *****************************************************************
    public int insert(String word, int position, int index) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty() || position >= word.length()
                || position < 0) {
            return -1;
        }

        if (this.children == null) {
            this.children = new HashMap<Character, TrieNode>();
        }

        char currentCharacter = word.charAt(position);
        TrieNode node = this.children.get(currentCharacter);

        if (node == null) {
            node = new TrieNode(currentCharacter);
            children.put(currentCharacter, node);
        }

        ++this.occurances;

        if (position == word.length() - 1) { // Last char
            node.index = index;
            ++node.occurances;
            return node.index;
        } else {
            return node.insert(word, position + 1, index);
        }
    }

    public TrieNode lookup(String word, int position) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty() || position >= word.length()
                || position < 0 || Objects.isNull(this.children)) {
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




























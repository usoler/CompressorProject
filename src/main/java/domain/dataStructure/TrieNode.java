package domain.dataStructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrieNode {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrieNode.class);

    boolean DEBUG_LOGGER_ACTIVATE = false;
    boolean WARN_LOGGER_ACTIVATE = false;
    // *****************************************************************
    //  State
    // *****************************************************************
    private char character;
    private int index;
    private int occurances;
    private Map<Character, TrieNode> children;

    // *****************************************************************
    //  Constructor
    // *****************************************************************

    /**
     * Constructs an empty Hash Trie Node with a given character key
     *
     * @param character character key of the new node
     */
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

    /**
     * Inserts a given word in the dictionary character by character
     *
     * @param word     word to insert in the dictionary
     * @param position character position to insert in the dictionary
     * @param index    index of the new dictionary word entry
     * @return the index of the inserted word
     */
    public int insert(String word, int position, int index) {
        if (word == null || position >= word.length()
                || position < 0) {
            if (WARN_LOGGER_ACTIVATE) LOGGER.warn(String.format("The word '%s' could not be inserted", word));
            return -1;
        }

        if (this.children == null) {
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("The children of character '%s' are null", this.character));
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug("Creating a new HashMap for the children");
            this.children = new HashMap<Character, TrieNode>();
        }

        char currentCharacter = word.charAt(position);
        if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("The current character of word '%s' at position '%s' is '%s'",
                    word, position, currentCharacter));


        TrieNode node = this.children.get(currentCharacter);

        if (Objects.isNull(node)) {
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("This node with character '%s' does not have a child with character '%s'",
                        this.character, currentCharacter));
            node = new TrieNode(currentCharacter);

            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("Putting a node with character '%s'", currentCharacter));
            children.put(currentCharacter, node);
        }

        ++this.occurances;

        if (position == word.length() - 1) { // Last char
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("There is the last character, '%s', of word '%s' with index '%s'",
                    currentCharacter, word, index));
            node.index = index;
            ++node.occurances;
            return node.index;
        } else {
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("Inserting the substring '%s' of word '%s' with index '%s'",
                    word.substring(position + 1), word, index));
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
        if (word == null || position >= word.length()
                || position < 0 || Objects.isNull(this.children)) {
            if (WARN_LOGGER_ACTIVATE) LOGGER.warn(String.format("The word '%s' could not be searched", word));
            return null;
        }

        if (position == word.length() - 1) {
            if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("There is the last character, '%s', of word '%s' with index '%s'",
                    word.charAt(position), word, this.index));
            return this.children.get(word.charAt(position));
        } else {
            TrieNode node = this.children.get(word.charAt(position));

            if (Objects.isNull(node)) {
                if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("No child with character '%s'", word.charAt(position)));
                return null;
            } else {
                if (DEBUG_LOGGER_ACTIVATE) LOGGER.debug(String.format("Searching the substring '%s' of word '%s'",
                        word.substring(position + 1), word));
                return node.lookup(word, position + 1);
            }
        }
    }
}




























package domain.dataStructure;

public class Trie {

    private TrieNode root;
    private int size;

    /**
     * Constructs an empty new {@link Trie}
     */
    public Trie() {
        this.root = new TrieNode((char) 0);
        this.size = 0;
    }

    /**
     * Gets the Trie size
     *
     * @return the Trie size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Inserts a new word in the {@link Trie}
     *
     * @param word the word to insert
     * @return the position index of the inserted word. If it could not be inserted, then returns -1
     */
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

    /**
     * Gets the {@link TrieNode} that contains the given word
     *
     * @param word the searched word
     * @return the {@link TrieNode} that contains the given word. If the trie does not contain it, then it returns null
     */
    public TrieNode contains(String word) {
        if (word == null) {
            return null;
        }

        return contains(word, this.root, 0);
    }

    /**
     * Gets the {@link TrieNode} that contains a given word, from a specific node and a specific position
     *
     * @param word
     * @param node
     * @param position
     * @return
     */
    public TrieNode contains(String word, TrieNode node, int position) {
        if (word == null) {
            return null;
        }

        return node.lookup(word, position);
    }

    /**
     * Gets the position index int the trie of a given word
     *
     * @param word the searched word
     * @return the position index of the searched word. If the trie does not contains it, then it returns -1
     */
    public int getIndexOf(String word) {
        if (word == null) {
            return -1;
        }

        return getIndexOf(word, this.root, 0);
    }

    private int getIndexOf(String word, TrieNode node, int position) {
        if (word == null) {
            return -1;
        }

        return node.lookup(word, position).getIndex();
    }
}

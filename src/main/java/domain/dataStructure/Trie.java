package domain.dataStructure;

public class Trie {
    private TrieNode root;
    private int size;

    public Trie() {
        this.root = new TrieNode((char) 0);
        this.size = 0;
    }

    public TrieNode getRoot() {
        return this.root;
    }

    public int getSize() {
        return this.size;
    }

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

        return contains(word, this.root, 0);
    }

    public TrieNode contains(String word, TrieNode node, int position) {
        if (word == null) {
            return null;
        }

        return node.lookup(word, position);
    }

    public int getIndexOf(String word) {
        if (word == null) {
            return -1;
        }

        return getIndexOf(word, this.root, 0);
    }

    public int getIndexOf(String word, TrieNode node, int position) {
        if (word == null) {
            return -1;
        }

        return node.lookup(word, position).getIndex();
    }
}

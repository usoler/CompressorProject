package domain.dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrieNode {
    private char character;
    private int occurances;
    private int index;
    private Map<Character, TrieNode> children;

    TrieNode(char character) {
        this.character = character;
        this.occurances = 0;
        this.children = null;
        this.index = 0;
    }

    public Map<Character, TrieNode> getChildren() {
        return this.children;
    }

    public int getOccurances() {
        return this.occurances;
    }

    public boolean insert(String word, int position) {
        if (Objects.isNull(word) || word.isEmpty() || word.trim().isEmpty() || position >= word.length()
                || position < 0) {
            return false;
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

        if (position == word.length() - 1) {
            ++node.occurances;
            return true;
        } else {
            return node.insert(word, position + 1);
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

    public boolean remove(String word, int position) {
        if (Objects.isNull(this.children) || Objects.isNull(word)) {
            return false;
        }

        char currentCharacter = word.charAt(position);
        TrieNode node = this.children.get(currentCharacter);

        boolean result;
        if (Objects.isNull(node)) {
            return false;
        } else if (position == word.length() - 1) {
            int before = node.occurances;
            --node.occurances;
            result = before > 0;
        } else {
            --node.occurances;
            result = node.remove(word, position + 1);
        }

        if (Objects.isNull(node.children) && node.occurances == 0) {
            this.children.remove(node.character);
            if (this.children.size() == 0) {
                this.children = null;
            }
        }

        return result;
    }


}




























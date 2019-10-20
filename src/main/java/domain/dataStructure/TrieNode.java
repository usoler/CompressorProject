package domain.dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TrieNode {
    private char character;
    private Map<Character, TrieNode> children;

    TrieNode(char character) {
        this.character = character;
        children = null;
    }

    public Map<Character, TrieNode> getChildren() {
        return this.children;
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

        if (position == word.length() - 1) {
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


}




























package domain.dataStructure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrieTest {
    private static final String word = "example";

    private Trie trie;

    @Before
    public void setup() {
        trie = new Trie();
    }

    // ************************************ INSERT ************************************
    @Test
    public void verify_insert_returnsFalse_whenWordIsNull() {
        boolean response = this.trie.insert(null);

        Assert.assertFalse(response);
    }

    @Test
    public void verify_insert_returnsFalse_whenWordIsEmpty() {
        boolean response = this.trie.insert("");

        Assert.assertFalse(response);
    }

    @Test
    public void verify_insert_returnsFalse_whenWordIsBlank() {
        boolean response = this.trie.insert("  ");

        Assert.assertFalse(response);
    }

    @Test
    public void verify_insert_returnsTrue_whenInsertsValidWord() {
        boolean response = this.trie.insert(this.word);

        Assert.assertTrue(response);
        Assert.assertTrue(this.trie.contains(this.word));
    }
    // ********************************************************************************

    // *********************************** CONTAINS ***********************************
    @Test
    public void verify_contains_returnsFalse_whenWordIsNull() {
        boolean response = this.trie.contains(null);

        Assert.assertFalse(response);
    }

    @Test
    public void verify_contains_returnsFalse_whenWordIsEmpty() {
        boolean response = this.trie.contains("");

        Assert.assertFalse(response);
    }

    @Test
    public void verify_contains_returnsFalse_whenWordIsBlank() {
        boolean response = this.trie.contains("   ");

        Assert.assertFalse(response);
    }

    @Test
    public void verify_contains_returnsFalse_whenNotFound() {
        boolean response = this.trie.contains(this.word);

        Assert.assertFalse(response);
    }

    @Test
    public void verify_contains_returnsTrue_whenIsFound() {
        // Mock
        this.trie.insert(this.word);

        // Test
        boolean response = this.trie.contains(this.word);

        Assert.assertTrue(response);
    }
    // ********************************************************************************
}
package domain.dataStructure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrieTest {
    private static final String word = "example";
    private static final int INDEX_INVALID = -1;

    private Trie trie;

    @Before
    public void setup() {
        trie = new Trie();
    }

    // ************************************ INSERT ************************************
    @Test
    public void verify_insert_returnsInvalidIndex_whenWordIsNull() {
        int response = this.trie.insert(null);

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsInvalidIndex_whenWordIsEmpty() {
        int response = this.trie.insert("");

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsInvalidIndex_whenWordIsBlank() {
        int response = this.trie.insert("  ");

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsValidIndex_whenInsertsValidWord() {
        int validIndex = 1;
        int response = this.trie.insert(this.word);

        Assert.assertEquals(validIndex, response);
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
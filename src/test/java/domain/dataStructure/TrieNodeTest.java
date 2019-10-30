package domain.dataStructure;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class TrieNodeTest {
    private static final String word = "example";

    private TrieNode node;

    @Before
    public void setUp() {
        node = new TrieNode((char) 0);
    }

    /*// ************************************ INSERT ************************************
    @Test
    public void verify_insert_returnsFalse_whenWordIsNull() {
        int response = this.node.insert(null, 0, 1);

        Assert.assertTrue(response == -1);
    }

    @Test
    public void verify_insert_returnsFalse_whenWordIsEmpty() {
        int response = this.node.insert("", 0, 1);

        Assert.assertTrue(response == -1);
    }

    @Test
    public void verify_insert_returnsFalse_whenWordIsBlank() {
        int response = this.node.insert("   ", 0, 1);

        Assert.assertTrue(response == -1);
    }

    @Test
    public void verify_insert_returnsFalse_whenPositionIsGreaterOrEqual_thanWordLength() {
        boolean response = this.node.insert(this.word, this.word.length(), 1;

        Assert.assertFalse(response);
    }

    @Test
    public void verify_insert_returnsFalse_whenPositionIsNegative() {
        boolean response = this.node.insert(this.word, -1);

        Assert.assertFalse(response);
    }

    @Test
    public void verify_insert_returnsTrue_whenPositionIsLastChar() {
        boolean response = this.node.insert(this.word, this.word.length() - 1);

        String lastChar = String.valueOf(this.word.charAt(this.word.length() - 1));

        Assert.assertTrue(response);
        Assert.assertNotNull(this.node.lookup(lastChar, 0));
    }

    @Test
    public void verify_insert_returnsTrue_whenInsertsAValidAndFullWord() {
        boolean response = this.node.insert(this.word, 0);

        Assert.assertTrue(response);
        Assert.assertNotNull(this.node.lookup(this.word, 0));

    }
    // ********************************************************************************

    // ************************************ LOOKUP ************************************
    @Test
    public void verify_lookup_returnsNull_whenWordIsNull() {
        TrieNode response = this.node.lookup(null, 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenWordIsEmpty() {
        TrieNode response = this.node.lookup("", 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenWordIsBlank() {
        TrieNode response = this.node.lookup("   ", 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenPositionIsGreaterOrEqual_thanWordLength() {
        TrieNode response = this.node.lookup(this.word, this.word.length());

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenChildrenAreNull() {
        TrieNode response = this.node.lookup(this.word, 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenIsNotFound() {
        // Mock
        this.node.insert("program", 0);

        // Test
        TrieNode response = this.node.lookup(this.word, 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNode_whenIsFound() {
        // Mock
        this.node.insert(this.word, 0);

        // Test
        TrieNode response = this.node.lookup(this.word, 0);

        Assert.assertNotNull(response);
    }
    // *********************************************************************************/
}
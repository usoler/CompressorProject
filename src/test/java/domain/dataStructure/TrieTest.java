package domain.dataStructure;

import org.junit.*;

public class TrieTest {

    private static final String word = "example";
    private static final int INDEX_INVALID = -1;
    private static final int INDEX_VALID = 1;

    private Trie trie;

    @BeforeClass
    public static void startUp(){
        System.out.println("TRIE TEST STARTED");
    }

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
    public void verify_insert_returnsValidIndex_whenWordIsEmpty() {
        int response = this.trie.insert("");

        Assert.assertEquals(INDEX_VALID, response);
    }

    @Test
    public void verify_insert_returnsValidIndex_whenWordIsBlank() {
        int response = this.trie.insert("  ");

        Assert.assertEquals(INDEX_VALID, response);
    }

    @Test
    public void verify_insert_returnsValidIndex_whenInsertsValidWord() {
        int validIndex = 1;
        int response = this.trie.insert(this.word);

        Assert.assertEquals(validIndex, response);
        Assert.assertNotNull(this.trie.contains(this.word));
    }
    // ********************************************************************************

    // *********************************** CONTAINS ***********************************
    @Test
    public void verify_contains_returnsNull_whenWordIsNull() {
        TrieNode response  = this.trie.contains(null);

        Assert.assertNull(response);
    }

    @Test
    public void verify_contains_returnsNull_whenWordIsEmpty() {
        this.trie.insert("");

        TrieNode response = this.trie.contains("");

        Assert.assertNull(response);
    }

    @Test
    public void verify_contains_returnsNotNull_whenWordIsBlank() {
        this.trie.insert("  ");

        TrieNode response = this.trie.contains("  ");

        Assert.assertNotNull(response);
    }

    @Test
    public void verify_contains_returnsNull_whenNotFound() {
        TrieNode response = this.trie.contains("");

        Assert.assertNull(response);
    }

    @Test
    public void verify_contains_returnsNotNull_whenIsFound() {
        // Mock
        this.trie.insert(this.word);

        // Test
        TrieNode response = this.trie.contains(this.word);

        Assert.assertNotNull(response);
    }
    // ********************************************************************************

    @AfterClass
    public static void end()
    {
        System.out.println("TRIE TEST ENDED");
    }
}
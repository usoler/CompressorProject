package domain.dataStructure;

import org.junit.*;

public class TrieNodeTest {
    private static final String WORD_VALID = "example";
    private static final int INDEX_INVALID = -1;
    private static final int INDEX_VALID = 1;


    private TrieNode node;

    @BeforeClass
    public static void startUp(){
        System.out.println("TRIE NODE TEST STARTED");
    }

    @Before
    public void setUp() {
        node = new TrieNode((char) 0);

    }

    // ************************************ INSERT ************************************
    @Test
    public void verify_insert_returnsInvalidIndex_whenWordIsNull() {
        int response = this.node.insert(null, 0, 1);

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsInvalidIndex_whenWordIsEmpty() {
        int response = this.node.insert("", 0, 1);

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsValidIndex_whenWordIsBlank() {
        int response = this.node.insert("   ", 0, 1);

        Assert.assertEquals(INDEX_VALID, response);
    }

    @Test
    public void verify_insert_returnsInvalidIndex_whenPositionIsGreaterOrEqual_thanWordLength() {
        int response = this.node.insert(WORD_VALID, WORD_VALID.length(), 1);

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsInvalidIndex_whenPositionIsNegative() {
        int response = this.node.insert(WORD_VALID, -1, 1);

        Assert.assertEquals(INDEX_INVALID, response);
    }

    @Test
    public void verify_insert_returnsValidIndex_whenPositionIsLastChar() {
        int validIndex = 1;
        int response = this.node.insert(WORD_VALID, WORD_VALID.length() - 1, validIndex);

        String lastChar = String.valueOf(WORD_VALID.charAt(WORD_VALID.length() - 1));

        Assert.assertEquals(validIndex, response);
        Assert.assertNotNull(this.node.lookup(lastChar, 0));
    }

    @Test
    public void verify_insert_returnsValidIndex_whenInsertsAValidAndFullWord() {
        int validIndex = 1;
        int response = this.node.insert(WORD_VALID, 0, validIndex);

        Assert.assertEquals(validIndex, response);
        Assert.assertNotNull(this.node.lookup(WORD_VALID, 0));

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
        TrieNode response = this.node.lookup(WORD_VALID, WORD_VALID.length());

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenChildrenAreNull() {
        TrieNode response = this.node.lookup(WORD_VALID, 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNull_whenIsNotFound() {
        // Mock
        this.node.insert("program", 0, 1);

        // Test
        TrieNode response = this.node.lookup(WORD_VALID, 0);

        Assert.assertNull(response);
    }

    @Test
    public void verify_lookup_returnsNode_whenIsFound() {
        // Mock
        this.node.insert(WORD_VALID, 0,1);

        // Test
        TrieNode response = this.node.lookup(WORD_VALID, 0);

        Assert.assertNotNull(response);
    }
    // ********************************************************************************

    @AfterClass
    public static void end()
    {
        System.out.println("TRIENODE TEST ENDED");
    }
}
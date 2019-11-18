import domain.CarpetaTest;
import domain.FicheroTest;
import domain.algorithms.lossless.LzwTest;
import domain.dataStructure.TrieNodeTest;
import domain.dataStructure.TrieTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CarpetaTest.class,
        TrieNodeTest.class,
        TrieTest.class,
        FicheroTest.class,
        LzwTest.class
})
public class TestSuite {

}

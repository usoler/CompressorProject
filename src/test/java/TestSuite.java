import domain.algorithms.AlgorithmTest;
import domain.components.*;
import domain.dataStructure.TrieNodeTest;
import domain.dataStructure.TrieTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TrieNodeTest.class,
        TrieTest.class,
        AlgorithmTest.class,
        ConversorYCbCrComponentTest.class,
        SamplingComponentTest.class,
        DCTComponentTest.class,
        HuffmanComponentTest.class,
        QuantizationComponentTest.class,
        ZigZagComponentTest.class
})
public class TestSuite {

}

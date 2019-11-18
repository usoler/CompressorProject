import domain.CarpetaTest;
import domain.FicheroTest;
import domain.algorithms.AlgorithmTest;
import domain.algorithms.lossless.LzwTest;
import domain.components.*;
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
        //LzwTest.class,
        AlgorithmTest.class,
        ConversorYCbCrComponentTest.class,
        DownsamplingComponentTest.class,
        DCTComponentTest.class,
        HuffmanComponentTest.class,
        QuantizationComponentTest.class,
        ZigZagComponentTest.class
})
public class TestSuite {

}

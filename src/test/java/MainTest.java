import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTest {

    public static void main(String[] args) {
        JUnitCore jCore = new JUnitCore();
        Result result = jCore.run(TestSuite.class);

        if (result.wasSuccessful()) {
            System.out.println("THE TESTS EXECUTION WAS SUCCESSFUL");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }
}

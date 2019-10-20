import domain.fileManager.FileManager;
import domain.fileManager.FileReader;

public class Main {

    public static void main(String[] args) {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();
        FileManager.readAndWriteFolder("input");
    }
}

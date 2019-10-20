import domain.Reader;

public class Main {

    public static void main(String[] args) {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();
        Reader reader = new Reader();
        //reader.readSpecificFile("input/test.txt");
        reader.readAllFilesFromFolder("input");
    }
}

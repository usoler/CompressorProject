<<<<<<< HEAD
import domain.fileManager.FileImpl;
import domain.fileManager.FileManager;

import java.io.IOException;
import java.util.List;

=======
import domain.dataStructure.Trie;
>>>>>>> oriol

public class Main {

    public static void main(String[] args) throws IOException {
        // Example
        MyLoggerExample logger = new MyLoggerExample();
        logger.showLogs();
<<<<<<< HEAD
        FileManager fileManager = new FileManager();

        fileManager.readFolder("input");
        List<FileImpl> filesRead = null;
        filesRead = fileManager.getListOfFiles();

        if (filesRead.get(0) != null)
        {
            System.out.println("Va a escribir archivo");
            fileManager.writeFile(filesRead.get(0),true);
            //TODO BETTER VERSION FOR WRITING
        }


=======

        // Example Trie Data Structure
        Trie trie = new Trie();
        trie.insert("ex");
        trie.insert("exam");
        trie.insert("example");
        trie.insert("coco");

        trie.printTrie();

        trie.remove("exam");
        trie.remove("example");

        trie.printTrie();
>>>>>>> oriol
    }
}

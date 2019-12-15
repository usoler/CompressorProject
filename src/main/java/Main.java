import domain.IFile;
import domain.exception.CompressorException;
import data.fileManager.FileManager;

public class Main {

    public static void main(String[] args) throws CompressorException {
        /*
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PresentationController presentationController = new PresentationController();
                presentationController.init();
            }
        });

         */
        FileManager fileManager = new FileManager();
        fileManager.readFolder("input");
        IFile file = fileManager.getFile("input/test.txt");
        IFile file2 = fileManager.getFile("input/test2.txt");
        IFile folder1 = fileManager.getFile("input/testFolder");
        System.out.println(file.getName());
        System.out.println(file2.getName());
        System.out.println(folder1.getName());

    }
}

package domain.fileManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriterImpl {

    public static void writeToFile(FileImpl file, boolean append_value) throws IOException {
        //Removes "input/" from pathname and set its to "output/"
        String newPathname = "output" + "/" + file.GetPathname().substring(6,file.GetPathname().length());
        FileWriter writer = new FileWriter(newPathname,append_value);
        PrintWriter print_line = new PrintWriter(writer);

        print_line.printf("%s"+"%n", file.GetData());

        print_line.close();
    }

    public static void writeFromFolderToFile(File file, boolean append_value) throws IOException{

    }
}

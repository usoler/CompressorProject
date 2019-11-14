package domain.fileManager;

import java.io.*;

public class FileWriterImpl {

    public static void writeToFile(FileImpl file, boolean append_value) throws IOException {
        //Removes "input/" from pathname and set its to "output/"
        String newPathname = "output" + "/" + file.getPathname().substring(6,file.getPathname().length());

        File fileWritten = new File (newPathname);
        FileOutputStream fileOutputStream = new FileOutputStream(fileWritten);

        fileOutputStream.write(file.getData());
        fileOutputStream.close();



    }

//    public static void writeCompressedToFile(CompressedFile file, boolean append_value) throws IOException {
//        //Removes "input/" from pathname and set its to "output/"
//        String newPathname = "output" + "/" + file.getPathname().substring(6,file.getPathname().length());
//        FileWriter writer = new FileWriter(newPathname,append_value);
//        PrintWriter print_line = new PrintWriter(writer);
//
//        print_line.printf("%s"+"%n", file.getOutputStream() );
//
//        print_line.close();
//    }

    public static void writeFromFolderToFile(File file, boolean append_value) throws IOException{

    }
}

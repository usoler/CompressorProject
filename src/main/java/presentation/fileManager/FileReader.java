package presentation.fileManager;

import domain.Folder;
import domain.IFile;
import domain.exception.CompressorErrorCode;
import domain.exception.CompressorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Scanner;

public class FileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);
    private static Scanner scanner;
    private static FileCreator fileCreator;

    public FileReader(FileCreator fileCreator) {
        this.fileCreator = fileCreator;
    }

    public static void readFile(String pathname, IFile folder) throws CompressorException {
        File file = new File(pathname);
        if (file.isDirectory()) {
            readAllFilesFromFolder(pathname, folder);
        } else {
            InputStream inputStream = readFile(file, pathname, folder);
            closeInputStream(inputStream);
        }
    }

    public static void readAllFilesFromFolder(String folderPathName, IFile i_folder) throws CompressorException {
        File folderFile = new File(folderPathName);
        IFile folder = createFolder(folderFile, folderPathName);
        if (i_folder != null)
        {
            i_folder.addFile(folder);
        }
        for (File file : folderFile.listFiles()) {
            String pathname = folderPathName + "/" + file.getName();
            if (file.isFile()) {
                InputStream inputStream = readFile(file, pathname, folder);
                closeInputStream(inputStream);
            } else if (file.isDirectory()) {
                readAllFilesFromFolder(pathname,folder);
            }
        }
    }

    private static void closeInputStream(InputStream inputStream) throws CompressorException {
        try {
            inputStream.close();
        } catch (IOException e) {
            String message = "Failure to close the input stream";
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.CLOSE_INPUT_STREAM_FAILURE);
        }
    }

    private static FileInputStream readFile(File file, String pathname, IFile folder) throws CompressorException {
        FileInputStream fileInputStream = startInputStream(file);
        String format =obtainFormatOfFile(file.getName());
        byte[] binaryData = readDataFromFile(fileInputStream).getBytes();
        if (isCompressed(format))
        {
            fileCreator.createCompressedFile(binaryData, pathname, folder, file.getName(),binaryData.length ,format);
        }
        else
        {
            fileCreator.createDecompressedFile(binaryData, pathname, folder, file.getName(), binaryData.length, format);
        }
        return fileInputStream;
    }

    private static FileInputStream startInputStream(File file) throws CompressorException {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            String message = String.format("Failure to read the file '%s'", file.getName());
            LOGGER.error(message);
            throw new CompressorException(message, e, CompressorErrorCode.READ_INPUT_STREAM_FAILURE);
        }
    }

    private static String readDataFromFile(FileInputStream fileInputStream) {
        scanner = new Scanner(fileInputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private static Folder createFolder(File file, String folderPathname)
    {
        String folderName = file.getName();
        String formatFolder = obtainFormatOfFile(folderName);
        Folder folder = new Folder(folderName,formatFolder, folderPathname);
        return folder;
    }


    private static String obtainFormatOfFile(String name)
    {
        String format = null;
        for (int iterator = name.length()-1; iterator >=0; --iterator)
        {
            if (name.charAt(iterator) == '.')
            {
                break;
            }
            else
            {
                if (format == null) format = Character.toString(name.charAt(iterator));
                else format += name.charAt(iterator);
            }
        }
        invertString(format);
        return format;
    }

    private static String invertString(String string)
    {
        String result = null;
        for (int iterator = 0; iterator < string.length(); ++iterator)
        {
            if (result==null) result = Character.toString(string.charAt(iterator));
            else result += string.charAt(iterator);
        }
        return result;
    }

    private static boolean isCompressed(String format){
        if (format == "lz78" || format == "lzw" || format == "jpeg")
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

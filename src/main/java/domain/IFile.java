package domain;

import java.util.ArrayList;

public interface IFile {

    /**
     * Gets the file pathname
     *
     * @return the file pathname
     */
    String getPathname();

    /**
     * Sets the file pathname with a given pathname
     *
     * @param pathname the pathname to set
     */
    void setPathname(String pathname);

    /**
     * Gets the file data bytes
     *
     * @return the file data bytes
     */
    byte[] getData();

    /**
     * Sets the file data bytes with some given data bytes
     *
     * @param data the data bytes to set
     */
    void setData(byte[] data);

    /**
     * Gets the number of files of this file
     *
     * @return the number of files
     */
    int getNumberOfFiles();

    /**
     * Gets the file name
     *
     * @return the file name
     */
    String getName();

    /**
     * Sets the file name with a given name
     *
     * @param name the name to set
     */
    void setName(String name);

    /**
     * Gets the file format
     *
     * @return the file format
     */
    String getFormat();

    /**
     * Sets the file format with a given format
     *
     * @param format the format to set
     */
    void setFormat(String format);

    /**
     * Gets the {@link ArrayList<IFile>} of this file
     *
     * @return the {@link ArrayList<IFile>}
     */
    ArrayList<IFile> getFiles();

    /**
     * Sets the files of this file with a given {@link ArrayList<IFile>}
     *
     * @param files the {@link ArrayList<IFile>} to set
     */
    void setFiles(ArrayList<IFile> files);

    /**
     * Gets the file size
     *
     * @return the file size
     */
    int getSize();

    /**
     * Sets the file size with a given size
     *
     * @param size the size to set
     */
    void setSize(int size);

    /**
     * Adds a file in the {@link ArrayList<IFile>}
     *
     * @param file the file to add
     */
    void addFile(IFile file);
}

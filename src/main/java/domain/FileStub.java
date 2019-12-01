package domain;

public class FileStub extends File {

    public FileStub(String name, String format, int size, Folder folder) {
        super(name, format, size, folder);
    }

    public String toString() {
        return String.format("Fichero{name='example', format='format', size=2 , folder=}");
    }

    public String getName() {
        return "example";
    }

    public String getFormat() {
        return "format";
    }

    public int getSize() {
        return 2;
    }

    public Folder getFolder() {
        return super.getFolder();
    }

    public void setFolder(Folder folder) {

    }
}

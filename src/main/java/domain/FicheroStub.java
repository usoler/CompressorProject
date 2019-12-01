package domain;


public class FicheroStub extends Fichero {

    public FicheroStub(String name, String format, int size, Carpeta folder) {
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

    public Carpeta getFolder() {
        return super.getFolder();
    }

    public void setFolder(Carpeta folder) {

    }
}

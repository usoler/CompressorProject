package domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FicheroTest {
    private Fichero file;
    private static final String name = "example";
    private static final String format = "txt";
    private static int size = 1;

    @Before
    public void setUp() {
        file = new Fichero(name, format, size, null);
    }

    @Test
    public void verify_toString_returnsFolderNameEmpty_whenFolderIsNull() {
        String response = file.toString();
        Assert.assertEquals("Fichero{name='example', format='txt', size=1 , folder=}", response);
    }

    @Test
    public void verify_equals_returnsTrue_whenNotSame() {
        Fichero file2 = new Fichero(new String(name), new String(format), size, null);
        Assert.assertEquals(file2, file);
    }

    @Test
    public void verify_equals_returnsTrue_whenFolderIsDifferent() {
        Carpeta folder = new Carpeta(name, format);
        Fichero file2 = new Fichero(new String(name), new String(format), size, folder);
        Assert.assertEquals(file2, file);
    }
}
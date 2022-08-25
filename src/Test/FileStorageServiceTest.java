package Test;

import main.Java.FileStorage;
import main.Java.FileStorageElement;
import main.Java.FileStorageService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceTest {
    private static final String path = "C:\\StorageTest";
    FileStorage storage;
    FileStorageElement file;

    @BeforeEach
    public void createEnv() {
        storage = new FileStorageService(path);
        storage.putFile("One", "1");
        storage.putFile("Two", "2");
        storage.putFile("Three", "3");
    }

    @AfterEach
    private void cleanStorage() {
        storage.readAllFileNames().stream().forEach(storage::removeFile);
    }


    @Test
    void getFile() {
        assertEquals(new FileStorageElement("Two", "2").toString(), storage.getFile("Two").toString());

    }

    @Test
    void getFileIfNotExists() {
        assertEquals(new FileStorageElement("Five", "").toString(), storage.getFile("Five").toString());

    }

    @Test
    void putFile() {
        storage.putFile("Four", "4");
        assertEquals(new FileStorageElement("Four", "4").toString(), storage.getFile("Four").toString());
    }

    @Test
    void removeFile() {
        assertEquals(true, storage.removeFile("One"));
    }

    @Test
    void removeFileIfnotExists() {
        ;
        assertEquals(false, storage.removeFile("six"));
    }

    @Test
    void readAllFileNames() {
        var result = storage.readAllFileNames().toArray(new String[0]);
        var etalon = new String[]{"One", "Two", "Three"};
        Arrays.sort(result);
        Arrays.sort(etalon);
        assertArrayEquals(etalon, result);

    }

    @Test
    void countFiles() {
        assertEquals(3, storage.countFiles());
    }
}
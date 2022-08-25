package Test.Java;

import main.Java.FileStorage;
import main.Java.FileStorageElement;
import main.Java.FileStorageService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @AfterAll
    public static void removeDir(){
        File file = new File(path);
        file.delete();
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
        assertEquals(false, storage.removeFile("six"));
    }

    @Test
    void returnAllFilesWithValues() {
        var result = new ArrayList<String>();
        var map = storage.returnAllFilesWithValues();
        for (String key : map.keySet()) {
            result.add(key + "=" + map.get(key));
        }
        assertLinesMatch(Arrays.asList("One=1", "Two=2", "Three=3"), result);
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

    @Test
    void multiThreadBehavior() {
        Thread thread1 = new MultiGenerateFiles(storage,100);
        Thread thread2 = new MultiGenerateFiles(storage,100);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread thread3 = new MultiRemoveFiles(storage);
        Thread thread4 = new MultiRemoveFiles(storage);
        thread3.start();
        thread4.start();
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0,storage.countFiles());
    }



    private static class MultiRemoveFiles extends Thread {
        private FileStorage storage;

        private MultiRemoveFiles(FileStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() { storage.readAllFileNames().stream().forEach(storage::removeFile); }
    }

    private static class MultiGenerateFiles extends Thread {
        private FileStorage storage;
        private int filesNumber;

        private MultiGenerateFiles(FileStorage storage, int filesNumber) {
            this.storage = storage;
            this.filesNumber = filesNumber;
        }

        private void generateFiles() {
            for (int i = 0; i < filesNumber; i++) {
                storage.putFile(storage.generateQuasiRandomString(), storage.generateQuasiRandomString());
            }
        }

        @Override
        public void run() { generateFiles(); }
    }

}
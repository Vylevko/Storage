package storageTests;

import storage.FileStorage;
import storage.FileStorageElement;
import storage.FileStorageService;
import org.junit.jupiter.api.*;


import java.io.File;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class FileStorageServiceTest {

    /**
     * We create some folder on disc for test files
     */
    private static final String path = "C:\\StorageTest";
    FileStorage storage;
    FileStorageElement file;
    /**
     * Before each test we create 3 files withe predefined names and content
     */
    @BeforeEach
    public void createEnv() {
        storage = new FileStorageService(path);
        storage.putFile("One", "1");
        storage.putFile("Two", "2");
        storage.putFile("Three", "3");
    }

    /**
     * Before each test we remove all files
     */
    @AfterEach
    private void cleanStorage() {
        storage.readAllFileNames().stream().forEach(storage::removeFile);
    }

    /**
     * After all tests we delete the the test folder
     */
    @AfterAll
    public static void removeDir(){
        File file = new File(path);
        file.delete();
    }
    /**
     * Get the content for the existing file by predefined fileName
     */
    @Test
    void getFile() {
        assertEquals(new FileStorageElement("Two", "2").toString(), storage.getFile("Two").toString());

    }
    /**
     * Try to get content for non existing file. Returns inserted name and "" value.
     */
    @Test
    void getFileIfNotExists() {
        assertEquals(new FileStorageElement("Five", "").toString(), storage.getFile("Five").toString());

    }
    /**
     * Creates new file fir input name and content on disc.
     */
    @Test
    void putFile() {
        storage.putFile("Four", "4");
        assertEquals(new FileStorageElement("Four", "4").toString(), storage.getFile("Four").toString());
    }
    /**
     * Removes existing file with specified name.
     */
    @Test
    void removeFile() {
        assertEquals(true, storage.removeFile("One"));
    }
    /**
     * Removes nothing if the file with specified name doesn't exist.
     */
    @Test
    void removeFileIfnotExists() {
        assertEquals(false, storage.removeFile("six"));
    }
    /**
     * Compares the predefined list with list of key=value.
     */
    @Test
    void returnAllFilesWithValues() {
        var result = new ArrayList<String>();
        var map = storage.returnAllFilesWithValues();
        for (String key : map.keySet()) {
            result.add(key + "=" + map.get(key));
        }
        assertLinesMatch(Arrays.asList("One=1", "Two=2", "Three=3"), result);
    }
    /**
     * String Array of all file names.
     */
    @Test
    void readAllFileNames() {
        var result = storage.readAllFileNames().toArray(new String[0]);
        var etalon = new String[]{"One", "Two", "Three"};
        Arrays.sort(result);
        Arrays.sort(etalon);
        assertArrayEquals(etalon, result);

    }
    /**
     * Number of all files.
     */
    @Test
    void countFiles() {
        assertEquals(3, storage.countFiles());
    }
    /**
     * We create number of files by 2 threads by generating of random keys and values.
     * We delete all existing files by 2 threads.
     */
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
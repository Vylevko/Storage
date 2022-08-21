package main;

import java.util.ArrayList;
import java.util.List;

public interface FileStorage {

    FileStorageElement getFile(String key);

    void putFile(String key, String value);

    boolean removeFile(String key);

    void showAllFilesWithValues();

    ArrayList<String> readAllFileNames();

    long countFiles();


}

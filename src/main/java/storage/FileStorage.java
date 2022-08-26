package storage;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: vylevko Date: 22.08.2022
 * Storage on disc.
 * Name of the fale equals to key
 */
public interface FileStorage {
    /**
     * Get file using input String key
     */

    FileStorageElement getFile(String key);

    /**
     * Put file using input value and name
     */
    void putFile(String key, String value);

    /**
     * Removes file for input name
     */
    boolean removeFile(String key);

    /**
     * Returns in Map all names and insides
     */
    Map<String,String> returnAllFilesWithValues();


    /**
     * Returns the list of file names
     */
    ArrayList<String> readAllFileNames();

    /**
     * Returns the number of all files in storage
     */
    long countFiles();

    /**
     * Generates key for file
     */
    String generateQuasiRandomString();


}

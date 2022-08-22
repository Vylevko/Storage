package main;

import java.util.ArrayList;
import java.util.List;
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
     * Showes in console all names and insides
     */
    void showAllFilesWithValues();

    /**
     * Showes in console the list of file names
     */
    void showAllFileNames();

    /**
     * Returns the list of file names
     */
    ArrayList<String> readAllFileNames();

    /**
     * Returns the number of all files in storage
     */
    long countFiles();


}

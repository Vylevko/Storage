package main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class FileStorageService implements FileStorage {

    private final String path;
    private static final int maxKeyLength = 128;

    public FileStorageService(String path) {
        this.path = openCreateStorage(path).getAbsolutePath();
    }

    public void dialog() {
        Scanner scan = new Scanner("ssss"); //to do
    }

    //to read storage from folder, if no such folder, create path
    private File openCreateStorage(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                System.out.println("Storage opened " + path);
            }
        } else {
            dir.mkdirs();
            System.out.println("Path doesn't exist " + path);
            System.out.println("Creating new folder tree " + dir.getAbsolutePath());
        }
        return dir;
    }

    //not to put keys longer then 128
    private static String checkKeyLength(String key) {
        if (key.length() > maxKeyLength) {
            key = key.substring(0, maxKeyLength);
            System.out.println("Key was truncated to 128");
        }
        return key;
    }

    // get element with key
    public FileStorageElement getFile(String key) {
        key = checkKeyLength(key);
        File file = getFilePath(key);
        try (FileInputStream fi = new FileInputStream(file);) {
            return new FileStorageElement(key, new String( fi.readAllBytes()));
        } catch (FileNotFoundException e) {
            System.out.println("No file with such key " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileStorageElement();
    }

    //save file on disc
    public void putFile(String key, String value) {
        key = checkKeyLength(key);
        try (FileOutputStream f = new FileOutputStream(getFilePath(key));) {
            f.write(value.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //remove
    public boolean removeFile(String key) {
        return getFilePath(key).delete();
    }

    //listing all elements in format Showing all key = value
    public void showAllFilesWithValues() {
        System.out.println("Showing all key = value");
        readAllFileNames().stream().forEach(e -> System.out.println(getFile(e).toString()));
    }

    //show list of file names
    public void showAllFileNames() {
        System.out.println("Showing list");
        readAllFileNames().stream().forEach(n -> System.out.println(n));
    }

    //create File object
    private File getFilePath(String key) {
        return new File(this.path + "\\" + key);
    }

    //reread list of file names from disc
    public ArrayList<String> readAllFileNames() {
        return new ArrayList<>(Arrays.asList(getFilePath("").list()));
    }

    public long countFiles() {
        return readAllFileNames().stream().count();
    }

}

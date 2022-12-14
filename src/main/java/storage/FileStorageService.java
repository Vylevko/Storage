package storage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class FileStorageService implements FileStorage {

    private final String path;
    private static final int maxKeyLength = 128;

    public FileStorageService(String path) {
        this.path = openCreateStorage(path).getAbsolutePath();
    }


    //to read storage from folder, if no such folder, create path
    private File openCreateStorage(String path) {
        var dir = new File(path);
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

    @Override
    public FileStorageElement getFile(String key) {
        key = checkKeyLength(key);
        var file = getFilePath(key);
        try (FileInputStream fi = new FileInputStream(file);) {
            return new FileStorageElement(key, new String( fi.readAllBytes()));
        } catch (FileNotFoundException e) {
            System.out.println("No file with such key " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new FileStorageElement(key,"");
    }

    @Override
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

    @Override
    public boolean removeFile(String key) { return getFilePath(key).delete(); }

    @Override
    public Map<String, String> returnAllFilesWithValues() {
        var nameValue = new HashMap<String,String>();
        readAllFileNames().stream().forEach(e -> nameValue.put(e,getFile(e).getValue()));
        return nameValue;
    }

    //create File object
    private File getFilePath(String key) {
        return new File(this.path + "\\" + key);
    }

    @Override
    public ArrayList<String> readAllFileNames() {
        return new ArrayList<>(Arrays.asList(getFilePath("").list()));
    }
    @Override
    public long countFiles() {
        return readAllFileNames().stream().count();
    }
    public  String generateQuasiRandomString() {
        return UUID.randomUUID().toString();
    }

}

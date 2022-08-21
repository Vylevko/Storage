import java.io.*;
import java.nio.file.*;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;


public class FileStorage {
    private volatile List<String> list;
    private String path;

    public FileStorage(String path) {
        File dir = openCreateStorage(path);
        this.list = new ArrayList<>(Arrays.asList(dir.list()));
        this.path = dir.getAbsolutePath();
    }

    public void dialog() {
        Scanner scan = new Scanner("ssss"); //to do
    }

    public List<String> getList() {
        return list;
    }
    //to read storage from folder, if no such folder, create path
    public File openCreateStorage(String path) {
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
    public static String checkKeyLength(String key) {
        if (key.length() > 128) {
            key = key.substring(0, 128);
            System.out.println("Key was truncated to 128");
        }
        return key;
    }
    //if key exists in internal list
    private boolean keyExists(String key) {
        return list.contains(key);
    }
    // get element with key
    public FileStorageElement getFile(String key) {
        key = checkKeyLength(key);
        FileStorageElement element = new FileStorageElement(key,"");
        File file = getFilePath(key);
            try (FileInputStream fi = new FileInputStream(file);
                 ObjectInputStream oi = new ObjectInputStream(fi);) {
                element = (FileStorageElement) oi.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("No file with such key "+ key);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        return element;
    }
    //actually not used
    private synchronized void updateList(String key, char action) {
        key = checkKeyLength(key);
        switch (action) {
            case 'R':
                list.remove(key);
                break;
            case 'I':
                list.add(key);
                break;
        }
    }
    //save file on disc
    public void putFile(String key, String value) {
        key = checkKeyLength(key);
        FileStorageElement element = new FileStorageElement(key, value);
        try (FileOutputStream f = new FileOutputStream(getFilePath(key));
             ObjectOutputStream o = new ObjectOutputStream(f);) {
            o.writeObject(element);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //remove
    public void removeFile(String key) {
            if (getFilePath(key).delete()) {
                System.out.println("Removed file=> " + key);
            }
    }
    //listing all elements in format Showing all key = value
    public void showAllFilesKeys() {
        System.out.println("Showing all key = value");
        refreshList();
        list.stream().forEach(e -> System.out.println(getFile(e).toString()));
    }
    //show list of file names
    public void showList() {
        System.out.println("Showing list");
        refreshList();
        list.stream().forEach(n -> System.out.println(n));
    }
    //create File object
    private File getFilePath(String key) {
        return new File(this.path + "\\" + key);
    }
    //reread list of file names from disc
    public void refreshList() {
        this.list = new ArrayList<>(Arrays.asList(getFilePath("").list()));
    }

    public long countFiles() {
        refreshList();
        return list.stream().count();
    }

}

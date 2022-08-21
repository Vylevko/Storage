import java.io.Serializable;

public class FileStorageElement implements Serializable {
    private String key;
    private String value;

    public FileStorageElement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
    public String toString(){
        return this.key+" = "+this.value;
    }
}

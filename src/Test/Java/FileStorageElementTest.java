package test.tava;

import main.java.FileStorageElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageElementTest {
    FileStorageElement element;

    @BeforeEach
    void createElement(){
        element = new FileStorageElement("AAAAA","BBBBBB");
    }
    @Test
    void setValue() {
        element.setValue("CCCCCC");
        assertEquals("CCCCCC",element.getValue());
    }

    @Test
    void getKey() {
        assertEquals("AAAAA",element.getKey());
    }

    @Test
    void getValue() {
        assertEquals("BBBBBB",element.getValue());
    }

    @Test
    void testToString() {
        assertEquals("AAAAA" + "="+ "BBBBBB",element.toString());
    }
}
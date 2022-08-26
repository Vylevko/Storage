package storageTests;

import storage.FileStorageElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageElementTest {
    FileStorageElement element;
    /**
     * Before each test we create FileStorageElement object
     */
    @BeforeEach
    void createElement(){
        element = new FileStorageElement("AAAAA","BBBBBB");
    }

    /**
     * Tests for getters and setters
     */
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


    /**
     * Test for toString method
     */
    @Test
    void testToString() {
        assertEquals("AAAAA" + "="+ "BBBBBB",element.toString());
    }
}
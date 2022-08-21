package service;

import model.FileStorageElement;

import java.util.List;

public interface FileStorageService {

    void addFile(String name, String content);

    void removeFile(String name);

    List<String> findAllFileNames();

    FileStorageElement findFileByName(String name);
}

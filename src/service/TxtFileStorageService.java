package service;

import model.FileStorageElement;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;


public class TxtFileStorageService implements FileStorageService {

    private final String path;
    private static final int NAME_LIMIT_SIZE = 128;

    public TxtFileStorageService(String path) {
        this.path = path;
    }

    @Override
    public List<String> findAllFileNames() {
        final var files = Optional.ofNullable(new File(path).list()).orElse(new String[0]);
        return stream(files).collect(toList());
    }

    @Override
    public FileStorageElement findFileByName(String name) {
        final var file = getFile(name).toPath();

        try (final var fileInputStream = Files.newInputStream(file)) {
            final var content = new String(fileInputStream.readAllBytes());
            return new FileStorageElement(name, content);
        } catch (IOException e) {
            System.out.println("No file with such key " + name);
            System.err.println(e);
        }
        return new FileStorageElement();
    }

    @Override
    public void addFile(String name, String content) {
        name = cropIfExceedsSizeLimit(name, NAME_LIMIT_SIZE);
        final var absoluteFilePath = getFile(name).toPath();

        try (var fileOutputStream = Files.newOutputStream(absoluteFilePath)) {
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFile(String key) {
        if (getFile(key).delete()) {
            System.out.println("Removed file=> " + key);
        }
    }

    private File getFile(String key) {
        return new File(path + "/" + key + ".txt");
    }

    private static String cropIfExceedsSizeLimit(String name, int size) {
        if (name.length() > size) {
            name = name.substring(0, size);
            System.out.println(format("Key was truncated to %s", size));
        }
        return name;
    }

}

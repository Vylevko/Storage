import service.FileStorageService;
import service.TxtFileStorageService;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

public class StorageApplication {
    public static void main(String[] args) throws InterruptedException {
        String path2 = "/Users/marharytaskokava/yury/My_repositiry/folder";
        String path = "C:\\Users\\Yuriy Vylevko\\Documents\\MyJava\\TestRep\\Storagexx";
        final var storageService = new TxtFileStorageService(path2);

        saveFindDeleteScenario(storageService);
        concurrencyWithTheSameFileNameScenario(storageService);
    }

    private static void saveFindDeleteScenario(FileStorageService storageService) {
        storageService.addFile(generateFileName(), "Hi, I'm content!");
        storageService.addFile(generateFileName(), "Hi, I'm content!");
        storageService.addFile(generateFileName(), "Hi, I'm content!");

        final var fileNames = storageService.findAllFileNames();
        System.out.println("Saved files' names: " + fileNames);

        final var anyFileName = fileNames.get(2).replace(".txt", "");
        var anyFileContent = storageService.findFileByName(anyFileName);
        System.out.println("Any file content: " + anyFileContent);

        storageService.removeFile(anyFileName);

        anyFileContent = storageService.findFileByName(anyFileName);
        System.out.println("Any file content: " + anyFileContent);
    }

    private static void concurrencyWithTheSameFileNameScenario(FileStorageService storageService) throws InterruptedException {
        final var executor = Executors.newFixedThreadPool(10);
        final var testTask0 = createTaskAddFile(storageService, "test", "hi0");
        final var testTask1 = createTaskAddFile(storageService, "test", "hi1");
        final var testTask2 = createTaskAddFile(storageService, "test", "hi2");
        final var testTask3 = createTaskAddFile(storageService, "test", "hi3");
        final var features = executor.invokeAll(List.of(testTask0, testTask1, testTask2, testTask3));

        Thread.sleep(1000);

        final var fileNames = storageService.findAllFileNames();
        System.out.println("File names: " + fileNames);
        System.out.println("Tasks were finished: " + features.stream().map(Future::isDone).collect(toList()));
    }

    private static Callable<String> createTaskAddFile(FileStorageService storageService, String fileName, String fileContent) {
        return () -> {
            storageService.addFile(fileName, fileContent);
            System.out.println(fileContent);
            return fileName;
        };
    }

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}

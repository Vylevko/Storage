package main.Java;

import main.Java.FileStorage;
import main.Java.FileStorageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageTest {
    public static void main(String[] args) throws IOException, InterruptedException {


        //Arrays.stream(dir.listFiles()).sequential().filter(File::isFile).forEach(e->System.out.println(e.getName()));


        String path = "C:\\Users\\Yuriy Vylevko\\Documents\\MyJava\\TestRep\\Storagexx";
        String path2 = "AAASddsadsad";
        FileStorage str = new FileStorageService(path);
        //List<String> = new str.readAllFileNames();
        Thread t1 = new MultiGenerateFiles(str);
        Thread t2 = new MultiRemoveFiles(str);
        t1.start();
        t1.join();
        str.countFiles();
        System.out.println(str.getFile("3fedabb781e7885d").toString());
        System.out.println(str.getFile("3fedabb781e7885daaaaaa").toString());
        str.getFile("3fedabb781e7885d");
        //System.out.println("str ="+str.countFiles());

        //System.out.println("str ="+str.countFiles());
        //System.out.println(str.getFile("3feff7ec366cddcd").toString());
        Thread t3 = new MultiGenerateFiles(str);
        t3.start();
        t2.start();

        System.out.println(str.countFiles());

        t3.join();
        t2.join();
        System.out.println(str.countFiles());
    }
    public static void generateFiles(int cnt, FileStorage stor){
        for (int i = 0; i < cnt; i++){
            stor.putFile(Long.toHexString(Double.doubleToLongBits(Math.random())),Long.toHexString(Double.doubleToLongBits(Math.random())));
        }
    }

    private static class MultiGenerateFiles extends Thread{
        private FileStorage storage;
        private MultiGenerateFiles(FileStorage storage){
            this.storage = storage;
        }
        @Override
        public void run(){
            generateFiles(100,storage);
        }


    }

    private static class MultiRemoveFiles extends Thread{
        private FileStorage storage;
        private MultiRemoveFiles(FileStorage storage){
            this.storage = storage;
        }
        @Override
        public void run(){
            storage.readAllFileNames().stream().forEach(storage::removeFile);
        }


    }
}

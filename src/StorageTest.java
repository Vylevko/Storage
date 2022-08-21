import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class StorageTest {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Storage storage = new Storage();
        //storage.showStorageKeys();
        //storage.setStorageKey("One","adyn".getBytes(StandardCharsets.UTF_8));
        //storage.setStorageKey("two","dwa".getBytes(StandardCharsets.UTF_8));
        //storage.showStorageKeys();
        //String path = "C:\\Users\\Yuriy Vylevko\\Music\\Projects\\Promsvyaz\\EC_payments\\TESTS\\results\\MIRAcqECPurchaseTest\\current\\[MIR EC] DEVH2H. 1.1. [Verif] (0) OK, [Auth] OK\\0_OnlineLauncher\\export_tables\\orig";
        //File dir = new File(path);

        //Arrays.stream(dir.listFiles()).sequential().filter(File::isFile).forEach(e->System.out.println(e.getName()));


        String path = "C:\\Users\\Yuriy Vylevko\\Documents\\MyJava\\TestRep\\Storagexx";
        String path2 = "AAASddsadsad";
        FileStorage str = new FileStorage(path);
        FileStorage str2 = new FileStorage(path);
        str.showList();
        System.out.println("str ="+str.countFiles());
        str2.removeFile("3feff7ec366cddcd");
        System.out.println("str ="+str.countFiles());
        System.out.println(str.getFile("3feff7ec366cddcd").toString());
        //Thread t1 = new Multi(str);
        //Thread t2 = new Multi(str);

        //str.showList();
        System.out.println(str.countFiles());

        //str.refreshList();
        //str.showAllFilesKeys();



       // System.out.println("through object");
        //str.showList();
       // System.out.println(FileStorage.checkKeyLength("AAAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }
    public static void generateFiles(int cnt,FileStorage stor){
        for (int i = 0; i < cnt; i++){
            stor.putFile(Long.toHexString(Double.doubleToLongBits(Math.random())),Long.toHexString(Double.doubleToLongBits(Math.random())));
        }
    }

    private static class Multi extends Thread{
        private FileStorage storage;
        private Multi(FileStorage storage){
            this.storage = storage;
        }
        @Override
        public void run(){
            generateFiles(100,storage);
        }


    }
}

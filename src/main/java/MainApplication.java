import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by minhvu on 5/28/17.
 */
public class MainApplication {

    public static int poolSize = 500;

    public static void main(String [] args) {
        File file = new File("/Users/minhvu/Desktop/testingFolder");
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        readingFile(file, executor);
    }

    public static void readingFile(File directory, Executor executor) {
        for (File file : directory.listFiles()) {
            executor.execute(new ReadingFile(file));
        }
    }
}

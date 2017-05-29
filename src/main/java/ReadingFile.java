import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

/**
 * Created by minhvu on 5/28/17.
 */
public class ReadingFile implements Runnable {

    File file;
    static String redisHost = "localhost";
    static int redisPort = 6379;
    RedisClient client = RedisClient.create(RedisURI.create(redisHost, redisPort));

    public ReadingFile (File file) {
        this.file = file;
    }
    public void run() {
        unzipFile(this.file);
    }

    public void unzipFile(File file) {
        try {
            GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(file));
            InputStreamReader reader = new InputStreamReader(gzis);
            BufferedReader in = new BufferedReader(reader);
            String readed;
            while ((readed = in.readLine()) != null) {
                String[] currentValue = readed.split(" ");
                RedisAsyncCommands<String, String> commands = client.connect().async();
                RedisFuture<Long> result = commands.exists(new String[]{currentValue[0]});
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

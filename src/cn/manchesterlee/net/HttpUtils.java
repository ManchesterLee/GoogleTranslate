package cn.manchesterlee.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2018/12/27
 */
public class HttpUtils {
    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    public static void call(String url, String method, Callback callback) {
        System.out.println(url);
        SERVICE.execute(() -> {
            try {
                HttpURLConnection connection = getConnection(url, method);
                if (connection.getResponseCode() >= 200 && connection.getResponseCode() <= 300) {
                    try (InputStream inputStream = connection.getInputStream(); BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        callback.onSuccess(sb.toString());
                    }
                } else {
                    callback.onFailure(connection.getResponseMessage());
                }
            } catch (IOException e) {
                callback.onFailure(e.getMessage());
            }
        });
    }

    private static HttpURLConnection getConnection(String url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(20 * 1000);
        connection.setUseCaches(true);
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        return connection;
    }

}

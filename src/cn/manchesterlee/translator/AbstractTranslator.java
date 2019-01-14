package cn.manchesterlee.translator;

import cn.manchesterlee.parser.ResultParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-14
 */
abstract class AbstractTranslator implements Translator {
    ResultParser resultParser;
    private OkHttpClient client;
    private static final long DEFAULT_CONNECT_TIMEOUT = 5;
    private static final long DEFAULT_READ_TIMEOUT = 5;

    AbstractTranslator(ResultParser resultParser) {
        this.resultParser = resultParser;
        client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    String request(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            throw new IOException(response.message());
        }
        ResponseBody body = response.body();
        if (body == null) {
            throw new IOException("Unknown Error");
        }
        return body.string();
    }
}

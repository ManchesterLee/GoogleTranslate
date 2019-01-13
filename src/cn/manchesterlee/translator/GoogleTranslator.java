package cn.manchesterlee.translator;

import cn.manchesterlee.domain.TranslateResults;
import cn.manchesterlee.parser.ResultParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Depends on Google Translate
 *
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public class GoogleTranslator implements Translator {
    private OkHttpClient client;
    private ResultParser resultParser;

    public GoogleTranslator(ResultParser resultParser) {
        this.resultParser = resultParser;
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public TranslateResults translate(String sourceLang, String targetLang, String text) throws Exception {
        Request request = new Request.Builder()
                .url("http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=auto&tl=zh_CN&q=" + text)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            throw new IOException(response.message());
        }
        ResponseBody body = response.body();
        if (body == null) {
            throw new IOException("Unknown Error");
        }
        String string = body.string();
        return resultParser.parse(string);
    }

}

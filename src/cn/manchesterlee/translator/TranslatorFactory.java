package cn.manchesterlee.translator;

import okhttp3.Request;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-14
 */
public class TranslatorFactory {
    private static final String API_GOOGLE_TRANSLATE = "http://translate.google.cn/translate_a/single?client=gtx&dt=t&dj=1&ie=UTF-8&sl=%s&tl=%s&q=%s";

    public static Request buildRequest(String fromLang, String toLang, String text, RequestFrom requestFrom) {
        return new Request.Builder()
                .url(String.format(API_GOOGLE_TRANSLATE, fromLang, toLang, text))
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .build();
    }

    public enum RequestFrom {
        /**
         * Google Translate
         */
        GOOGLE_TRANSLATE,
        /**
         * YouDao Translate
         */
        YOUDAO_DICT
    }

}

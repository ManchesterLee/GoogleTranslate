package cn.manchesterlee.translator;

import cn.manchesterlee.filter.CharacterFilter;
import cn.manchesterlee.parser.ResultParser;
import okhttp3.Request;

/**
 * Depends on Google Translate
 *
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public class GoogleTranslator extends AbstractTranslator {

    public GoogleTranslator(ResultParser resultParser) {
        super(resultParser);
    }

    @Override
    public String translate(String srcLang, String text) throws Exception {
        Request request = TranslatorFactory.buildRequest(srcLang, "zh_CN", CharacterFilter.filter(text), TranslatorFactory.RequestFrom.GOOGLE_TRANSLATE);
        String result = request(request);
        return resultParser.parse(result);
    }

}

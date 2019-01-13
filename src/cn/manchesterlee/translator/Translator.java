package cn.manchesterlee.translator;

import cn.manchesterlee.domain.TranslateResults;

/**
 * Translator
 *
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public interface Translator {

    TranslateResults translate(String sourceLang, String targetLang, String text) throws Exception;

}

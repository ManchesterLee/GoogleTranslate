package cn.manchesterlee.translator;

/**
 * Translator
 *
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public interface Translator {

    String translate(String srcLang, String text) throws Exception;

}

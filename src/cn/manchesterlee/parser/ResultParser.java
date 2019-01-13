package cn.manchesterlee.parser;

import cn.manchesterlee.domain.TranslateResults;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public interface ResultParser {

    TranslateResults parse(String result);

}

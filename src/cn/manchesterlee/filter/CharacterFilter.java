package cn.manchesterlee.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-14
 */
public class CharacterFilter {
    private static final List<String> INVALID_CHARACTERS = Collections.unmodifiableList(
            Arrays.asList("/\\*\\*", "\\*", "\n")
    );

    public static String filter(String text) {
        for (String invalidCharacter : INVALID_CHARACTERS) {
            text = text.replaceAll(invalidCharacter, "");
        }
        return text;
    }

}

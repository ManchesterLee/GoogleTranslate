package cn.manchesterlee.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-13
 */
public class TranslateResults {
    private Map<String, String> map;

    private TranslateResults(Map<String, String> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    public boolean hasResult(String lang) {
        return map.containsKey(lang);
    }

    public String getResult(String lang) {
        return map.get(lang);
    }

    @Override
    public String toString() {
        return "TranslateResults{" +
                "results=" + map +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, String> map;

        Builder() {
            map = new HashMap<>();
        }

        public Builder add(String lang, String result) {
            map.put(Objects.requireNonNull(lang), Objects.requireNonNull(result));
            return this;
        }

        public TranslateResults build() {
            return new TranslateResults(map);
        }
    }
}

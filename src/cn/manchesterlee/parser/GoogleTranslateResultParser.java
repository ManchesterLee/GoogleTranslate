package cn.manchesterlee.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * @author ManchesterLee  <a href="mailto:man.chester.lee.cn@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 2019-01-14
 */
public class GoogleTranslateResultParser implements ResultParser {

    @SuppressWarnings("unchecked")
    @Override
    public String parse(String result) throws Exception {
        ObjectMapper objectMapper = JsonFactory.getObjectMapper();
        Map<String, Object> map = objectMapper.readValue(result, Map.class);
        if (!map.containsKey("sentences")) {
            throw new Exception("Translate Error");
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("sentences");
        if (list.isEmpty()) {
            throw new Exception("Translate Error");
        }
        StringBuilder sb = new StringBuilder();
        for (Map<String, Object> transMap : list) {
            if (!transMap.containsKey("trans")) {
                continue;
            }
            sb.append(transMap.get("trans"));
        }
        return sb.toString();
    }

}

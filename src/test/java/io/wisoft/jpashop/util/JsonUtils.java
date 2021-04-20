package io.wisoft.jpashop.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * JSON 객체 역직렬화
     * @param json 변환할 JSON 객체
     * @param type 변환 타입
     * @param <T> type
     * @return JSON -> Class<T>
     */
    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    /**
     * Object 를 JSON 객체로 직렬화
     * @param obj obj -> json
     * @return JSON
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }
}

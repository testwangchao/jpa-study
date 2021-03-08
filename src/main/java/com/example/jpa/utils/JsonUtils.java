package com.example.jpa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    public static final  ObjectMapper DEFAULT_JSON_MAPPER = createDefaultJsonMapper();

    private JsonUtils(){};

    /**
     * 创建default json mapper
     * @return ObjectMapper
     */
    public static ObjectMapper createDefaultJsonMapper() {
        // 创建ObjectMapper对象
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    /**
     * 对象转为json
     * @param source 目标对象
     * @param mapper json mapper
     * @return json string
     * @throws JsonProcessingException 转换失败时抛出
     */
    @NonNull
    public static String objectToJson(@NonNull Object source, @NonNull ObjectMapper mapper)
            throws JsonProcessingException {
        Assert.notNull(source, "Source object must not be null");
        Assert.notNull(mapper, "Object mapper must not null");
        return mapper.writeValueAsString(source);
    }

    /**
     * 对象转为json
     * @param source 目标对象
     * @return json string
     * @throws JsonProcessingException 转换失败时抛出
     */
    @NonNull
    public static String objectToJson(@NonNull Object source) throws JsonProcessingException {
        return objectToJson(source, DEFAULT_JSON_MAPPER);
    }

    /**
     * json转为指定对象
     * @param json json string
     * @param type tatget object
     * @param <T> target object type
     * @return object specified type
     * @throws IOException 转为失败时抛出
     */
    @NonNull
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type)
            throws IOException {
        return jsonToObject(json, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * json转为指定对象
     * @param json json string
     * @param type tatget object
     * @param objectMapper objectMapper
     * @param <T> target object type
     * @return object specified type
     * @throws JsonProcessingException 转为失败时抛出
     */
    public static <T> T jsonToObject(@NonNull String json, @NonNull Class<T> type,
                                     @NonNull ObjectMapper objectMapper) throws JsonProcessingException {
        Assert.hasText(json, "Json content must not be blank");
        Assert.notNull(type, "Target type must not be null");
        Assert.notNull(objectMapper, "Object mapper must not null");
        return objectMapper.readValue(json, type);
    }

    /**
     * json转map
     * @return
     */
    public static Map jsonToMap(@NonNull String json, @NonNull ObjectMapper objectMapper) throws JsonProcessingException {
        return objectMapper.readValue(json, Map.class);
    }

    public static Map<String,Object> jsonToMap(@NonNull String json) throws JsonProcessingException {
        return jsonToMap(json, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap source map must not be empty
     * @param type object type must not be null
     * @param <T> target object type
     * @return the object specified type
     * @throws IOException throws when fail to convert
     */
    @NonNull
    public static <T> T mapToObject(@NonNull Map<String, ?> sourceMap, @NonNull Class<T> type)
            throws IOException {
        return mapToObject(sourceMap, type, DEFAULT_JSON_MAPPER);
    }

    /**
     * Converts a map to the object specified type.
     *
     * @param sourceMap source map must not be empty
     * @param type object type must not be null
     * @param objectMapper object mapper must not be null
     * @param <T> target object type
     * @return the object specified type
     * @throws IOException throws when fail to convert
     */
    @NonNull
    public static <T> T mapToObject(@NonNull Map<String, ?> sourceMap, @NonNull Class<T> type,
                                    @NonNull ObjectMapper objectMapper) throws IOException {
        Assert.notEmpty(sourceMap, "Source map must not be empty");

        // Serialize the map
        String json = objectToJson(sourceMap, objectMapper);

        // Deserialize the json format of the map
        return jsonToObject(json, type, objectMapper);
    }
}

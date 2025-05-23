package com.planning.occupancy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public static String toJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public static <T> T fromJson(String json, TypeReference<T> ref) throws JsonProcessingException {
        return mapper.readValue(json, ref);
    }

    public static <T> T fromJson(InputStream stream, Class<T> clazz) throws IOException {
        return mapper.readValue(stream, clazz);
    }

    public static <T> T fromJson(InputStream stream, TypeReference<T> ref) throws IOException {
        return mapper.readValue(stream, ref);
    }

    public static void toJson(OutputStream out, Object obj) throws IOException {
        mapper.writeValue(out, obj);
    }
}

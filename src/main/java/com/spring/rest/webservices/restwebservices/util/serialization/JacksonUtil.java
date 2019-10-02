package com.spring.rest.webservices.restwebservices.util.serialization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.StringWriter;

public class JacksonUtil {

//    private static final Logger logger = Logger.class(JacksonUtil.class);
    private static final String ERROR_MSG = "Failed to Convert Object %s to JSON string";

    private JacksonUtil() {
    }

    public static <T> String toString(T t) {
        ObjectMapper mapper = new ObjectMapper();
        final StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, t);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, t.getClass().getName()), e);
        }

        return sw.toString();
    }

    public static <T> String toStringByLowercaseField(T t) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        mapper.setAnnotationIntrospector(new JacksonMethodSerializer());

        final StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, t);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, t.getClass().getName()), e);
        }

        return sw.toString();
    }

    public static <T> String toStringByExcludeFieldAnnotation(T t) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        mapper.setAnnotationIntrospector(new JacksonMethodSerializer());
        final StringWriter sw = new StringWriter();

        try {
            mapper.writeValue(sw, t);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, t.getClass().getName()), e);
        }

        return sw.toString();
    }

    public static <T> String toStringIncludeOnlyAnnotationFeilds(T t){

        final ObjectMapper mapper = new ObjectMapper();
        // disable auto detection
        mapper.disable(MapperFeature.AUTO_DETECT_CREATORS,
                MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS,
                MapperFeature.AUTO_DETECT_IS_GETTERS);
        // if you want to prevent an exception when classes have no annotated properties
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        try {
            return mapper.writeValueAsString(t);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, t.getClass().getName()), e);
        }

        return "";
    }

    public static <T> T toObjectByExcludeFieldAnnotation(T t) {
        String str = toStringByExcludeFieldAnnotation(t);
        return toObject(str, t.getClass());
    }

    /**
     * Convert JSON to Object of Class type ?
     * Notes: for normal class
     * @param jsonString : Json String
     * @param outputClass : Object Class
     * @return
     */
    public static <T> T toObject(String jsonString, Class<?> outputClass) {

        T obj = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            obj = (T) mapper.readValue(jsonString, outputClass);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, outputClass.getClass().getName()), e);
        }

        return obj;
    }

    /**
     * Convert JSON to Object of Class type {@link TypeReference}
     * Notes: for generic class
     *
     * @param jsonString
     * @param typeReference
     * Example: TypeReference typeReference =new TypeReference<ApiResponseData<SearchQueryListBO>>(){};
     * @return
     */
    public static <T> T toObject(String jsonString, TypeReference typeReference) {

        T obj = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            obj = (T) mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
//            logger.error(String.format(ERROR_MSG, typeReference.getClass().getName()), e);
        }

        return obj;
    }
}

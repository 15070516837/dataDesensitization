package com.example.masking.myJackson;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @ProjectName: demo1
 * @Package: com.example.demo1
 * @ClassName: DataMaskConfiguration
 * @Author: json
 * @Description:
 * @Date: 2022/9/28 16:09
 * @Version: 1.0
 */
@Configuration(proxyBeanMethods = false)
public class DataMaskConfiguration {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass({Jackson2ObjectMapperBuilder.class})
    static class JacksonObjectMapperConfiguration {

    }

    @Bean
    @Primary
    ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        AnnotationIntrospector ai = objectMapper.getSerializationConfig().getAnnotationIntrospector();
        AnnotationIntrospector newAi = AnnotationIntrospectorPair.pair(ai, new DataMaskingAnnotationIntrospector());
        objectMapper.setAnnotationIntrospector(newAi);
        return objectMapper;
    }
}

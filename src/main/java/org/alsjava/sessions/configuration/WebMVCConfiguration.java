package org.alsjava.sessions.configuration;

import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.configuration.converter.FastJson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Value("${web.converter.fastjson2.enabled}")
    private Boolean useFastjson2AsHTTPConverter;

    @Override
    public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
        if (useFastjson2AsHTTPConverter) {
            log.info("Configuring FastJSON2 as HTTP Message Converter");
            builder.withJsonConverter(new FastJson2HttpMessageConverter());
        }
        builder.build();
    }
}

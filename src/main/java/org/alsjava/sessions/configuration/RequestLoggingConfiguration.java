package org.alsjava.sessions.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "log.request", name = "enabled", havingValue = "true")
public class RequestLoggingConfiguration {

    @Value("${log.request.max-payload-length}")
    private int logMaxPayloadLength;

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        log.info("Configuring Request Logging Filter");
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludePayload(true);
        commonsRequestLoggingFilter.setMaxPayloadLength(logMaxPayloadLength);
        commonsRequestLoggingFilter.setIncludeHeaders(true);
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        return commonsRequestLoggingFilter;
    }
}

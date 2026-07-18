package org.alsjava.sessions.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.HttpURLConnection;

@Slf4j
@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        log.info("Configuring RestClient");
        return builder
                .requestFactory(new SimpleClientHttpRequestFactory() {
                    @Override
                    protected void prepareConnection(@NonNull HttpURLConnection connection, @NonNull String httpMethod) throws IOException {
                        super.prepareConnection(connection, httpMethod);
                        connection.setInstanceFollowRedirects(true);
                    }
                })
                .build();
    }
}

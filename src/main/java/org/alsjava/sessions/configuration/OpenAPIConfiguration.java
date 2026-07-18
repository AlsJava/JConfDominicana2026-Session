package org.alsjava.sessions.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(BuildProperties.class)
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI openAPIConfiguration(BuildProperties buildProperties) {
        return new OpenAPI()
                .info(new Info()
                        .version(buildProperties.getVersion()));
    }
}

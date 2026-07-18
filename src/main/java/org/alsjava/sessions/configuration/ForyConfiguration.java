package org.alsjava.sessions.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.fory.BaseFory;
import org.apache.fory.Fory;
import org.apache.fory.ThreadSafeFory;
import org.apache.fory.config.Language;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ForyConfiguration {

    public static final String FORY_SERIALIZER = "FORY_SERIALIZER";

    @Value("${fory.max-depth}")
    private int foryMaxDepth;

    @Value("${fory.trusted-package}")
    private String foryTrustedPackage;

    @Value("${fory.pool.size}")
    private int foryPoolSize;

    @Bean
    public BaseFory fory() {
        log.info("Configuring FORY: '{}' and pool: {} with depth: {}", foryTrustedPackage, foryPoolSize, foryMaxDepth);
        ThreadSafeFory threadSafeFory = Fory.builder()
                .withLanguage(Language.JAVA)
                .withCodegen(true)
                .withAsyncCompilation(true)
                .registerGuavaTypes(false)
                .serializeEnumByName(true)
                .withLongArrayCompressed(true)
                .withIntArrayCompressed(true)
                .deserializeUnknownEnumValueAsNull(true)
                .suppressClassRegistrationWarnings(true)
                .requireClassRegistration(true)
                .withMaxDepth(foryMaxDepth)
                .buildThreadSafeForyPool(foryPoolSize);
        threadSafeFory.setTypeChecker((resolver, className) -> {
            log.info("Validating FORY class: {}", className);
            return className.startsWith(foryTrustedPackage);
        });
        return threadSafeFory;
    }
}

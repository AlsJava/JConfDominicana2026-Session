package org.alsjava.sessions.configuration;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.alsjava.sessions.test.UnitTest;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JacksonModule;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ObjectMapperConfigurationTest extends UnitTest {

    private final SerializationData serializationData = SerializationData.builder()
            .localDate(LocalDate.of(2025, Month.JANUARY, 1))
            .data("test")
            .integer(3)
            .build();

    @Test
    void localDateFormat() {
        String json = jsonMapper.writeValueAsString(serializationData);
        log.info("json: {}", json);
        assertNotNull(json);
        assertEquals("{\"localDate\":\"2025-01-01\",\"data\":\"test\",\"integer\":3}", json);
    }

    @Test
    void loadedModulesTest() {
        Collection<JacksonModule> registeredModules = jsonMapper.registeredModules();
        assertNotNull(registeredModules);
        assertFalse(registeredModules.isEmpty());
        for (Object registeredModuleId : registeredModules) {
            log.info("registeredModuleId: {}", registeredModuleId);
        }
        List<String> names = registeredModules.stream().map(JacksonModule::getModuleName).toList();
        assertEquals(registeredModules.size(), names.size());
        assertTrue(names.contains("BlackbirdModule"));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    private static class SerializationData {

        private LocalDate localDate;
        private String data;
        private int integer;
    }
}
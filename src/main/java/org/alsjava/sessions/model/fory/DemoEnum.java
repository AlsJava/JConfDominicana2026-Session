package org.alsjava.sessions.model.fory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DemoEnum {

    NORMAL("1"),
    EXTRA("2");

    @JsonValue
    private final String description;

    DemoEnum(String description) {
        this.description = description;
    }

    @JsonCreator
    public static DemoEnum createFrom(String key) {
        return Arrays.stream(values())
                .filter(demoEnum -> demoEnum.getDescription().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }
}

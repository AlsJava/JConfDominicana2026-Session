package org.alsjava.sessions.model.network;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class APIError {

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private int status;
    private String message;

    @Hidden
    private Integer line;
    @Hidden
    private Throwable trace;
}

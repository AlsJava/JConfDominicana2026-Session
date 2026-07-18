package org.alsjava.sessions.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserDto {

    private String name;
    private String description;

    private LocalDate date;

    private List<DataDto> data;
}

package org.alsjava.sessions.model.fory;

import lombok.*;
import org.alsjava.sessions.model.UserDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Wrapper {

    private String description;
    private List<UserDto> users;

}

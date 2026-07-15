package org.alsjava.sessions.model.network.response;

import lombok.*;
import org.alsjava.sessions.model.DemoDto;
import org.alsjava.sessions.model.network.AbstractResponse;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateDemoResponse extends AbstractResponse {

    private DemoDto demoDto;
}

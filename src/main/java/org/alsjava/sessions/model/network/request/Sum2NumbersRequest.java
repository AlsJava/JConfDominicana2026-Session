package org.alsjava.sessions.model.network.request;

import lombok.*;
import org.alsjava.sessions.model.network.AbstractRequest;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateDemoRequest extends AbstractRequest {

    private String name;
    private String description;
}

package org.alsjava.sessions.model.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public abstract class AbstractRequest extends AbstractNetwork {

    @JsonIgnore
    public void generateTid() {
        this.tid = UUID.randomUUID();
    }
}

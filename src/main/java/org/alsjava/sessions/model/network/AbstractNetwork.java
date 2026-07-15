package org.alsjava.sessions.model.network;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AbstractNetwork {

    protected UUID tid;

    @JsonIgnore
    public <T extends AbstractRequest> void copyTid(T request) {
        this.tid = request.getTid();
    }
}

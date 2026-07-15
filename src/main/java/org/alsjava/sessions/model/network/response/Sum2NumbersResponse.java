package org.alsjava.sessions.model.network.response;

import lombok.*;
import org.alsjava.sessions.model.network.AbstractResponse;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Sum2NumbersResponse extends AbstractResponse {

    private BigDecimal result;
}

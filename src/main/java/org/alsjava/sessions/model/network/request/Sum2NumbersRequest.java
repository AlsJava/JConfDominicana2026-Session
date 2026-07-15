package org.alsjava.sessions.model.network.request;

import lombok.*;
import org.alsjava.sessions.model.network.AbstractRequest;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Sum2NumbersRequest extends AbstractRequest {

    private BigDecimal number1;
    private BigDecimal number2;
}

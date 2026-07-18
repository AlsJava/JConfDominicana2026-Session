package org.alsjava.sessions.model;

import lombok.*;
import org.alsjava.sessions.model.fory.DemoEnum;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class DataDto {

    private String name;
    private String description;

    private int num;
    private double bigNumber;

    private BigDecimal extraBigNumber;

    private DemoEnum demoEnum;
}

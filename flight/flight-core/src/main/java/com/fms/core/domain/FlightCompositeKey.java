package com.fms.core.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FlightCompositeKey implements Serializable {

    private String vendor;
    private String source;
    private String destination;
    private String depDate;
    private String depTime;
}

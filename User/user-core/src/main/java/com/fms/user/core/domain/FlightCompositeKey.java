package com.fms.user.core.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightCompositeKey {

    private String vendor;
    private String source;
    private String destination;
    private String depDate;
    private String depTime;
}


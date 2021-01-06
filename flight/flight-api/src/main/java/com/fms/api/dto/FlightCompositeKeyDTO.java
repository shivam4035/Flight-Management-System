package com.fms.api.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightCompositeKeyDTO {

    private String vendor;
    private String source;
    private String destination;
    private String depDate;
    private String depTime;

}

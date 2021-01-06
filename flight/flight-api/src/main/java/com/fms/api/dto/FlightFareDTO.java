package com.fms.api.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightFareDTO {

    private String flightId;
    private int businessFare;
    private int economyFare;
    private String date;

}

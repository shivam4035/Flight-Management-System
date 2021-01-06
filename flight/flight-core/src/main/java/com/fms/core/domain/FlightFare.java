package com.fms.core.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "FlightFare")
public class FlightFare {

    @Id
    private String flightId;
    private int businessFare;
    private int economyFare;
    private String date;
}

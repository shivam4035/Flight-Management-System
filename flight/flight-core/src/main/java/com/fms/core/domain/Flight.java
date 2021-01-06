package com.fms.core.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EmbeddedId;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "FlightSchedule")
public class Flight {

    @Id
    private String id;
    @EmbeddedId
    private FlightCompositeKey flightId;
    private String arrDate;
    private String arrTime;
    private int totalBusiness;
    private int availableBusiness;
    private int totalEconomy;
    private int availableEconomy;

}

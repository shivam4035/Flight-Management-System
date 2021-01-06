package com.fms.api.dto;


import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightDTO {

    private String id;
    private FlightCompositeKeyDTO flightId;
    private String arrDate;
    private String arrTime;
    private int totalBusiness;
    private int availableBusiness;
    private int totalEconomy;
    private int availableEconomy;
}

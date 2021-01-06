package com.fms.user.core.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "Bookings")
public class Bookings {

    @ManyToOne
    private String mobile;
    @Id
    private String pnr;
    private String seatType;
    private int numberOfSeats;
    private List<Traveller> listOfTravellers;
    private FlightCompositeKey flightDetails;
    private String bookedDate;
}

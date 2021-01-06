package com.fms.user.api.dto;

import java.util.List;

import com.fms.api.dto.FlightCompositeKeyDTO;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookingsDTO {

    private String mobile;
    private String pnr;
    private String seatType;
    private int numberOfSeats;
    private List<TravellerDTO> listOfTravellers;
    private FlightCompositeKeyDTO flightDetails;
    private String bookedDate;

}

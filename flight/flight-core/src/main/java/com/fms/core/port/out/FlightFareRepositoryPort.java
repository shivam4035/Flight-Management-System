package com.fms.core.port.out;

import com.fms.core.domain.FlightFare;

public interface FlightFareRepositoryPort {

    boolean addFlightFare(FlightFare flightFare);

    FlightFare getFlightFare(String flightId);

    boolean updateFlightFare(FlightFare flightFare);
}

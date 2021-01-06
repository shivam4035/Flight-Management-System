package com.fms.core.port.in;

import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;

import java.util.List;

public interface FlightService {


    List<Flight> getFlightsBySrcAndDestAndDate(
            String src, String dest, String date, String berth, String travellers);


    Flight addFlight(Flight flight);

    Flight getFlightsById(String flightId);

    boolean addFlightFare(FlightFare flightFare);

    FlightFare getFlightFare(String flightId);

    boolean updateFlight(Flight flight, String flightId, String flag);

    boolean addIATACode(IATACode iataCode);

    List<IATACode> getIATACodes();

    boolean updateFlightFare(FlightFare flightFare);
}

package com.fms.core.port.out;

import com.fms.core.domain.Flight;

import java.util.List;

public interface FlightRepositoryPort {

    List<Flight> findBySrcAndDestAndDate(String src, String dest, String date);

    Flight addFlight(Flight flight);

    Flight findById(String flightId);

    boolean updateFlight(Flight flight, String flightId, String flag);
}



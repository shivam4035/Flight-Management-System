package com.fms.user.core.port.out;

import com.fms.user.core.domain.Flight;


public interface BookingClientPort {

    Flight getFlightById(String flightId);

    boolean updateFlight(Flight flight, String flightId);

}

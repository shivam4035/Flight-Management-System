package com.fms.user.core.port.out;


import com.fms.user.core.domain.Bookings;
import com.fms.user.core.domain.FlightCompositeKey;

import java.util.List;

public interface BookingRepositoryport {

    boolean addBooking(Bookings bookings, FlightCompositeKey flightCompositeKey);

    List<Bookings> getBookingsById(String mobile);


}

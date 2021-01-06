package com.fms.user.core.port.in;

import com.fms.user.core.domain.Bookings;

import java.util.List;

public interface UserService {


    Bookings addBooking(Bookings booking, String flightId);

    List<Bookings> getBookingsById(String mobile);


}

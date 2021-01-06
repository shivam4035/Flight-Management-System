package com.fms.user.adapter.database;


import com.fms.user.core.domain.Bookings;
import com.fms.user.adapter.persistence.BookingRepository;
import com.fms.user.core.domain.FlightCompositeKey;
import com.fms.user.core.exception.BookingNotFoundException;
import com.fms.user.core.exception.PNRAlreadyExistException;
import com.fms.user.core.port.out.BookingRepositoryport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserDBAdapter implements BookingRepositoryport {


    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public boolean addBooking(Bookings bookings, FlightCompositeKey flightDetails) {
        bookings.setFlightDetails(flightDetails);
        bookings.setBookedDate(new Date().toString());

        List<Bookings> bookingsList = bookingRepository.findByMobile(bookings.getMobile());
        for (int i = 0; i < bookingsList.size(); i++) {
            if (bookings.getPnr().equalsIgnoreCase(bookingsList.get(i).getPnr()))
                throw new PNRAlreadyExistException("This booking pnr already exists");
        }

        bookingRepository.save(bookings);
        return true;
    }


    @Override
    public List<Bookings> getBookingsById(String mobile) {
        List<Bookings> bookingsList = bookingRepository.findByMobile(mobile);
        if (bookingsList.size() == 0)
            throw new BookingNotFoundException("No booking with this mobile " + mobile);
        return bookingsList;
    }

}

package com.fms.user.adapter.controller;

import com.fms.user.api.UIPort.UserController;
import com.fms.user.api.dto.BookingsDTO;
import com.fms.user.core.domain.Bookings;
import com.fms.user.adapter.mapper.UserAndBookingMapper;
import com.fms.user.core.port.in.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserControllerAdapter implements UserController {

    @Autowired
    private UserAndBookingMapper mapper;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<BookingsDTO> addBooking(BookingsDTO bookingsDTO, String flightId) {

        Bookings bookings = mapper.convertToBookings(bookingsDTO);
        bookings = userService.addBooking(bookings, flightId);
        if (bookings == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        BookingsDTO ticketDetails = mapper.convertToBookingsDTO(bookings);
        return ResponseEntity.ok(ticketDetails);
    }

    @Override
    public ResponseEntity<List<BookingsDTO>> getBookingsById(String mobileNo) {
        List<Bookings> bookingsList = userService.getBookingsById(mobileNo);
        List<BookingsDTO> bookingsDTOList = mapper.convertToBookingsDTOList(bookingsList);
        return ResponseEntity.ok(bookingsDTOList);
    }
}

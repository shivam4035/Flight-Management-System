package com.fms.user.core.userServiceImpl;

import com.fms.user.core.dataLoader.UserServiceDataLoader;
import com.fms.user.core.domain.*;
import com.fms.user.core.port.out.BookingClientPort;
import com.fms.user.core.port.out.BookingRepositoryport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Mock
    private BookingRepositoryport bookingRepositoryport;
    @Mock
    private BookingClientPort bookingClientPort;


    Flight flight1;
    FlightCompositeKey flightDetails;

    Traveller traveller1, traveller2;
    List<Traveller> travellerList;

    Bookings bookings;

    @BeforeEach
    public void setUp() {


        flight1 = UserServiceDataLoader.createFlight();
        flightDetails = flight1.getFlightId();

        traveller1 = UserServiceDataLoader.createTraveller1();
        traveller2 = UserServiceDataLoader.createTraveller2();

        travellerList = new ArrayList<Traveller>();
        travellerList.add(traveller1);
        travellerList.add(traveller2);

        bookings = UserServiceDataLoader.createBookings(travellerList, flightDetails);

    }

    @Test
    void getBookingsById() {

        when(bookingRepositoryport.getBookingsById(bookings.getMobile())).thenReturn(Stream.of(bookings).
                collect(Collectors.toList()));

        assertThat(userServiceImpl.getBookingsById(bookings.getMobile()).get(0).getBookedDate()
        ).isEqualTo(bookings.getBookedDate());

        assertEquals(1, userServiceImpl.getBookingsById(bookings.getMobile()).size());

    }


    @Test
    void addBookings() {
        when(bookingClientPort.getFlightById(flight1.getId())).thenReturn(flight1);
        when(bookingRepositoryport.addBooking(bookings, flightDetails))
                .thenReturn(true);

        when(bookingRepositoryport.getBookingsById(bookings.getMobile())).thenReturn(Stream.of(bookings).
                collect(Collectors.toList()));
        when(bookingClientPort.updateFlight(flight1, flight1.getId())).thenReturn(true);

        assertEquals(userServiceImpl.addBooking(bookings, flight1.getId()),
                bookings);
    }
}






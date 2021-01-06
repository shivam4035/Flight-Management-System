package com.fms.user.dataLoader;

import com.fms.api.dto.FlightCompositeKeyDTO;
import com.fms.api.dto.FlightDTO;
import com.fms.user.adapter.mapper.UserAndBookingMapper;
import com.fms.user.api.dto.BookingsDTO;
import com.fms.user.api.dto.TravellerDTO;
import com.fms.user.core.domain.*;

import java.util.Date;
import java.util.List;

public class UserServiceDataLoader {


    public static UserAndBookingMapper mapper = new UserAndBookingMapper();


    public static Traveller createTraveller1() {
        Traveller traveller1 = new Traveller("xyz", "male", 12, 1);
        return traveller1;
    }

    public static Traveller createTraveller2() {
        Traveller traveller2 = new Traveller("qwer", "female", 44, 2);
        return traveller2;
    }

    public static Bookings createBookings(List<Traveller> travellerList, FlightCompositeKey
            flightDetails, String pnr) {


        Bookings bookings = new Bookings("7388343401", pnr, "Business",
                2, travellerList, flightDetails, new Date().toString());
        return bookings;

    }

    public static FlightDTO createFlightDTO() {
        FlightDTO flightDTO = new FlightDTO("spicejet", new FlightCompositeKeyDTO(
                "assd", "Delhi", "Mumbai", "12-08-21", "12:44"),
                "12-08-21", "03:43", 123, 24, 220, 190);
        return flightDTO;
    }

    public static Flight createFlight() {
        Flight flight = new Flight("spicejet", new FlightCompositeKey(
                "assd", "Delhi", "Mumbai", "12-08-21", "12:44"),
                "12-08-21", "03:43", 123, 24, 220, 190);
        return flight;
    }

    public static TravellerDTO createTravellerDTO(Traveller traveller) {
        TravellerDTO travellerDTO = mapper.convertToTravellerDTO(traveller);
        return travellerDTO;
    }

    public static BookingsDTO createBookingsDTO(Bookings bookings) {
        BookingsDTO bookingsDTO = mapper.convertToBookingsDTO(bookings);
        return bookingsDTO;
    }
}

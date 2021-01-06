package com.fms.user.core.dataLoader;

import com.fms.user.core.domain.*;

import java.util.Date;
import java.util.List;

public class UserServiceDataLoader {


    public static Traveller createTraveller1() {
        Traveller traveller1 = new Traveller("xyz", "male", 12, 1);
        return traveller1;
    }

    public static Traveller createTraveller2() {
        Traveller traveller2 = new Traveller("qwer", "female", 44, 2);
        return traveller2;
    }

    public static Bookings createBookings(List<Traveller> travellerList, FlightCompositeKey
            flightDetails) {
        Bookings bookings = new Bookings("7388343401", "27655876911", "Business",
                2, travellerList, flightDetails, new Date().toString());
        return bookings;

    }

    public static Flight createFlight() {
        Flight flight1 = new Flight("spicejet", new FlightCompositeKey(
                "assd", "Delhi", "Mumbai", "12-08-21", "12:44"),
                "12-08-21", "03:43", 123, 24, 220, 190);
        return flight1;
    }
}

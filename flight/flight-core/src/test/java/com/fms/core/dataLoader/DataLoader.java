package com.fms.core.dataLoader;

import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightCompositeKey;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;

public class DataLoader {

    public static Flight createFlight1() {
        Flight flight1 = new Flight("spicejet", new FlightCompositeKey(
                "assd", "Delhi", "Mumbai", "12-08-2020", "12:44"),
                "12-08-21", "03:43", 123, 24, 220, 190);
        return flight1;
    }

    public static Flight createFlight2() {
        Flight flight2 = new Flight("jet", new FlightCompositeKey(
                "qwwer", "Kolkata", "Mumbai", "14-08-21", "11:44"),
                "14-08-21", "02:40", 110, 54, 338, 110);
        return flight2;
    }

    public static Flight createFlight3() {
        Flight flight3 = new Flight("airways", new FlightCompositeKey(
                "zxxv", "Hyderabad", "Allahabad", "08-08-21", "12:12"),
                "08-08-21", "03:12", 120, 8, 400, 150);
        return flight3;

    }

    public static FlightFare createFlightFare(String flightId) {
        FlightFare flightFare = new FlightFare(flightId, 2500, 1600, "12-08-20");
        return flightFare;
    }

    public static IATACode createIATACode1() {
        return new IATACode("DEL", "Delhi");
    }

    public static IATACode createIATACode2() {
        return new IATACode("MUM", "Mumbai");
    }
}

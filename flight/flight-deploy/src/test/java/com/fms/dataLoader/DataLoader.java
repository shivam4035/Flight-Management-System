package com.fms.dataLoader;

import com.fms.adapter.Mapper.FlightMapper;
import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import com.fms.api.dto.IATACodeDTO;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightCompositeKey;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;

public class DataLoader {

    public static FlightMapper mapper = new FlightMapper();

    public static Flight createFlight1() {
        Flight flight1 = new Flight("spicejet", new FlightCompositeKey(
                "assd", "Delhi", "Mumbai", "12-08-21", "12:44"),
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

    public static FlightDTO createFlightDTO(Flight flight) {
        FlightDTO flight1DTO = mapper.convertToFlightDTO(flight);
        return flight1DTO;
    }

    public static FlightFare createFlightFare(String flightId) {
        FlightFare flightFare = new FlightFare(flightId, 2500, 1600, "12-08-20");
        return flightFare;
    }

    public static FlightFareDTO createFlightFareDTO(FlightFare flightFare) {
        FlightFareDTO flightFareDTO = mapper.convertToFlightFareDTO(flightFare);
        return flightFareDTO;
    }

    public static IATACode createIATACode1() {
        return new IATACode("DEL", "Delhi");
    }

    public static IATACode createIATACode2() {
        return new IATACode("MUM", "Mumbai");
    }

    public static IATACodeDTO createIATACode1DTO(IATACode iataCode) {
        return mapper.convertToIATACodeDTO(iataCode);
    }
}

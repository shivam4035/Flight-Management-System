package com.fms.core.FlightServiceImpl;

import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;
import com.fms.core.port.in.FlightService;
import com.fms.core.port.out.FlightFareRepositoryPort;
import com.fms.core.port.out.FlightRepositoryPort;
import com.fms.core.port.out.IATACodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private IATACodeRepositoryPort iataCodeRepositoryPort;

    @Autowired
    private FlightRepositoryPort flightRepositoryPort;

    @Autowired
    private FlightFareRepositoryPort flightFareRepositoryPort;

    @Override
    public List<Flight> getFlightsBySrcAndDestAndDate(String src, String dest, String date
            , String berth, String numberOfTravellers) {
        List<Flight> flightList = flightRepositoryPort.findBySrcAndDestAndDate(src, dest, date);
        int noOfTravellers = Integer.parseInt(numberOfTravellers);
        for (int i = 0; i < flightList.size(); i++) {

            Flight flight = flightList.get(i);

            if (berth.equalsIgnoreCase("business")) {
                if (flight.getAvailableBusiness() < noOfTravellers) {
                    flightList.remove(i);
                    i--;
                }
            } else {
                if (flight.getAvailableEconomy() < noOfTravellers) {
                    flightList.remove(i);
                    i--;
                }
            }
        }
        return flightList;
    }

    @Override
    public Flight addFlight(Flight flight) {
        Flight flight1 = flightRepositoryPort.addFlight(flight);
        return flight1;
    }

    @Override
    public Flight getFlightsById(String flightId) {
        return flightRepositoryPort.findById(flightId);

    }

    @Override
    public boolean addFlightFare(FlightFare flightFare) {
        boolean status = flightFareRepositoryPort.addFlightFare(flightFare);
        return status;
    }

    @Override
    public FlightFare getFlightFare(String flightId) {
        return flightFareRepositoryPort.getFlightFare(flightId);
    }

    @Override
    public boolean updateFlight(Flight flight, String flightId, String flag) {

        return flightRepositoryPort.updateFlight(flight, flightId, flag);

    }

    @Override
    public boolean addIATACode(IATACode iataCode) {
        boolean status = iataCodeRepositoryPort.addIATACode(iataCode);
        return status;
    }

    @Override
    public List<IATACode> getIATACodes() {
        return iataCodeRepositoryPort.getIATACodes();
    }

    @Override
    public boolean updateFlightFare(FlightFare flightFare) {
        return flightFareRepositoryPort.updateFlightFare(flightFare);
    }


}

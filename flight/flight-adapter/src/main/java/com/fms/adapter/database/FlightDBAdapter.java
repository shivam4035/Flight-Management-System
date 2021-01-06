package com.fms.adapter.database;

import com.fms.adapter.persistence.ICityCodeRepository;
import com.fms.adapter.persistence.IFlightFareRepository;
import com.fms.adapter.persistence.IFlightRepository;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightCompositeKey;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;
import com.fms.core.exception.FlightAlreadyExistException;
import com.fms.core.exception.FlightFareErrorExcpetion;
import com.fms.core.exception.FlightNotfoundException;
import com.fms.core.exception.FlightSeatsException;
import com.fms.core.port.out.FlightFareRepositoryPort;
import com.fms.core.port.out.FlightRepositoryPort;
import com.fms.core.port.out.IATACodeRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightDBAdapter implements FlightRepositoryPort, FlightFareRepositoryPort, IATACodeRepositoryPort {

    @Autowired
    private IFlightRepository iFlightRepository;

    @Autowired
    private IFlightFareRepository iFlightFareRepository;

    @Autowired
    private ICityCodeRepository iCityCodeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Flight> findBySrcAndDestAndDate(String src, String dest, String date) {

        IATACode sourceCode = iCityCodeRepository.findByCityName(src);
        IATACode destCode = iCityCodeRepository.findByCityName(dest);
        if (sourceCode == null) {
            throw new FlightNotfoundException("City " + src + " doesn't exist");
        }
        if (destCode == null) {
            throw new FlightNotfoundException("City " + dest + " doesn't exist");
        }
        if (sourceCode.getCityCode().equalsIgnoreCase(destCode.getCityCode()))
            throw new FlightNotfoundException("Source and Destination can't be same");
        List<Flight> availableFlights = iFlightRepository.findBySrcAndDestAndDate(sourceCode.getCityCode(), destCode.getCityCode(), date);

        if (availableFlights.size() == 0)
            throw new FlightNotfoundException("Flights Not Available");

        return availableFlights;
    }

    @Override
    public Flight addFlight(Flight flight) {

        Flight availableFlight;
        IATACode sourceCode = iCityCodeRepository.findByCityName(flight.getFlightId().getSource());
        IATACode destCode = iCityCodeRepository.findByCityName(flight.getFlightId().getDestination());
        if (sourceCode == null) {
            throw new FlightNotfoundException("City " + flight.getFlightId().getSource() + " doesn't exist");
        }
        if (destCode == null) {
            throw new FlightNotfoundException("City " + flight.getFlightId().getDestination() + " doesn't exist");
        }
        if (sourceCode.getCityCode().equalsIgnoreCase(destCode.getCityCode()))
            throw new FlightNotfoundException("Source and Destination can't be same");
        availableFlight = iFlightRepository.getFlight(flight.getFlightId().getVendor(),
                sourceCode.getCityCode(),
                destCode.getCityCode(),
                flight.getFlightId().getDepDate(), flight.getFlightId().getDepTime());

        flight.setFlightId(new FlightCompositeKey(flight.getFlightId().getVendor(),
                sourceCode.getCityCode(),
                destCode.getCityCode(),
                flight.getFlightId().getDepDate(), flight.getFlightId().getDepTime()));

        if (availableFlight == null) {
            if (flight.getAvailableBusiness() > flight.getTotalBusiness())
                throw new FlightSeatsException("Total Business seats can't be lesser than " +
                        "Available Business Seats");
            if (flight.getAvailableEconomy() > flight.getTotalEconomy())
                throw new FlightSeatsException("Total Economy seats can't be lesser than " +
                        "Available Economy Seats");
            iFlightRepository.save(flight);
            Flight added = iFlightRepository.getFlight(flight.getFlightId().getVendor(),
                    sourceCode.getCityCode(), destCode.getCityCode(),
                    flight.getFlightId().getDepDate(), flight.getFlightId().getDepTime());
            return added;
        } else {
            throw new FlightAlreadyExistException("This flight already exists");
        }
    }

    @Override
    public Flight findById(String id) {
        Flight flight = iFlightRepository.findByFlightId(id);
        if (flight == null)
            throw new FlightNotfoundException("Flight Not Exist");
        else
            return flight;
    }

    @Override
    public boolean updateFlight(Flight flight, String flightId, String flag) {
        flight.setId(flightId);
        if (flag.equalsIgnoreCase("0")) {
            IATACode sourceCode = iCityCodeRepository.findByCityName(flight.getFlightId().getSource());
            IATACode destCode = iCityCodeRepository.findByCityName(flight.getFlightId().getDestination());
            if (sourceCode == null) {
                throw new FlightNotfoundException("City " + flight.getFlightId().getSource() + " doesn't exist");
            }
            if (destCode == null) {
                throw new FlightNotfoundException("City " + flight.getFlightId().getDestination() + " doesn't exist");
            }
            if (sourceCode.getCityCode().equalsIgnoreCase(destCode.getCityCode()))
                throw new FlightNotfoundException("Source and Destination can't be same");
            flight.getFlightId().setSource(sourceCode.getCityCode());
            flight.getFlightId().setDestination(destCode.getCityCode());
        }
        Flight flightAvailableForId = iFlightRepository.findByFlightId(flightId);

        if (flightAvailableForId == null) {
            throw new FlightNotfoundException("This flight doesn't exists");
        }
        if (flight.getAvailableBusiness() > flight.getTotalBusiness())
            throw new FlightSeatsException("Total Business seats can't be lesser than " +
                    "Available Business Seats");
        if (flight.getAvailableEconomy() > flight.getTotalEconomy())
            throw new FlightSeatsException("Total Economy seats can't be lesser than " +
                    "Available Economy Seats");
        iFlightRepository.save(flight);
        return true;
    }

    @Override
    public boolean addFlightFare(FlightFare flightFare) {
        String flightId = flightFare.getFlightId();
        if (flightFare.getBusinessFare() < flightFare.getEconomyFare()) {
            throw new FlightFareErrorExcpetion("Business Fare can't be smaller than Economy Fare");
        }
        Flight flight = iFlightRepository.findByFlightId(flightId);
        if (flight == null) {
            throw new FlightNotfoundException("This flight doesn't exists");
        }
        FlightFare availableFlightFare = iFlightFareRepository.findByFlightId(flightId);

        if (availableFlightFare == null) {

            iFlightFareRepository.save(flightFare);
            System.out.println("Fare added for " + flightId);
            return true;
        } else {
            throw new FlightFareErrorExcpetion("Fare already added for this flight");
        }

    }

    @Override
    public FlightFare getFlightFare(String flightId) {
        Flight flight = iFlightRepository.findByFlightId(flightId);
        if (flight == null) {
            throw new FlightNotfoundException("This flight doesn't exist");
        }
        FlightFare flightFare = iFlightFareRepository.findByFlightId(flightId);
        if (flightFare == null) {
            throw new FlightFareErrorExcpetion("The fare for this flight is not added");
        }
        return flightFare;
    }

    @Override
    public boolean updateFlightFare(FlightFare flightFare) {
        String flightId = flightFare.getFlightId();
        Flight flight = iFlightRepository.findByFlightId(flightId);
        if (flight == null) {
            throw new FlightNotfoundException("This flight doesn't exist");

        }
        if (flightFare.getBusinessFare() < flightFare.getEconomyFare()) {
            throw new FlightFareErrorExcpetion("Business Fare can't be smaller than Economy Fare");
        }
        FlightFare flightFareExist = iFlightFareRepository.findByFlightId(flightId);
        if (flightFareExist == null) {
            iFlightFareRepository.save(flightFare);
        } else {
            Query query = new Query();
            query.addCriteria(Criteria.where("flightId").is(flightId));
            Update update = new Update();
            update.set("businessFare", flightFare.getBusinessFare());
            update.set("economyFare", flightFare.getEconomyFare());
            update.set("date", flightFare.getDate());
            mongoTemplate.findAndModify(query, update, FlightFare.class);
        }
        return true;
    }

    @Override
    public boolean addIATACode(IATACode iataCode) {
        boolean status = false;
        if (iCityCodeRepository.findByCityCode(iataCode.getCityCode()) == null) {
            iCityCodeRepository.save(iataCode);
            status = true;
        }

        return status;
    }

    @Override
    public List<IATACode> getIATACodes() {
        return iCityCodeRepository.findAll();
    }
}

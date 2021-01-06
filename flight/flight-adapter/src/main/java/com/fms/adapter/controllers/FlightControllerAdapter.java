package com.fms.adapter.controllers;

import com.fms.adapter.Mapper.FlightMapper;
import com.fms.api.FlightController;
import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import com.fms.api.dto.IATACodeDTO;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;
import com.fms.core.port.in.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightControllerAdapter implements FlightController {

    @Autowired
    private FlightMapper flightMapper;

    @Autowired
    private FlightService flightService;

    @Override
    public ResponseEntity<FlightDTO> getFlightsById(String flightId) {
        Flight availableFlight = flightService.getFlightsById(flightId);

        FlightDTO availableFlightDTO = flightMapper.convertToFlightDTO(availableFlight);
        return ResponseEntity.ok(availableFlightDTO);
    }


    @Override
    public ResponseEntity<List<FlightDTO>> getFlightsBySrcAndDestAndDate(
            String src, String dest, String date, String berth, String travellers) {

        List<Flight> availableFlights = flightService.getFlightsBySrcAndDestAndDate(src, dest, date, berth, travellers);

        ArrayList<FlightDTO> availableFlightsDTO = flightMapper.convertToFlightDTOList(availableFlights);
        return ResponseEntity.ok(availableFlightsDTO);
    }

    @Override
    public ResponseEntity<FlightFareDTO> getFlightFare(String flightId) {

        FlightFare flightFare = flightService.getFlightFare(flightId);
        FlightFareDTO flightFareDTO = flightMapper.convertToFlightFareDTO(flightFare);
        return ResponseEntity.ok(flightFareDTO);

    }

    @Override
    public ResponseEntity<FlightDTO> addFlight(FlightDTO flightDTO) {


        Flight flight = flightMapper.convertToFlight(flightDTO);
        Flight addedFlight = flightService.addFlight(flight);
        FlightDTO flightDTOAdded = flightMapper.convertToFlightDTO(addedFlight);
        return ResponseEntity.status(HttpStatus.OK)
                .body(flightDTOAdded);
    }

    @Override
    public ResponseEntity<FlightFareDTO> addFlightFare(FlightFareDTO flightFareDTO, String flightId) {
        FlightFare flightFare = flightMapper.convertToFlightFare(flightFareDTO, flightId);
        boolean status = flightService.addFlightFare(flightFare);
        FlightFareDTO flightFareDTOwithId = flightMapper.convertToFlightFareDTO(flightFare);
        return ResponseEntity.status(HttpStatus.OK).body(flightFareDTOwithId);
    }

    @Override
    public ResponseEntity<FlightDTO> updateFlight(FlightDTO flightDTO, String flightId, String flag) {
        Flight flight = flightMapper.convertToFlight(flightDTO);
        boolean updateStatus = flightService.updateFlight(flight, flightId, flag);
        flight.setId(flightId);
        FlightDTO updatedFlightDTO = flightMapper.convertToFlightDTO(flight);
        return ResponseEntity.ok(updatedFlightDTO);
    }

    @Override
    public ResponseEntity<FlightFareDTO> updateFlightFare(FlightFareDTO flightFareDTO, String flightId) {
        FlightFare flightFare = flightMapper.convertToFlightFare(flightFareDTO, flightId);
        boolean updateStatus = flightService.updateFlightFare(flightFare);
        flightFare.setFlightId(flightId);
        FlightFareDTO updatedFlightFareDTO = flightMapper.convertToFlightFareDTO(flightFare);
        return ResponseEntity.ok(updatedFlightFareDTO);
    }

    @Override
    public ResponseEntity<IATACodeDTO> addIATACode(IATACodeDTO IATACodeDTO) {
        IATACode IATACode = flightMapper.convertToIATACode(IATACodeDTO);
        boolean status = flightService.addIATACode(IATACode);
        HttpStatus httpStatus = status ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        if (httpStatus == HttpStatus.BAD_REQUEST)
            return ResponseEntity.status(httpStatus).body(null);
        return ResponseEntity.status(httpStatus).body(IATACodeDTO);
    }

    @Override
    public ResponseEntity<List<IATACodeDTO>> getIATACodes() {
        List<IATACode> iataCodeList = flightService.getIATACodes();
        List<IATACodeDTO> iataCodeDTOList = flightMapper.convertToIATACodeDTOList(iataCodeList);
        return ResponseEntity.ok(iataCodeDTOList);
    }

}

package com.fms.user.adapter.feignClient;

import com.fms.api.dto.FlightDTO;
import com.fms.user.adapter.mapper.UserAndBookingMapper;
import com.fms.user.api.UIPort.BookingClient;
import com.fms.user.core.domain.Flight;
import com.fms.user.core.exception.BookingClientException;
import com.fms.user.core.port.out.BookingClientPort;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeignClientAdapter implements BookingClientPort {

    @Autowired
    private BookingClient bookingClient;

    @Autowired
    private UserAndBookingMapper mapper;

    @Override
    public Flight getFlightById(String flightId) {
        ResponseEntity<FlightDTO> responseEntity;
        FlightDTO flightDTO = new FlightDTO();
        try {
            responseEntity = bookingClient.getFlightsById(flightId);
            flightDTO = responseEntity.getBody();
        } catch (FeignException ex) {
            throw new BookingClientException(new String(ex.content()));
        }

        Flight flight = mapper.convertToFlight(flightDTO);
        return flight;
    }

    @Override
    public boolean updateFlight(Flight flight, String flightId) {
        FlightDTO flightDTO = mapper.convertToFlightDTO(flight);
        System.out.println(flightDTO.getAvailableBusiness() + " " + flightDTO.getId());
        ResponseEntity<FlightDTO> responseEntity = bookingClient.updateFlight(flightDTO, flightId, "1");
        flightDTO = responseEntity.getBody();
        return responseEntity.getStatusCode().is2xxSuccessful();
    }
}

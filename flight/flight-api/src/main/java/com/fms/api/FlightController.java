package com.fms.api;

import com.fms.api.dto.IATACodeDTO;
import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
public interface FlightController {

    @GetMapping("/flight/{src}/{dest}/{date}/{berth}/{travellers}")
    ResponseEntity<List<FlightDTO>> getFlightsBySrcAndDestAndDate(@PathVariable String src,
                                                                  @PathVariable String dest,
                                                                  @PathVariable String date,
                                                                  @PathVariable String berth,
                                                                  @PathVariable String travellers);

    @GetMapping("/flight/{flightId}")
    ResponseEntity<FlightDTO> getFlightsById(@PathVariable String flightId);

    @GetMapping("/flight/{flightId}/fare")
    ResponseEntity<FlightFareDTO> getFlightFare(@PathVariable String flightId);


    @PostMapping("/flight")
    ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO);

    @PostMapping("/flight/{flightId}/fare")
    ResponseEntity<FlightFareDTO> addFlightFare(@RequestBody FlightFareDTO flightFareDTO,
                                                @PathVariable String flightId);


    @PutMapping("/flight/{flightId}/{flag}")
    ResponseEntity<FlightDTO> updateFlight(@RequestBody FlightDTO flightDTO, @PathVariable String flightId, @PathVariable String flag);

    @PutMapping("/flight/{flightId}/fare")
    ResponseEntity<FlightFareDTO> updateFlightFare(@RequestBody FlightFareDTO flightFareDTO, @PathVariable String flightId);

    @PostMapping("/iatacode")
    ResponseEntity<IATACodeDTO> addIATACode(@RequestBody IATACodeDTO IATACodeDTO);

    @GetMapping("/iatacode")
    ResponseEntity<List<IATACodeDTO>> getIATACodes();

}

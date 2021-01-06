package com.fms.user.api.UIPort;

import com.fms.api.dto.FlightDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "flight-service")
public interface BookingClient {

    @GetMapping("/flight/{flightId}")
    ResponseEntity<FlightDTO> getFlightsById(@PathVariable String flightId);

    @PutMapping("/flight/{flightId}/{flag}")
    ResponseEntity<FlightDTO> updateFlight(@RequestBody FlightDTO flightDTO,
                                           @PathVariable String flightId,
                                           @PathVariable String flag);

}

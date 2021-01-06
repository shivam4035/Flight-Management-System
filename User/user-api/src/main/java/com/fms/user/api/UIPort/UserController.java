package com.fms.user.api.UIPort;

import com.fms.user.api.dto.BookingsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
public interface UserController {


    @PostMapping("/flight/{flightId}/booking")
    ResponseEntity<BookingsDTO> addBooking(@RequestBody BookingsDTO bookingsDTO,
                                           @PathVariable String flightId);

    @GetMapping("/bookings/mobile/{mobileNo}")
    ResponseEntity<List<BookingsDTO>> getBookingsById(@PathVariable String mobileNo);

}

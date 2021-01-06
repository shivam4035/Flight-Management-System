package com.fms.user.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.api.dto.FlightCompositeKeyDTO;
import com.fms.api.dto.FlightDTO;
import com.fms.user.adapter.dataLoader.UserServiceDataLoader;
import com.fms.user.adapter.mapper.UserAndBookingMapper;
import com.fms.user.api.UIPort.UserController;
import com.fms.user.api.dto.BookingsDTO;
import com.fms.user.api.dto.TravellerDTO;
import com.fms.user.core.domain.*;
import com.fms.user.core.port.in.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(UserControllerAdapter.class)
@ContextConfiguration(classes = {UserController.class, UserControllerAdapter.class})
class UserControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserAndBookingMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();


    Flight flight;
    FlightDTO flightDTO;
    FlightCompositeKey flightDetails;
    FlightCompositeKeyDTO flightDetailsDTO;
    Traveller traveller1, traveller2;
    TravellerDTO traveller1DTO, traveller2DTO;
    List<Traveller> travellerList;
    List<TravellerDTO> travellerListDTO;
    Bookings bookings;
    BookingsDTO bookingsDTO;

    @BeforeEach
    public void setUp() {


        flight = UserServiceDataLoader.createFlight();
        flightDetails = flight.getFlightId();
        flightDTO = UserServiceDataLoader.createFlightDTO();
        flightDetailsDTO = flightDTO.getFlightId();

        traveller1 = UserServiceDataLoader.createTraveller1();
        traveller2 = UserServiceDataLoader.createTraveller2();
        traveller1DTO = UserServiceDataLoader.createTravellerDTO(traveller1);
        traveller2DTO = UserServiceDataLoader.createTravellerDTO(traveller2);

        travellerList = new ArrayList<>();
        travellerList.add(traveller1);
        travellerList.add(traveller2);

        travellerListDTO = new ArrayList<>();
        travellerListDTO.add(traveller1DTO);
        travellerListDTO.add(traveller2DTO);

        bookings = UserServiceDataLoader.createBookings(travellerList, flightDetails, "54576886676");
        bookingsDTO = UserServiceDataLoader.createBookingsDTO(bookings);

    }


    @Test
    @Order(1)
    void addBooking() throws Exception {

        when(mapper.convertToBookings(any(BookingsDTO.class))).thenReturn(bookings);
        when(mapper.convertToBookingsDTO(any(Bookings.class))).thenReturn(bookingsDTO);
        when(userService.addBooking(bookings, flight.getId())).thenReturn(bookings);

        mockMvc.perform(MockMvcRequestBuilders.post(
                "/flight/{flightId}/booking", flightDTO.getId())
                .content(objectMapper.writeValueAsString(bookingsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    void getBookingsById() throws Exception {

        when(userService.getBookingsById(bookings.getMobile())).thenReturn(Stream.of(bookings).collect(
                Collectors.toList()));
        when(mapper.convertToBookingsDTOList(Stream.of(bookings).collect(Collectors.toList())))
                .thenReturn(Stream.of(bookingsDTO).collect(Collectors.toList()));

        mockMvc.perform(MockMvcRequestBuilders.get("/bookings/mobile/" + bookings.getMobile())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}

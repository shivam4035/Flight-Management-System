

package com.fms.adapter.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.adapter.DataLoader.DataLoader;
import com.fms.adapter.Mapper.FlightMapper;
import com.fms.api.FlightController;
import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.core.port.in.FlightService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(value = FlightControllerAdapter.class)
@ContextConfiguration(classes = {FlightController.class, FlightControllerAdapter.class})
public class FlightControllerAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightMapper flightMapper;

    @MockBean
    private FlightService flightService;

    private ObjectMapper objectMapper = new ObjectMapper();

    Flight flight1, flight2;
    FlightDTO flight1DTO, flight2DTO;
    FlightFare flightFare;
    FlightFareDTO flightFareDTO;


    @BeforeEach
    public void setUp() {
        flight1 = DataLoader.createFlight1();
        flight2 = DataLoader.createFlight2();

        flight1DTO = DataLoader.createFlightDTO(flight1);
        flight2DTO = DataLoader.createFlightDTO(flight2);

        flightFare = DataLoader.createFlightFare(flight1.getId());
        flightFareDTO = DataLoader.createFlightFareDTO(flightFare);
    }


    @Test
    @Order(1)
    public void addFlight() throws Exception {
        Flight flight3 = DataLoader.createFlight3();
        FlightDTO flight3DTO = DataLoader.createFlightDTO(flight3);

        when(flightMapper.convertToFlight(flight3DTO)).thenReturn(flight3);
        when(flightService.addFlight(flight3)).thenReturn(flight3);
        when(flightMapper.convertToFlightDTO(flight3)).thenReturn(flight3DTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/flight")
                .content(objectMapper.writeValueAsString(flight3DTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @Order(3)
    public void getFlightsById() throws Exception {
        when(flightService.getFlightsById(flight1.getId())).thenReturn(flight1);
        when(flightMapper.convertToFlightDTO(flight1)).thenReturn(flight1DTO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flight/"
                + flight1.getId()).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", hasToString(flight1.getId())))
                .andExpect(jsonPath("$.flightId.source",
                        hasToString(flight1.getFlightId().getSource())));
    }

    @Test
    @Order(4)
    public void getFlightsBySrcAndDestAndDate() throws Exception {
        String src = flight2.getFlightId().getSource();
        String dest = flight2.getFlightId().getDestination();
        String date = flight2.getFlightId().getDepDate();
        String berth = "Business";
        String numberOfTravellers = "2";

        when(flightService.getFlightsBySrcAndDestAndDate(src, dest, date, berth, (numberOfTravellers))).thenReturn(
                Stream.of(flight2).collect(Collectors.toList()));
        when(flightMapper.convertToFlightDTOList(Stream.of(flight2).collect(Collectors.toList())))
                .thenReturn((ArrayList<FlightDTO>) Stream.of(flight2DTO).collect(Collectors.toList()));


        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flight/"
                + src + "/" + dest + "/" + date + "/" + berth + "/" + numberOfTravellers)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", hasToString(flight2.getId())));

    }


    @Test
    @Order(5)
    public void updateFlight() throws Exception {
        flight1.setAvailableEconomy(11);

        when(flightMapper.convertToFlight(flight1DTO)).thenReturn(flight1);
        when(flightService.updateFlight(flight1, flight1.getId(), "0")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/flight/" + flight1.getId())
                .content(objectMapper.writeValueAsString(flight1DTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @Order(6)
    public void addFlightFare() throws Exception {

        when(flightService.addFlightFare(flightFare))
                .thenReturn(true);
        when(flightMapper.convertToFlightFare(flightFareDTO, flight1.getId())).thenReturn(flightFare);
        when(flightMapper.convertToFlightFareDTO(flightFare)).thenReturn(flightFareDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/flight/" + flight1.getId() + "/fare")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flightFareDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @Order(7)
    public void getFlightFare() throws Exception {

        when(flightService.getFlightFare(flight1.getId())).thenReturn(flightFare);
        when(flightMapper.convertToFlightFareDTO(flightFare)).thenReturn(flightFareDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/flight/" + flight1.getId() + "/fare"))
                .andExpect(status().isOk())
                .andDo(print());

    }

}

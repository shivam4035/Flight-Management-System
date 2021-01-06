package com.fms.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fms.api.dto.FlightDTO;
import com.fms.user.core.domain.Flight;
import com.fms.user.dataLoader.UserServiceDataLoader;
import com.fms.user.api.dto.BookingsDTO;
import com.fms.user.core.domain.Bookings;
import com.fms.user.core.domain.Traveller;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.junit.Before;
import org.junit.ClassRule;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {UserApplicationLauncherTest.LocalRibbonClientConfiguration.class})
public class UserApplicationLauncherTest {

    @LocalServerPort
    private int port;

    @ClassRule
    public static WireMockClassRule wiremock = new
            WireMockClassRule(WireMockConfiguration.options().dynamicPort());

    ObjectMapper objectMapper;
    TestRestTemplate restTemplate;
    HttpHeaders headers;
    Flight flight;
    FlightDTO flightDTO;
    Traveller traveller1;
    Traveller traveller2;
    List<Traveller> travellerList;
    Bookings bookings;
    BookingsDTO bookingsDTO;

    @Before
    public void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();

        flight = UserServiceDataLoader.createFlight();
        flightDTO = UserServiceDataLoader.createFlightDTO();


        traveller1 = UserServiceDataLoader.createTraveller1();
        traveller2 = UserServiceDataLoader.createTraveller2();

        travellerList = new ArrayList<>();
        travellerList.add(traveller1);
        travellerList.add(traveller2);


        bookings = UserServiceDataLoader.createBookings(travellerList,
                flight.getFlightId(), "9875454558");
        bookingsDTO = UserServiceDataLoader.createBookingsDTO(bookings);

        stubFor(get(urlEqualTo("/flight/" + flightDTO.getId()))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                        .withBody(objectMapper.writeValueAsString(flightDTO))));

        stubFor(put(urlEqualTo("/flight/" + flightDTO.getId() + "/1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
                        .withBody(objectMapper.writeValueAsString(flightDTO))));


    }


    @Test
    public void testCaddBooking() {

        String flightId = flightDTO.getId();


        HttpEntity<BookingsDTO> entity = new HttpEntity<BookingsDTO>(bookingsDTO, headers);
        ResponseEntity<BookingsDTO> response = restTemplate.exchange(
                createURIWithPort("/flight/" + flightId + "/booking"),
                HttpMethod.POST, entity, new ParameterizedTypeReference<BookingsDTO>() {
                });


        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testDgetBookingbyUserId() {
        String id = bookingsDTO.getMobile();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<List<BookingsDTO>> response = restTemplate.exchange(
                createURIWithPort("/bookings/mobile/" + id), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<BookingsDTO>>() {
                });

        List<BookingsDTO> responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(responseBody.get(0).getPnr(), bookings.getPnr());
    }


    private String createURIWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    @TestConfiguration
    public static class LocalRibbonClientConfiguration {

        @Bean
        public ServerList<Server> ribbonServerList() {
            return new StaticServerList<>(new Server("localhost", wiremock.port()));
        }
    }
}

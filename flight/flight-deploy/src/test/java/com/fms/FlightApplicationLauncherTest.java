package com.fms;

import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.dataLoader.DataLoader;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FlightApplicationLauncherTest {

    @LocalServerPort
    private int port;

    static int numberOfFlight = 0;

    TestRestTemplate restTemplate;
    HttpHeaders headers;
    Flight flight;
    FlightDTO flightDTO;
    FlightFare flightFare;
    FlightFareDTO flightFareDTO;
    String berth = "business";
    int numberOfTravellers = 2;

    @BeforeEach
    public void setUp() {
        restTemplate = new TestRestTemplate();
        headers = new HttpHeaders();

        flight = DataLoader.createFlight1();
        flightDTO = DataLoader.createFlightDTO(flight);

        flightFare = DataLoader.createFlightFare(flight.getId());
        flightFareDTO = DataLoader.createFlightFareDTO(flightFare);

    }


    @Test
    @Order(1)
    public void addFlight() {

        HttpEntity<FlightDTO> entity = new HttpEntity<FlightDTO>(flightDTO, headers);
        ResponseEntity<FlightDTO> response = restTemplate.exchange(
                createURIWithPort("/flight"), HttpMethod.POST, entity, FlightDTO.class);

        FlightDTO responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(responseBody);
        numberOfFlight += 1;
    }


    @Test
    @Order(3)
    public void getFlightBySrcAndDestAndDate() {


        String src = flight.getFlightId().getSource();
        String dest = flight.getFlightId().getDestination();
        String date = flight.getFlightId().getDepDate();

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<List<FlightDTO>> response = restTemplate.exchange(
                createURIWithPort("/flight/" + src + "/" + dest + "/" + date + "/" + berth + "/" + numberOfTravellers),
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<FlightDTO>>() {
                });

        List<FlightDTO> responseBody = response.getBody();

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(responseBody.size(), 1);
    }

    @Test
    @Order(4)
    public void updateFlight() {
        String id = flight.getId();
        flight.setArrTime("02:12");
        FlightDTO flightDTOUpdate = DataLoader.createFlightDTO(flight);

        HttpEntity<FlightDTO> entity = new HttpEntity<FlightDTO>(flightDTOUpdate, headers);
        ResponseEntity<FlightDTO> response = restTemplate.exchange(
                createURIWithPort("/flight/" + id + "/0"), HttpMethod.PUT, entity,
                FlightDTO.class);

        FlightDTO responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(responseBody);
    }

    @Test
    @Order(5)
    public void getFlightbyId() {
        String id = flight.getId();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<FlightDTO> response = restTemplate.exchange(
                createURIWithPort("/flight/" + id),
                HttpMethod.GET, entity, FlightDTO.class);

        FlightDTO responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(responseBody.getId());
    }

    @Test
    @Order(6)
    public void addFlightFare() {
        String id = flight.getId();
        HttpEntity<FlightFareDTO> entity = new HttpEntity<FlightFareDTO>(flightFareDTO, headers);
        ResponseEntity<FlightFareDTO> response = restTemplate.exchange(
                createURIWithPort("/flight/" + id + "/fare"), HttpMethod.POST, entity,
                FlightFareDTO.class);

        FlightFareDTO responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(responseBody);

    }

    @Test
    @Order(7)
    public void getFlightFare() {
        String id = flight.getId();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<FlightFareDTO> response = restTemplate.exchange(
                createURIWithPort("/flight/" + id + "/fare"), HttpMethod.GET, entity,
                FlightFareDTO.class);

        FlightFareDTO responseBody = response.getBody();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(responseBody);

    }

    private String createURIWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}

package com.fms.core.FlightServiceImpl;

import com.fms.core.dataLoader.DataLoader;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightCompositeKey;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;
import com.fms.core.port.out.FlightFareRepositoryPort;
import com.fms.core.port.out.FlightRepositoryPort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

    @InjectMocks
    private FlightServiceImpl flightServiceImpl;

    @Mock
    private FlightRepositoryPort flightRepositoryPort;
    @Mock
    private FlightFareRepositoryPort flightFareRepositoryPort;

    Flight flight1, flight2, flight3;
    FlightFare flightFare;
    IATACode iataCode1, iataCode2;
    String berth = "Business";
    String numberOfTravellers = "2";

    @BeforeEach
    public void setUp() {
        flight1 = DataLoader.createFlight1();
        flight2 = DataLoader.createFlight2();
        flight3 = DataLoader.createFlight3();

        iataCode1 = DataLoader.createIATACode1();
        iataCode2 = DataLoader.createIATACode2();
        flightFare = DataLoader.createFlightFare(flight1.getId());
    }


    @Test
    @Order(3)
    public void getFlightsBySrcAndDestAndDate() {
        FlightCompositeKey flightCompositeKey = flight2.getFlightId();

        when(flightRepositoryPort.findBySrcAndDestAndDate(flightCompositeKey.getSource(),
                flightCompositeKey.getDestination(), flightCompositeKey.getDepDate())).
                thenReturn(Stream.of(flight2).collect(Collectors.toList()));

        assertEquals(flightCompositeKey.getVendor(),
                flightServiceImpl.getFlightsBySrcAndDestAndDate(
                        flightCompositeKey.getSource(), flightCompositeKey.getDestination(),
                        flightCompositeKey.getDepDate(), berth, numberOfTravellers).get(0).getFlightId().getVendor());

        assertEquals(flightCompositeKey.getDepTime(),
                flightServiceImpl.getFlightsBySrcAndDestAndDate(
                        flightCompositeKey.getSource(), flightCompositeKey.getDestination(),
                        flightCompositeKey.getDepDate(), berth, numberOfTravellers).get(0).getFlightId().getDepTime());

        assertEquals(1, flightServiceImpl.getFlightsBySrcAndDestAndDate(
                flightCompositeKey.getSource(), flightCompositeKey.getDestination(),
                flightCompositeKey.getDepDate(), berth, numberOfTravellers).size());
    }


    @Test
    @Order(4)
    public void getFlightsById() {
        when(flightRepositoryPort.findById(flight2.getId())).thenReturn(flight2);
        assertThat(flightServiceImpl.getFlightsById(flight2.getId())).isEqualTo(flight2);
    }

    @Test
    @Order(1)
    public void addFlight() {

        when(flightRepositoryPort.addFlight(flight2)).thenReturn(flight2);
        flightServiceImpl.addFlight(flight2);
    }

    @Test
    @Order(5)
    public void updateFlight() {
        flight1.setAvailableEconomy(120);
        when(flightRepositoryPort.updateFlight(flight1, flight1.getId(), "0")).thenReturn(true);
        flightServiceImpl.updateFlight(flight1, flight1.getId(), "0");
    }

    @Test
    @Order(6)
    public void addFlightFare() {

        when(flightFareRepositoryPort.addFlightFare(flightFare))
                .thenReturn(true);
        flightServiceImpl.addFlightFare(flightFare);
    }

    @Test
    @Order(7)
    public void getFlightFare() {

        when(flightFareRepositoryPort.getFlightFare(flight1.getId())).thenReturn(flightFare);

        assertThat(flightServiceImpl.getFlightFare(flight1.getId())
                .getBusinessFare()).isEqualTo(flightFare.getBusinessFare());

    }


}

package com.fms.adapter.database;

import com.fms.adapter.DataLoader.DataLoader;
import com.fms.adapter.persistence.ICityCodeRepository;
import com.fms.adapter.persistence.IFlightFareRepository;
import com.fms.adapter.persistence.IFlightRepository;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightFare;
import com.fms.core.domain.IATACode;
import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataMongoTest
@ContextConfiguration(classes = FlightDBAdapter.class)
@EnableAutoConfiguration
public class FlightDBAdapterTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FlightDBAdapter flightDBAdapter;

    @MockBean
    private IFlightRepository iFlightRepository;
    @MockBean
    private IFlightFareRepository iFlightFareRepository;
    @MockBean
    private ICityCodeRepository iCityCodeRepository;

    Flight flight1, flight2;
    FlightFare flightFare1;
    IATACode iataCode1, iataCode2, iataCode3;

    @BeforeEach
    public void setup() {

        flight1 = DataLoader.createFlight1();
        flight2 = DataLoader.createFlight2();

        iataCode1 = DataLoader.createIATACode1();
        iataCode2 = DataLoader.createIATACode2();
        iataCode3 = DataLoader.createIATACode3();
        flightFare1 = DataLoader.createFlightFare(flight1.getId());

        mongoTemplate.save(flight1);
        mongoTemplate.save(flight2);
        mongoTemplate.save(flightFare1, "FlightFare");
    }

    @Test
    @Order(1)
    public void addFlight() {

        Flight flight3 = DataLoader.createFlight3();

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                mongoTemplate.save(flight3);
                System.out.println(flight3.getFlightId().getSource() + " " + flight3.getFlightId().getDestination());
                return flight3;
            }
        };

        when(iCityCodeRepository.findByCityName(iataCode1.getCityName())).thenReturn(iataCode1);
        when(iCityCodeRepository.findByCityName(iataCode2.getCityName())).thenReturn(iataCode2);

        when(iFlightRepository.getFlight(flight3.getFlightId().getVendor(),
                iataCode1.getCityCode(), iataCode2.getCityCode(),
                flight3.getFlightId().getDepDate(), flight3.getFlightId().getDepTime()))
                .thenReturn(null);

        when(iFlightRepository.save(flight3)).thenAnswer(answer);

        flightDBAdapter.addFlight(flight3);

    }


    @Test
    @Order(3)
    public void findBySrcAndDestAndDate() {
        String src = iataCode1.getCityCode();
        String dest = iataCode2.getCityCode();
        String date = "12-08-2020";

        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("flightId.source").is(src),
                        Criteria.where("flightId.destination").is(dest),
                        Criteria.where("flightId.depDate").is(date)
                )
        );
        List<Flight> result = mongoTemplate.find(query, Flight.class);

        when(iCityCodeRepository.findByCityName(iataCode1.getCityName())).thenReturn(iataCode1);
        when(iCityCodeRepository.findByCityName(iataCode2.getCityName())).thenReturn(iataCode2);

        when(iFlightRepository.findBySrcAndDestAndDate(src, dest, date))
                .thenReturn(result);

        assertThat(flightDBAdapter.findBySrcAndDestAndDate(iataCode1.getCityName(), iataCode2.getCityName(), date)
                .get(0).getId()).isEqualTo("airways");

    }

    @Test
    @Order(4)
    public void findById() {

        String id = "jet";
        Flight flight = mongoTemplate.findById(id, Flight.class);

        when(iFlightRepository.findByFlightId(id)).thenReturn(flight);

        assertThat(flightDBAdapter.findById(id).getId()).isEqualTo(id);

    }

    @Test
    @Order(5)
    public void updateFlight() {
        flight2.setAvailableBusiness(100);

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                mongoTemplate.save(flight2);
                return flight2;
            }
        };
        when(iFlightRepository.save(flight2)).thenAnswer(answer);
        when(iFlightRepository.findByFlightId(flight2.getId())).thenReturn(flight2);
        when(iCityCodeRepository.findByCityName(iataCode3.getCityName())).thenReturn(iataCode3);
        when(iCityCodeRepository.findByCityName(iataCode2.getCityName())).thenReturn(iataCode2);
        flightDBAdapter.updateFlight(flight2, flight2.getId(), "0");
        assertThat(flightDBAdapter.findById(flight2.getId()).getAvailableBusiness()).isEqualTo(100);
    }


    @Test
    @Order(6)
    public void addFlightFare() {
        FlightFare flightFare2 = new FlightFare(flight2.getId(), 2222, 1200, "12-12-21");

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                mongoTemplate.save(flightFare2, "FlightFare");
                return flightFare2;
            }
        };
        when(iFlightRepository.findByFlightId(flight2.getId())).thenReturn(flight2);
        when(iFlightFareRepository.findByFlightId(flight2.getId())).thenReturn(null);
        when(iFlightFareRepository.save(flightFare2)).thenAnswer(answer);
        flightDBAdapter.addFlightFare(flightFare2);

    }

    @Test
    @Order(7)
    public void getFlightFare() {
        String flightID = flight1.getId();
        Query query = new Query();
        query.addCriteria(Criteria.where("flightId").is(flightID));
        List<FlightFare> result = mongoTemplate.find(query, FlightFare.class, "FlightFare");
        when(iFlightFareRepository.findByFlightId(flightID)).thenReturn(result.get(0));

        assertThat(flightDBAdapter.getFlightFare(flightID).getBusinessFare()).isEqualTo(flightFare1.getBusinessFare());
    }
}

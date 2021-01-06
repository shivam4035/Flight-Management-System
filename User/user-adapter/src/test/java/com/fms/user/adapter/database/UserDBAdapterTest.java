package com.fms.user.adapter.database;

import com.fms.api.dto.FlightDTO;
import com.fms.user.adapter.dataLoader.UserServiceDataLoader;
import com.fms.user.adapter.persistence.BookingRepository;
import com.fms.user.core.domain.*;

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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {UserDBAdapter.class})
@DataMongoTest
@EnableAutoConfiguration
public class UserDBAdapterTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserDBAdapter userDBAdapter;

    @MockBean
    private BookingRepository bookingRepository;


    Flight flight;
    FlightDTO flightDTO;
    Bookings bookings;
    FlightCompositeKey flightDetails;
    Traveller traveller1, traveller2;
    List<Traveller> travellerList;

    @BeforeEach
    public void setup() {


        flight = UserServiceDataLoader.createFlight();
        flightDTO = UserServiceDataLoader.createFlightDTO();
        flightDetails = flight.getFlightId();

        traveller1 = UserServiceDataLoader.createTraveller1();
        traveller2 = UserServiceDataLoader.createTraveller2();

        travellerList = new ArrayList<>();
        travellerList.add(traveller1);
        travellerList.add(traveller2);

        bookings = UserServiceDataLoader.createBookings(
                travellerList, flightDetails, "9876543234");


        mongoTemplate.save(bookings, "Booking");
    }


    @Test
    @Order(1)
    public void addBooking() {
        Bookings bookings1 = UserServiceDataLoader.createBookings(
                travellerList, flightDetails, "2345678943");

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                mongoTemplate.save(bookings1, "Booking");
                return bookings1;
            }
        };

        when(bookingRepository.save(bookings1)).thenAnswer(answer);
        userDBAdapter.addBooking(bookings1, flightDetails);

        assertThat(mongoTemplate.findAll(Bookings.class, "Booking").size())
                .isEqualTo(2);

    }

    @Test
    @Order(2)
    public void getBookingsById() {

        Query query = new Query();
        query.addCriteria(Criteria.where("mobile").is(bookings.getMobile()));

        List<Bookings> result = mongoTemplate.find(query, Bookings.class, "Booking");
        when(bookingRepository.findByMobile(bookings.getMobile())).thenReturn(result);

        assertThat(userDBAdapter.getBookingsById(bookings.getMobile()).get(0).getPnr())
                .isEqualTo(bookings.getPnr());

    }

}

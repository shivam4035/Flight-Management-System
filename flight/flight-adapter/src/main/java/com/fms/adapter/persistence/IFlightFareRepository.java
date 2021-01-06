package com.fms.adapter.persistence;

import com.fms.core.domain.FlightFare;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IFlightFareRepository extends MongoRepository<FlightFare, String> {

    @Query("{ 'flightId' : ?0 }")
    FlightFare findByFlightId(String flightId);

}

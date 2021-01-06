package com.fms.adapter.persistence;

import com.fms.core.domain.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFlightRepository extends MongoRepository<Flight, String> {

    @Query("{ 'flightId.source' : ?0 , 'flightId.destination' : ?1, 'flightId.depDate' : ?2}")
    List<Flight> findBySrcAndDestAndDate(String src, String dest, String date);

    @Query("{ 'id' : ?0 }")
    Flight findByFlightId(String id);

    @Query("{'flightId.vendor' : ?0 ,'flightId.source' : ?1, 'flightId.destination' : ?2, " +
            "'flightId.depDate' : ?3, 'flightId.depTime' : ?4 }")
    Flight getFlight(String vendor, String src, String dest, String date, String time);
}

package com.fms.user.adapter.persistence;

import com.fms.user.core.domain.Bookings;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Bookings, String> {

    @Query("{ 'mobile' : ?0 }")
    List<Bookings> findByMobile(String mobile);
}

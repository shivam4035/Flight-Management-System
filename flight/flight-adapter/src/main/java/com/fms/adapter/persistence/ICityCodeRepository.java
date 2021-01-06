package com.fms.adapter.persistence;

import com.fms.core.domain.IATACode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityCodeRepository extends MongoRepository<IATACode, String> {

    @Query("{ 'cityCode' : ?0 }")
    IATACode findByCityCode(String cityCode);

    @Query("{ 'cityName' : ?0 }")
    IATACode findByCityName(String cityName);
}

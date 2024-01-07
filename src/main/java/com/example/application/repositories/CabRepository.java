package com.example.application.repositories;

import com.example.application.module.Cab;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CabRepository extends MongoRepository<Cab, String> {
    List<Cab> findByStatus(String status) ;//available or not
    List<Cab> findByCurrentLocation(String location);
    Cab findByDriverName(String driverName);

}

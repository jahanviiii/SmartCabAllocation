package com.example.application.repositories;

import com.example.application.module.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripRepository extends MongoRepository<Trip, String> {
    List<Trip> findByUserId(String userId); // Find trips by user ID

    List<Trip> findByCabId(String cabId); // Find trips by cab ID

    List<Trip> findByTripStatus(String tripStatus);
}

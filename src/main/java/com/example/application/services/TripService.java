package com.example.application.services;

import com.example.application.module.Cab;
import com.example.application.module.Trip;

import java.util.List;

public interface TripService {
    Trip createTrip(Trip trip);
    Trip getTripById(String tripId);
    List<Trip> getAllTrips();
    void updateTrip(Trip trip);
    void deleteTrip(String tripId);

    void finalizeRideDetails(Cab acceptedCab, String tripId);
}

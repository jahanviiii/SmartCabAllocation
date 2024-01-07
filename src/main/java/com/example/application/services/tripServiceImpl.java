package com.example.application.services;

import com.example.application.module.Cab;
import com.example.application.module.Trip;
import com.example.application.repositories.CabRepository;
import com.example.application.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class tripServiceImpl implements TripService {
    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CabRepository cabRepository;

    @Override
    public void finalizeRideDetails(Cab acceptedCabId, String tripId) {
        Trip trip = tripRepository.findById(tripId).orElse(null);
        if (trip != null) {
            trip.setCabId(acceptedCabId.getCabId());
            trip.setTripStatus("In Progress");
            tripRepository.save(trip);
        }

        // Update the status of the Cab
        Cab cab = cabRepository.findById(acceptedCabId.getCabId()).orElse(null);
        if (cab != null) {
            cab.setStatus("On Trip");
            cabRepository.save(cab);
        }
    }

    @Override
    public Trip createTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTripById(String tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @Override
    public void updateTrip(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public void deleteTrip(String tripId) {
        tripRepository.deleteById(tripId);
    }



}

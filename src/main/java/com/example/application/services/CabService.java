package com.example.application.services;

import com.example.application.module.Cab;

import java.util.List;

public interface CabService {
    Cab createCab(Cab cab);
    Cab getCabById(String cabId);
    List<Cab> getAllCabs();
    void updateCab(Cab cab);
    void deleteCab(String cabId);

    void completeTrip(String cabId);

    void startNewTrip(String cabId);

    List<Cab> findAvailableCabsWithinRadius(double latitude, double longitude, double v);

    Cab suggestBestCabByProximity(double latitude, double longitude, List<Cab> availableCabs);
    void acceptRide(String cabId);
    void cancelRide(String cabId);
}

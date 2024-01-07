package com.example.application.services;

import com.example.application.module.Cab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.repositories.CabRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CabLocationService {
    private final Map<String, String> lastKnownLocations = new HashMap<>();
    private final Map<String, String> dynamicCabList = new HashMap<>();

    private CabRepository cabRepository;
    @Autowired
    public CabLocationService(CabRepository cabRepository)
    {this.cabRepository=cabRepository;
        loadLastKnownLocations();
    }

    private void loadLastKnownLocations() {
        List<Cab> cabs = cabRepository.findAll(); // Assuming findAll() retrieves all cabs

        for (Cab cab : cabs) {
            lastKnownLocations.put(cab.getCabId(), cab.getCurrentLocation());
        }
    }
    public String getLastKnownLocation(String cabId) {
        return lastKnownLocations.getOrDefault(cabId, "Location not found");
    }
    public Map<String, String> getDynamicCabList() {
        return dynamicCabList;
    }
    // Simulate real-time updates for cab locations using Scheduled task
//    @Scheduled(fixedDelay = 10000) // Simulate updates every 10 seconds
//    public void updateCabLocations() {
//        List<Cab> cabs = cabRepository.findAll(); // Retrieve all cabs
//        Random random = new Random();
//
//        for (Cab cab : cabs) {
//            // Simulate change in cab location (randomly altering latitude and longitude)
//            double latitude = cab.getCurrentLatitude() + random.nextDouble() - 0.5;
//            double longitude = cab.getCurrentLongitude() + random.nextDouble() - 0.5;
//
//            // Update cab's location
//            cab.setCurrentLocation(latitude+","+longitude);
//
//            // Save updated cab with new location to the database
//            cabRepository.save(cab);
//        }
//    }
    public void receiveLocationUpdate(String cabId, String newLocation) {
        String lastKnownLocation = lastKnownLocations.getOrDefault(cabId, "");

        // Check if the reported location significantly differs from the last known location
        if (!lastKnownLocation.equals(newLocation)) {
            // Update the cab's entry in the geospatial index
            updateCabLocationInDatabase(cabId, newLocation);

            // Update the dynamic list/map to reflect the new location
            updateDynamicList(cabId, newLocation);

            // Update the last known location
            lastKnownLocations.put(cabId, newLocation);
        }
    }
    private void updateCabLocationInDatabase(String cabId, String newLocation) {
        Cab cab = cabRepository.findById(cabId).orElse(null);
        if (cab != null) {
            cab.setCurrentLocation(newLocation);
            cabRepository.save(cab);
        }
    }
    public void updateDynamicList(String cabId, String newLocation)
    {
        dynamicCabList.put(cabId, newLocation);
    }
}


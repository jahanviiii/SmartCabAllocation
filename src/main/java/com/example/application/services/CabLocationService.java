package com.example.application.services;

import com.example.application.module.Cab;
import com.example.application.repositories.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CabLocationService {
    private final Map<String, String> lastKnownLocations = new HashMap<>();
    private final Map<String, String> dynamicCabList = new HashMap<>();

    private CabRepository cabRepository;

    @Autowired
    public CabLocationService(CabRepository cabRepository) {
        this.cabRepository = cabRepository;
        loadLastKnownLocations();
    }

    private void loadLastKnownLocations() {
        List<Cab> cabs = cabRepository.findAll(); // Assuming findAll() retrieves all cabs

        for (Cab cab : cabs) {
            lastKnownLocations.put(cab.getCabId(), cab.getCurrentLocation());
        }
    }

    @Cacheable(value = "cabLocations", key = "#cabId")
    public String getLastKnownLocation(String cabId) {
        return lastKnownLocations.getOrDefault(cabId, "Location not found");
    }

    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void refreshCache() {
        clearCache();
    }

    @CacheEvict(value = "cabLocations", allEntries = true)
    public void clearCache() {
    }

    public Map<String, String> getDynamicCabList() {
        return dynamicCabList;
    }

    public void receiveLocationUpdate(String cabId, String newLocation) {
        String lastKnownLocation = lastKnownLocations.getOrDefault(cabId, "");

        // Check if the reported location significantly differs from the last known location
        if (!lastKnownLocation.equals(newLocation)) {
            // Update the cab's entry in the geospatial index
            updateCabLocationInDatabase(cabId, newLocation);
            updateDynamicList(cabId, newLocation);
            lastKnownLocations.put(cabId, newLocation);
        }
    }

    @CachePut(value = "cabLocations", key = "#cabId")
    public void updateCabLocationInDatabase(String cabId, String newLocation) {
        Cab cab = cabRepository.findById(cabId).orElse(null);
        if (cab != null) {
            cab.setCurrentLocation(newLocation);
            cabRepository.save(cab);
        }
    }

    public void updateDynamicList(String cabId, String newLocation) {
        dynamicCabList.put(cabId, newLocation);
    }
}


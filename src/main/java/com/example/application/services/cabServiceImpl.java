package com.example.application.services;

import com.example.application.module.Cab;
import com.example.application.repositories.CabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class cabServiceImpl implements CabService{
    private final CabLocationService cabLocationService; // Inject CabLocationService
    private final CabRepository cabRepository;
    private static final double AVERAGE_RADIUS_OF_EARTH_KM= 6371.0;

    @Autowired
    public cabServiceImpl(CabLocationService cabLocationService, CabRepository cabRepository) {
        this.cabLocationService = cabLocationService;
        this.cabRepository = cabRepository;
    }

    @Override
    public Cab createCab(Cab cab) {
        return cabRepository.save(cab);
    }

    @Override
    public Cab getCabById(String cabId) {
        return cabRepository.findById(cabId).orElse(null);
    }

    @Override
    public List<Cab> getAllCabs() {
        return cabRepository.findAll();
    }

    @Override
    public void updateCab(Cab cab) {
        cabRepository.save(cab);
    }

    @Override
    public void deleteCab(String cabId) {
        cabRepository.deleteById(cabId);
    }
    @Override
    public Cab suggestBestCabByProximity(double latitude,double longitude, List<Cab> availableCabs) {

        if (availableCabs.isEmpty()) {
            return null; // No available cabs
        }

        Cab closestCab = availableCabs.get(0);
        double minDistance = calculateDistance(closestCab.getCurrentLatitude(),closestCab.getCurrentLongitude(), latitude,longitude);

        for (Cab cab : availableCabs) {
            double distance = calculateDistance(cab.getCurrentLatitude(),cab.getCurrentLongitude(),latitude,longitude);
            if (distance < minDistance) {
                minDistance = distance;
                closestCab = cab;
            }
        }

        return closestCab; // Return the closest cab
    }



    @Override
    public void cancelRide(String cabId) {

    }
    @Override
    public List<Cab> findAvailableCabsWithinRadius(double userLatitude, double userLongitude, double radiusInKilometers) {
        List<Cab> availableCabs = cabRepository.findByStatus("Available");
        List<Cab> cabsWithinRadius = new ArrayList<>();

        for (Cab cab : availableCabs) {
            double cabDistance = calculateDistance(cab.getCurrentLatitude(), cab.getCurrentLongitude(), userLatitude, userLongitude);
            if (cabDistance <= radiusInKilometers) {
                cabsWithinRadius.add(cab);
            }
        }

        return cabsWithinRadius;
    }
    private double calculateDistance(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {
        double latDistance = Math.toRadians(destinationLatitude - sourceLatitude);
        double lonDistance = Math.toRadians(destinationLongitude - sourceLongitude);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(sourceLatitude)) * Math.cos(Math.toRadians(destinationLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH_KM * c; // Distance in kilometers
    }
    // Method to update cab status when a trip starts
    public void startNewTrip(String cabId) {
        Cab cab = cabRepository.findById(cabId).orElse(null);
        if (cab != null) {
            cab.setStatus("On Trip");
            cabRepository.save(cab);
        }
    }
    // Method to update cab status when a trip ends
    public void completeTrip(String cabId) {
        Cab cab = cabRepository.findById(cabId).orElse(null);
        if (cab != null) {
            cab.setStatus("Available");
            cabRepository.save(cab);
        }
    }
    @Override
    public void acceptRide(String cabId)
    {
        Cab cab = cabRepository.findById(cabId).orElse(null);
        if(cab!=null)
        {
            cab.setStatus("Accepted");
            cabRepository.save(cab);
        }
        startNewTrip(cabId);
    }

}

